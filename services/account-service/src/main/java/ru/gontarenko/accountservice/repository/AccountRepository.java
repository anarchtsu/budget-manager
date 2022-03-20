package ru.gontarenko.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gontarenko.accountservice.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
}
