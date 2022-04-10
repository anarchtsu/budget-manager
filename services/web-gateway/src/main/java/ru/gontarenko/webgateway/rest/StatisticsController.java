package ru.gontarenko.webgateway.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.statisticsservice.StatisticsClient;
import ru.gontarenko.feignclients.statisticsservice.dto.StatisticsDto;
import ru.gontarenko.webgateway.security.AuthorizedAccount;

import java.time.LocalDate;

@RestController
@RequestMapping(StatisticsClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsController {
    StatisticsClient client;

    @GetMapping
    StatisticsDto getStatisticsByDatesBetween(
            @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(value = "currencyId") Integer currencyId
    ) {
        return client.getByDateBetween(dateFrom, dateTo, AuthorizedAccount.getId(), currencyId);
    }
}
