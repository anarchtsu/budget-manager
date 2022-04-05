package ru.gontarenko.accountservice.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.accountservice.exception.AccountNotFoundException;
import ru.gontarenko.accountservice.mapper.AccountMapper;
import ru.gontarenko.accountservice.repository.AccountRepository;
import ru.gontarenko.accountservice.service.AccountService;
import ru.gontarenko.feignclients.accountservice.AccountClient;
import ru.gontarenko.feignclients.accountservice.dto.AccountDto;

@RestController
@RequestMapping(AccountClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController implements AccountClient {
    AccountRepository repository;
    AccountMapper mapper;
    AccountService service;

    @Override
    public AccountDto getByEmail(String email) {
        val account = repository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(email));
        return mapper.dto(account);
    }

    @Override
    public AccountDto create(String email, String password) {
        return mapper.dto(service.create(email, password));
    }

    @Override
    public AccountDto changePassword(Integer id, String password) {
        return mapper.dto(service.changePassword(id, password));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
