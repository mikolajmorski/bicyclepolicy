package com.morski.proofit.bicyclepolicy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

public interface ValidSumInsured extends Annotation {

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = ValidSumInsuredValidator.class)
    @interface Valid {
        String message() default "Wrong sumInsured. SumInsured can not be grater than %s.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
