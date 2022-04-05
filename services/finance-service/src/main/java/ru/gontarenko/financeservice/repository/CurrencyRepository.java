package ru.gontarenko.financeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gontarenko.financeservice.domain.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByCode(String code);
}
