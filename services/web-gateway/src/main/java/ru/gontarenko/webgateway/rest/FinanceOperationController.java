package ru.gontarenko.webgateway.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.financeservice.FinanceOperationClient;
import ru.gontarenko.feignclients.financeservice.dto.FinanceOperationDto;
import ru.gontarenko.webgateway.rest.dto.SaveFinanceOperationWebCommand;
import ru.gontarenko.webgateway.rest.mapper.FinanceOperationWebMapper;
import ru.gontarenko.webgateway.security.AuthorizedAccount;

import java.util.List;

@RestController
@RequestMapping(FinanceOperationClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinanceOperationController {
    FinanceOperationClient client;
    FinanceOperationWebMapper mapper;

    @GetMapping
    List<FinanceOperationDto> getAll() {
        return client.getAllByAccountId(AuthorizedAccount.getId());
    }

    @PostMapping
    FinanceOperationDto create(@RequestBody SaveFinanceOperationWebCommand webCommand) {
        val command = mapper.command(webCommand);
        command.setAccountId(AuthorizedAccount.getId());
        return client.create(command);
    }

    @PutMapping("{id}")
    FinanceOperationDto update(@PathVariable Long id, @RequestBody SaveFinanceOperationWebCommand webCommand) {
        System.out.println("id: " + id);
        System.out.println("SaveFinanceOperationWebCommand: " + webCommand);
        val command = mapper.command(webCommand);
        command.setAccountId(AuthorizedAccount.getId());
        return client.update(id, command);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        client.delete(id);
    }
}
