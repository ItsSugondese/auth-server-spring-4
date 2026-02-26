package com.lazy.authserver.annotations.dtovalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequiredIfIdNullValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredIfIdNull {
    String triggerField();        // e.g. "id"
    String[] requiredFields();    // e.g. {"fileId", "externalId", "reference"}

//    String message() default
//            "At least one of {requiredFields} must be provided when {triggerField} is null";
    String message() default
        "Providing values for required fields: {requiredFields} is must";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
