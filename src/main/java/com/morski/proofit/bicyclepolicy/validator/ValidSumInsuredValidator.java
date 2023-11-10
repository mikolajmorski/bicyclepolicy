package com.morski.proofit.bicyclepolicy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class ValidSumInsuredValidator implements ConstraintValidator<ValidSumInsured.Valid, Double> {

    @Value("${max.sum.insured}")
    private double maxSumInsured;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {

        // leave null-checking to @NotNull
        if (value == null) {
            return true;
        }
        formatMessage(context);
        return value <= maxSumInsured;
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String msg = context.getDefaultConstraintMessageTemplate();
        String formattedMsg = String.format(msg, this.maxSumInsured);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg).addConstraintViolation();
    }

}
