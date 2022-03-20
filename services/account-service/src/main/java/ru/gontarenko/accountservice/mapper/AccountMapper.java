package ru.gontarenko.accountservice.mapper;

import org.mapstruct.Mapper;
import ru.gontarenko.accountservice.domain.Account;
import ru.gontarenko.feignclients.accountservice.dto.AccountDto;

@Mapper
public interface AccountMapper {
    AccountDto dto(Account account);
}
