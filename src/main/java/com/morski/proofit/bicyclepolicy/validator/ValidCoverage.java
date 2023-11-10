package com.morski.proofit.bicyclepolicy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

public interface ValidCoverage extends Annotation {

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = ValidCoverageValidator.class)
    @interface Valid {
        String message() default "Wrong coverage. Coverage can bo only {0}.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
