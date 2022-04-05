package ru.gontarenko.financeservice.mapper;

import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gontarenko.feignclients.financeservice.dto.FinanceOperationDto;
import ru.gontarenko.financeservice.domain.Category;
import ru.gontarenko.financeservice.domain.Currency;
import ru.gontarenko.financeservice.domain.FinanceOperation;
import ru.gontarenko.financeservice.repository.CategoryRepository;
import ru.gontarenko.financeservice.repository.CurrencyRepository;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Mapper
public abstract class FinanceOperationMapper {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Mapping(target = "categoryName", source = "category.name")
    public abstract FinanceOperationDto dto(FinanceOperation financeOperation);

    public abstract List<FinanceOperationDto> dtos(List<FinanceOperation> financeOperation);

    public void update(FinanceOperation financeOperation, FinanceOperationDto dto) {
        val category = categoryRepository.findByName(dto.getCategoryName())
                .orElse(new Category(dto.getCategoryName()));
        val currency = currencyRepository.findByCode(dto.getCurrencyCode())
                .orElseThrow(() -> new ConstraintViolationException("Currency not found, code: " + dto.getCurrencyCode(), null));
        update(financeOperation, dto, category, currency);
    }

    @Mapping(target = "id", ignore = true)
    protected abstract void update(@MappingTarget FinanceOperation financeOperation,
                                   FinanceOperationDto dto,
                                   Category category,
                                   Currency currency);
}
