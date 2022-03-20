package ru.gontarenko.webgateway.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ru.gontarenko.feignclients.accountservice.dto.AccountDto;

import java.util.Collections;
import java.util.Optional;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizedAccount extends User {
    AccountDto account;

    public static AuthorizedAccount of(AccountDto account) {
        return new AuthorizedAccount(account);
    }

    private AuthorizedAccount(AccountDto account) {
        super(account.getEmail(), account.getPassword(), Collections.emptyList());
        this.account = account;
    }

    public static Integer getId() {
        return get()
                .map(AuthorizedAccount::getAccount)
                .map(AccountDto::getId)
                .orElse(null);
    }

    public static AccountDto getCurrent() {
        return get()
                .map(AuthorizedAccount::getAccount)
                .orElse(null);
    }

    private static Optional<AuthorizedAccount> get() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(AuthorizedAccount.class::isInstance)
                .map(AuthorizedAccount.class::cast);
    }
}