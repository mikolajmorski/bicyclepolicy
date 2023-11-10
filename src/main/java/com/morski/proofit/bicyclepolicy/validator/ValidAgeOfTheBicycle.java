package com.morski.proofit.bicyclepolicy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

public interface ValidAgeOfTheBicycle extends Annotation {

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = ValidAgeOfTheBicycleValidator.class)
    @interface ValidAge {
        String message() default "Wrong manufactureYear. Bicycle can not be older than %s years and newer than current year %s.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
