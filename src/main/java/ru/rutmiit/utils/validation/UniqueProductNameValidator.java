package ru.rutmiit.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.rutmiit.repositories.ProductRepository;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {
    private final ProductRepository productRepository;

    public UniqueProductNameValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return productRepository.findByName(value).isEmpty();
    }
}
