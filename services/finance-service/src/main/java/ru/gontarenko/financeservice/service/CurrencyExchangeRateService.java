package ru.gontarenko.financeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gontarenko.financeservice.domain.CurrencyExchangeRate;
import ru.gontarenko.financeservice.service.dto.CurrencyExchangeRateResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyExchangeRateService {
    private static final long ONE_HOUR = 3_600_000;
    private static final CurrencyExchangeRate currencyExchangeCache = new CurrencyExchangeRate();
    ObjectMapper objectMapper;
    HttpClient httpClient;
    HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.cbr-xml-daily.ru/latest.js"))
            .GET()
            .build();

    @Scheduled(fixedRate = ONE_HOUR)
    protected void updateCurrencyRate() {
        try {
            val responseBody = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
            val currencyExchangeRateResponse = objectMapper.readValue(responseBody, CurrencyExchangeRateResponse.class);
            currencyExchangeCache.update(
                    currencyExchangeRateResponse.getRubToEur(),
                    currencyExchangeRateResponse.getRubToUsd()
            );
            log.info("The exchange rate has been successfully updated.");
        } catch (IOException | InterruptedException e) {
            log.info("An error occurred while updating the exchange rate. Error message: {}.", e.getMessage());
        }
    }
}
