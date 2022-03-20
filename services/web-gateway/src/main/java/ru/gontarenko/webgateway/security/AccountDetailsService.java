package ru.gontarenko.webgateway.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gontarenko.feignclients.accountservice.AccountClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountDetailsService implements UserDetailsService {
    AccountClient accountClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return AuthorizedAccount.of(accountClient.getByEmail(email));
    }
}
