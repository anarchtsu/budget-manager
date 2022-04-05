package ru.gontarenko.webgateway.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.financeservice.FinanceOperationClient;
import ru.gontarenko.webgateway.rest.dto.FinanceOperationWebDto;
import ru.gontarenko.webgateway.rest.mapper.FinanceOperationWebMapper;
import ru.gontarenko.webgateway.security.AuthorizedAccount;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(FinanceOperationClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FinanceOperationController {
    FinanceOperationClient client;
    FinanceOperationWebMapper mapper;

    @GetMapping
    List<FinanceOperationWebDto> getAll() {
        return mapper.dtos(client.getAllByAccountId(AuthorizedAccount.getId()));
    }

    @GetMapping(params = {"dateFrom", "dateTo"})
    List<FinanceOperationWebDto> getAllByDateBetweenAndAccountId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        return mapper.dtos(client.getAllByDateBetweenAndAccountId(dateFrom, dateTo, AuthorizedAccount.getId()));
    }

    @PostMapping
    FinanceOperationWebDto create(@RequestBody FinanceOperationWebDto dto) {
        val command = mapper.command(dto);
        command.setAccountId(AuthorizedAccount.getId());
        return mapper.dto(client.create(command));
    }

    @PutMapping("{id}")
    FinanceOperationWebDto update(@PathVariable Long id, @RequestBody FinanceOperationWebDto dto) {
        val command = mapper.command(dto);
        command.setAccountId(AuthorizedAccount.getId());
        return mapper.dto(client.update(id, command));
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        client.delete(id);
    }
}
