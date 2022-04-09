package ru.gontarenko.webgateway.rest.mapper;

import org.mapstruct.Mapper;
import ru.gontarenko.feignclients.financeservice.dto.SaveFinanceOperationCommand;
import ru.gontarenko.webgateway.rest.dto.SaveFinanceOperationWebCommand;

@Mapper
public interface FinanceOperationWebMapper {
    SaveFinanceOperationCommand command(SaveFinanceOperationWebCommand command);
}
