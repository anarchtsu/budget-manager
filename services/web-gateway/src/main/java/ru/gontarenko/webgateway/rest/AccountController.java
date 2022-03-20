package ru.gontarenko.webgateway.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.accountservice.AccountClient;
import ru.gontarenko.webgateway.rest.dto.AccountWebDto;
import ru.gontarenko.webgateway.rest.mapper.AccountWebMapper;
import ru.gontarenko.webgateway.security.AuthorizedAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(AccountClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountClient client;
    PasswordEncoder passwordEncoder;
    AccountWebMapper mapper;

    @GetMapping("current")
    AccountWebDto getCurrent() {
        return mapper.dto(AuthorizedAccount.getCurrent());
    }

    @PostMapping(params = {"email", "password"})
    AccountWebDto create(@RequestParam String email, @RequestParam String password) {
        val account = client.create(email, passwordEncoder.encode(password));
        return mapper.dto(account);
    }

    @PatchMapping(params = "password")
    void changePassword(
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        client.changePassword(AuthorizedAccount.getId(), passwordEncoder.encode(password));
        logout(request, response);
    }

    @DeleteMapping
    void delete(HttpServletRequest request, HttpServletResponse response) {
        client.delete(AuthorizedAccount.getId());
        logout(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        val auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }
}
