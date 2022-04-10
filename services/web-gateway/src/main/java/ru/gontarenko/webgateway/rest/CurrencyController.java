package ru.gontarenko.webgateway.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.financeservice.CurrencyClient;
import ru.gontarenko.feignclients.financeservice.dto.CurrencyDto;

import java.util.List;

@RestController
@RequestMapping(CurrencyClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyController {
    CurrencyClient client;

    @GetMapping
    List<CurrencyDto> getAll() {
        return client.getAll();
    }
}
