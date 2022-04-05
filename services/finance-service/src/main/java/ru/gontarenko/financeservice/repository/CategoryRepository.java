package ru.gontarenko.financeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gontarenko.financeservice.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
