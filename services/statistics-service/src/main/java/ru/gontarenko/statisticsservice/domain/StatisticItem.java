package ru.gontarenko.statisticsservice.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticItem {
    LocalDate date;
    BigDecimal amount;
    String categoryName;
    boolean income;

    public StatisticItem(
            LocalDate date,
            BigDecimal amount,
            String categoryName,
            boolean income
    ) {
        setDate(date);
        setAmount(amount);
        setCategoryName(categoryName);
        setIncome(income);
    }
}
