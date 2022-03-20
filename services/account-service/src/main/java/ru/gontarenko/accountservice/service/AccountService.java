package ru.gontarenko.accountservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gontarenko.accountservice.domain.Account;
import ru.gontarenko.accountservice.repository.AccountRepository;


@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository repository;

    public Account create(String email, String password) {
        return repository.save(new Account(email, password));
    }

    public Account changePassword(Integer id, String password) {
        val account = repository.getById(id);
        account.setPassword(password);
        return repository.save(account);
    }
}
