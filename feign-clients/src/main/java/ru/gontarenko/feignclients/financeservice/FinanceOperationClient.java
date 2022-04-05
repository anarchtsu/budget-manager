package ru.gontarenko.feignclients.financeservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gontarenko.feignclients.financeservice.dto.FinanceOperationDto;

import java.time.LocalDate;
import java.util.List;

@FeignClient(value = "finance-service", path = FinanceOperationClient.PATH)
public interface FinanceOperationClient {
    String PATH = "/api/v1/finance-operations";

    @GetMapping(params = "accountId")
    List<FinanceOperationDto> getAllByAccountId(@RequestParam(value = "accountId") Integer accountId);

    @GetMapping(params = {"accountId", "dateFrom", "dateTo"})
    List<FinanceOperationDto> getAllByDateBetweenAndAccountId(
            @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(value = "accountId") Integer accountId
    );

    @PostMapping
    FinanceOperationDto create(@RequestBody FinanceOperationDto dto);

    @PutMapping("{id}")
    FinanceOperationDto update(
            @PathVariable(value = "id") Long id,
            @RequestBody FinanceOperationDto dto
    );

    @DeleteMapping("{id}")
    void delete(@PathVariable(value = "id") Long id);
}
