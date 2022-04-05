package ru.gontarenko.financeservice.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyExchangeRate {
    private static final Set<String> validCodes = Set.of("RUB", "EUR", "USD");
    Map<String, BigDecimal> rubRates = new HashMap<>();
    Map<String, BigDecimal> eurRates = new HashMap<>();
    Map<String, BigDecimal> usdRates = new HashMap<>();

    public void update(BigDecimal rubToEur, BigDecimal rubToUsd) {
        rubRates.put("USD", rubToUsd);
        rubRates.put("EUR", rubToEur);
        eurRates.put("RUB", BigDecimal.ONE.divide(rubToEur, RoundingMode.HALF_UP));
        eurRates.put("USD", rubToUsd.divide(rubToEur, RoundingMode.HALF_UP));
        usdRates.put("RUB", BigDecimal.ONE.divide(rubToUsd, RoundingMode.HALF_UP));
        usdRates.put("EUR", rubToEur.divide(rubToUsd, RoundingMode.HALF_UP));
    }

    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        if (from.getCode().equals(to.getCode()))
            return amount;
        if (!validCodes.contains(to.getCode()))
            throw new UnsupportedOperationException("Unsupported code: " + from.getCode());
        return switch (from.getCode()) {
            case "RUB" -> amount.multiply(rubRates.get(to.getCode()));
            case "EUR" -> amount.multiply(eurRates.get(to.getCode()));
            case "USD" -> amount.multiply(usdRates.get(to.getCode()));
            default -> throw new UnsupportedOperationException("Unsupported code: " + from.getCode());
        };
    }
}
