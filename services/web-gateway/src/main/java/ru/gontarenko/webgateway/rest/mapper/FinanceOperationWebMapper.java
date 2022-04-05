package ru.gontarenko.webgateway.rest.mapper;

import org.mapstruct.Mapper;
import ru.gontarenko.feignclients.financeservice.dto.FinanceOperationDto;
import ru.gontarenko.webgateway.rest.dto.FinanceOperationWebDto;

import java.util.List;

@Mapper
public interface FinanceOperationWebMapper {
    FinanceOperationWebDto dto(FinanceOperationDto dto);

    List<FinanceOperationWebDto> dtos(List<FinanceOperationDto> dto);

    FinanceOperationDto command(FinanceOperationWebDto dto);
}
