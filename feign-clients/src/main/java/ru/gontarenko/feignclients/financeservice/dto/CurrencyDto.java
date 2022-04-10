package ru.gontarenko.feignclients.financeservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyDto {
    Integer id;
    String code;
}
