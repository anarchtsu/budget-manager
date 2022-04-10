package ru.gontarenko.feignclients.financeservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gontarenko.feignclients.financeservice.dto.CurrencyDto;

import java.util.List;

@FeignClient(value = "finance-service", path = CurrencyClient.PATH, contextId = "currencies")
public interface CurrencyClient {
    String PATH = "/api/v1/currencies";

    @GetMapping("{id}")
    CurrencyDto getById(@PathVariable(value = "id") Integer id);

    @GetMapping
    List<CurrencyDto> getAll();
}
