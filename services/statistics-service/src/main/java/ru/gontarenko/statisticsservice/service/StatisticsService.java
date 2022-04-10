package ru.gontarenko.statisticsservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gontarenko.feignclients.financeservice.CurrencyClient;
import ru.gontarenko.feignclients.financeservice.FinanceOperationClient;
import ru.gontarenko.feignclients.statisticsservice.dto.DailyStatistic;
import ru.gontarenko.feignclients.statisticsservice.dto.StatisticsDto;
import ru.gontarenko.statisticsservice.domain.StatisticItem;
import ru.gontarenko.values.FinanceOperationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsService {
    private static final String START_LOG_MESSAGE = "Calculating statistics is started, accountId: {}, finance operation size: {}, period: from {} to {}, base currency: {}.";
    private static final String FINISH_LOG_MESSAGE = "Statistics successfully calculated. IncomesAmount {}, ExpensesAmount {}.";
    FinanceOperationClient financeOperationClient;
    CurrencyClient currencyClient;

    public synchronized StatisticsDto create(
            LocalDate dateFrom,
            LocalDate dateTo,
            Integer accountId,
            Integer currencyId
    ) {
        val currency = currencyClient.getById(currencyId);
        val financeOperations = financeOperationClient.getAllByAccountId(accountId);
        log.info(START_LOG_MESSAGE, accountId, financeOperations.size(), dateFrom, dateTo, currency.getCode());
        val monthPeriodDates = getFirstDateOfEachMonthByDatesBetween(dateFrom, dateTo);
        val yearPeriodDates = getFirstDateOfEachYearByDatesBetween(dateFrom, dateTo);
        val statisticItems = financeOperations.stream()
                .map(fo -> {
                    // todo перевод в валюту переданную пользователем
                    val income = fo.getType() == FinanceOperationType.INCOME;
                    val amount = fo.getAmount();
                    val categoryName = fo.getCategoryName();
                    return switch (fo.getPeriod()) {
                        case NONE -> fo.getDate().isBefore(dateFrom) || fo.getDate().isAfter(dateTo) ?
                                Collections.emptyList() :
                                List.of(new StatisticItem(fo.getDate(), amount, categoryName, income));
                        case DAY -> dateFrom.datesUntil(dateTo.plusDays(1))
                                .map(date -> new StatisticItem(date, amount, categoryName, income))
                                .toList();
                        case MONTH -> monthPeriodDates.stream()
                                .map(date -> new StatisticItem(date, amount, categoryName, income))
                                .toList();
                        case YEAR -> yearPeriodDates.stream()
                                .map(date -> new StatisticItem(date, amount, categoryName, income))
                                .toList();
                    };
                })
                .filter(not(Collection::isEmpty))
                .flatMap(Collection::stream)
                .map(StatisticItem.class::cast)
                .collect(Collectors.toList());
        val dailyStatistics = statisticItems.stream()
                .collect(Collectors.groupingBy(StatisticItem::getDate))
                .entrySet().stream()
                .map(entry -> {
                    val dailyStatistic = new DailyStatistic();
                    dailyStatistic.setDate(entry.getKey());
                    val statisticItemList = entry.getValue();
                    dailyStatistic.setIncomesAmount(
                            statisticItemList.stream()
                                    .filter(StatisticItem::isIncome)
                                    .map(StatisticItem::getAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    );
                    dailyStatistic.setExpensesAmount(
                            statisticItemList.stream()
                                    .filter(not(StatisticItem::isIncome))
                                    .map(StatisticItem::getAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    );
                    dailyStatistic.setCategoryStatistics(
                            statisticItemList.stream()
                                    .collect(Collectors.toMap(StatisticItem::getCategoryName, this::toAmount, BigDecimal::add))
                    );
                    return dailyStatistic;
                })
                .sorted(Comparator.comparing(DailyStatistic::getDate))
                .toList();
        val incomesAmount = dailyStatistics.stream()
                .map(DailyStatistic::getIncomesAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        val expensesAmount = dailyStatistics.stream()
                .map(DailyStatistic::getExpensesAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        val statistics = new StatisticsDto();
        statistics.setIncomesAmount(incomesAmount);
        statistics.setExpensesAmount(expensesAmount);
        statistics.setDailyStatistics(dailyStatistics);
        log.info(FINISH_LOG_MESSAGE, incomesAmount, expensesAmount);
        return statistics;
    }

    private List<LocalDate> getFirstDateOfEachMonthByDatesBetween(LocalDate dateFrom, LocalDate dateTo) {
        val firstDates = new ArrayList<LocalDate>();
        for (var month = YearMonth.from(dateFrom); !month.isAfter(YearMonth.from(dateTo)); month = month.plusMonths(1)) {
            val firstDate = month.atDay(1);
            if (!firstDate.isBefore(dateFrom) && !firstDate.isAfter(dateTo)) {
                firstDates.add(firstDate);
            }
        }
        return firstDates;
    }

    private List<LocalDate> getFirstDateOfEachYearByDatesBetween(LocalDate dateFrom, LocalDate dateTo) {
        val firstDates = new ArrayList<LocalDate>();
        for (var year = Year.from(dateFrom); !year.isAfter(Year.from(dateTo)); year = year.plusYears(1)) {
            val firstDate = year.atDay(1);
            if (!firstDate.isBefore(dateFrom) && !firstDate.isAfter(dateTo)) {
                firstDates.add(firstDate);
            }
        }
        return firstDates;
    }

    private BigDecimal toAmount(StatisticItem item) {
        return item.isIncome() ?
                item.getAmount() :
                item.getAmount().negate();
    }
}
