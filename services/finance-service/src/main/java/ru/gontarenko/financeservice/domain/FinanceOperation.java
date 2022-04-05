package ru.gontarenko.financeservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.gontarenko.values.FinanceOperationType;
import ru.gontarenko.values.RepeatPeriod;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    Integer accountId;

    @NotNull
    LocalDate date;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    Currency currency;

    @NotNull
    BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    FinanceOperationType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    RepeatPeriod repeatPeriod;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    Category category;
}
