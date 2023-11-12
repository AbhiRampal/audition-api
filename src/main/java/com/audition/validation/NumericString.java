package com.audition.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
//@Pattern(regexp = "^[0-9]+$", message = "Must be a numeric string")
@Constraint(validatedBy = InputParamValidator.class)
public @interface NumericString {

    String message() default "Must be a numeric string.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
