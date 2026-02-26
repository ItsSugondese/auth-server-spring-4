package com.lazy.authserver.annotations.dtovalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValuesNotAllowedValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValuesNotAllowed {

    String[] forbiddenValues(); // enum names that should NOT be allowed

    String message() default "Enum value is not allowed";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
