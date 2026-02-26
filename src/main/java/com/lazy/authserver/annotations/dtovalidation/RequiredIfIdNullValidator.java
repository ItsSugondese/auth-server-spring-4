package com.lazy.authserver.annotations.dtovalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;


public class RequiredIfIdNullValidator implements ConstraintValidator<RequiredIfIdNull, Object> {

    private String triggerField;
    private String[] requiredFields;

    @Override
    public void initialize(RequiredIfIdNull annotation) {
        this.triggerField = annotation.triggerField();
        this.requiredFields = annotation.requiredFields();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        try {
            Object triggerValue = getFieldValue(dto, triggerField);

            // id is NOT null → valid
            if (triggerValue != null) {
                return true;
            }


            // id is null → check if any required field is present
            for (String field : requiredFields) {
                Object value = getFieldValue(dto, field);
                if (value == null) {

                    context.buildConstraintViolationWithTemplate("Please provide value for " + field + ".")
                            .addPropertyNode(field)
                            .addConstraintViolation();

                    return false;
                }

            }


            return true;
        } catch (Exception e) {
            throw new RuntimeException("Validation error", e);
        }
    }

    private Object getFieldValue(Object obj, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {

        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                // Move to parent class
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException("Field '" + fieldName + "' not found in " + obj.getClass());
    }

}