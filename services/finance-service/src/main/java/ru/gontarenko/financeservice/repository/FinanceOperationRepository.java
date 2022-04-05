package ru.gontarenko.financeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gontarenko.financeservice.domain.FinanceOperation;

import java.time.LocalDate;
import java.util.List;

public interface FinanceOperationRepository extends JpaRepository<FinanceOperation, Long> {
    List<FinanceOperation> findByAccountId(Integer accountId);

    List<FinanceOperation> findByDateBetweenAndAccountId(LocalDate dateFrom, LocalDate dateTo, Integer accountId);
}
