package ru.gontarenko.feignclients.financeservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.gontarenko.values.FinanceOperationType;
import ru.gontarenko.values.Period;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceOperationDto {
    Long id;
    LocalDate date;
    Integer currencyId;
    String currencyCode;
    BigDecimal amount;
    FinanceOperationType type;
    Period period;
    String categoryName;
}
