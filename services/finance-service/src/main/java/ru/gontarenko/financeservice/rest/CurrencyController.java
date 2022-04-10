package ru.gontarenko.financeservice.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gontarenko.feignclients.financeservice.CurrencyClient;
import ru.gontarenko.feignclients.financeservice.dto.CurrencyDto;
import ru.gontarenko.financeservice.mapper.CurrencyMapper;
import ru.gontarenko.financeservice.repository.CurrencyRepository;

import java.util.List;

@RestController
@RequestMapping(CurrencyClient.PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyController implements CurrencyClient {
    CurrencyRepository repository;
    CurrencyMapper mapper;

    @Override
    public CurrencyDto getById(Integer id) {
        return mapper.dto(repository.getById(id));
    }

    @Override
    public List<CurrencyDto> getAll() {
        return mapper.dtos(repository.findAll());
    }
}
