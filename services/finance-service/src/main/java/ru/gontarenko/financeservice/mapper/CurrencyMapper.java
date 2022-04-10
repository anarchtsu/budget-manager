package ru.gontarenko.financeservice.mapper;

import org.mapstruct.Mapper;
import ru.gontarenko.feignclients.financeservice.dto.CurrencyDto;
import ru.gontarenko.financeservice.domain.Currency;

import java.util.List;

@Mapper
public interface CurrencyMapper {
    CurrencyDto dto(Currency currency);

    List<CurrencyDto> dtos(List<Currency> currencies);
}
