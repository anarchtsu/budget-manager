package ru.gontarenko.financeservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gontarenko.feignclients.financeservice.dto.SaveFinanceOperationCommand;
import ru.gontarenko.financeservice.domain.FinanceOperation;
import ru.gontarenko.financeservice.mapper.FinanceOperationMapper;
import ru.gontarenko.financeservice.repository.FinanceOperationRepository;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinanceOperationService {
    FinanceOperationRepository repository;
    FinanceOperationMapper mapper;

    public FinanceOperation create(SaveFinanceOperationCommand command) {
        return save(new FinanceOperation(), command);
    }

    public FinanceOperation update(Long id, SaveFinanceOperationCommand command) {
        return save(repository.getById(id), command);
    }

    private FinanceOperation save(FinanceOperation financeOperation, SaveFinanceOperationCommand command) {
        mapper.update(financeOperation, command);
        return repository.save(financeOperation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
