package ru.gontarenko.financeservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gontarenko.feignclients.financeservice.dto.FinanceOperationDto;
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

    public FinanceOperation create(FinanceOperationDto dto) {
        return save(new FinanceOperation(), dto);
    }

    public FinanceOperation update(Long id, FinanceOperationDto dto) {
        return save(repository.getById(id), dto);
    }

    private FinanceOperation save(FinanceOperation financeOperation, FinanceOperationDto dto) {
        mapper.update(financeOperation, dto);
        return repository.save(financeOperation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}