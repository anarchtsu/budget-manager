package ru.gontarenko.feignclients.statisticsservice.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyStatistic {
    LocalDate date;
    BigDecimal incomesAmount;
    BigDecimal expensesAmount;
    Map<String, BigDecimal> categoryStatistics;
}
