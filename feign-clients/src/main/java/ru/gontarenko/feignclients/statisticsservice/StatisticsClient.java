package ru.gontarenko.feignclients.statisticsservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gontarenko.feignclients.statisticsservice.dto.StatisticsDto;

import java.time.LocalDate;

@FeignClient(value = "statistics-service", path = StatisticsClient.PATH)
public interface StatisticsClient {
    String PATH = "/api/v1/statistics";

    @GetMapping(params = {"dateFrom", "dateTo", "accountId", "currencyId"})
    StatisticsDto getByDateBetween(
            @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(value = "accountId") Integer accountId,
            @RequestParam(value = "currencyId") Integer currencyId
    );
}
