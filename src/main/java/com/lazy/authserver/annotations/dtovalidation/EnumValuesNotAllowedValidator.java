package com.lazy.authserver.annotations.dtovalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValuesNotAllowedValidator
        implements ConstraintValidator<EnumValuesNotAllowed, Enum<?>> {

    private Set<String> forbiddenValues;

    @Override
    public void initialize(EnumValuesNotAllowed constraintAnnotation) {
        forbiddenValues = Arrays.stream(constraintAnnotation.forbiddenValues())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {

        // null values are usually considered valid
        if (value == null) {
            return true;
        }

        // ❌ If forbidden enum value is passed → FAIL
        return !forbiddenValues.contains(value.name());
    }
}
