package ru.gontarenko.webgateway.rest.mapper;

import org.mapstruct.Mapper;
import ru.gontarenko.feignclients.accountservice.dto.AccountDto;
import ru.gontarenko.webgateway.rest.dto.AccountWebDto;

@Mapper
public interface AccountWebMapper {
    AccountWebDto dto(AccountDto dto);
}
