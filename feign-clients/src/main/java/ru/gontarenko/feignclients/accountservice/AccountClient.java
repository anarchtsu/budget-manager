package ru.gontarenko.feignclients.accountservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gontarenko.feignclients.accountservice.dto.AccountDto;

@FeignClient(value = "account-service", path = AccountClient.PATH)
public interface AccountClient {
    String PATH = "/api/v1/accounts";

    @GetMapping(params = "email")
    AccountDto getByEmail(@RequestParam(value = "email") String email);

    @PostMapping(params = {"email", "password"})
    AccountDto create(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    );

    @PutMapping(value = "{id}", params = "password")
    AccountDto changePassword(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "password") String password
    );

    @DeleteMapping("{id}")
    void delete(@PathVariable(value = "id") Integer id);
}
