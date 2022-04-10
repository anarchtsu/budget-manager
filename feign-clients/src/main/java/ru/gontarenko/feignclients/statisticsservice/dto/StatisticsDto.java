package ru.gontarenko.feignclients.statisticsservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticsDto {
    BigDecimal incomesAmount;
    BigDecimal expensesAmount;
    List<DailyStatistic> dailyStatistics;
}
