package ru.gontarenko.feignclients.accountservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
    Integer id;
    String email;
    String password;
}
