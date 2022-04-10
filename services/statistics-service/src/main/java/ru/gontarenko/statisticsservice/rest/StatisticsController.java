package ru.gontarenko.statisticsservice.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.statisticsservice.StatisticsClient;
import ru.gontarenko.feignclients.statisticsservice.dto.StatisticsDto;
import ru.gontarenko.statisticsservice.service.StatisticsService;

import java.time.LocalDate;

@RestController
@RequestMapping(StatisticsClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsController implements StatisticsClient {
    StatisticsService service;

    @Override
    public StatisticsDto getByDateBetween(
            LocalDate dateFrom,
            LocalDate dateTo,
            Integer accountId,
            Integer currencyId
    ) {
        return service.create(dateFrom, dateTo, accountId, currencyId);
    }
}
