package ru.gontarenko.financeservice.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.financeservice.FinanceOperationClient;
import ru.gontarenko.feignclients.financeservice.dto.FinanceOperationDto;
import ru.gontarenko.feignclients.financeservice.dto.SaveFinanceOperationCommand;
import ru.gontarenko.financeservice.mapper.FinanceOperationMapper;
import ru.gontarenko.financeservice.repository.FinanceOperationRepository;
import ru.gontarenko.financeservice.service.FinanceOperationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(FinanceOperationClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinanceOperationController implements FinanceOperationClient {
    FinanceOperationRepository repository;
    FinanceOperationMapper mapper;
    FinanceOperationService service;

    @Override
    public List<FinanceOperationDto> getAllByAccountId(Integer accountId) {
        return mapper.dtos(repository.findByAccountId(accountId));
    }

    @Override
    public List<FinanceOperationDto> getAllByDateBetweenAndAccountId(LocalDate dateFrom, LocalDate dateTo, Integer accountId) {
        return mapper.dtos(repository.findByDateBetweenAndAccountId(dateFrom, dateTo, accountId));
    }

    @Override
    public FinanceOperationDto create(SaveFinanceOperationCommand command) {
        return mapper.dto(service.create(command));
    }

    @Override
    public FinanceOperationDto update(Long id, SaveFinanceOperationCommand command) {
        return mapper.dto(service.update(id, command));
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }
}
