package ru.gontarenko.financeservice.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@ToString
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyExchangeRateResponse {
    LocalDate date;
    Instant timestamp;
    String base;
    Map<String, Double> rates;

    public BigDecimal getRubToEur() {
        return BigDecimal.valueOf(rates.get("EUR"));
    }

    public BigDecimal getRubToUsd() {
        return BigDecimal.valueOf(rates.get("USD"));
    }
}

