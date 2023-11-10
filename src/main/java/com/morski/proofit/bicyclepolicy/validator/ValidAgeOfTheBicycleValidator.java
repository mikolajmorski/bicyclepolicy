package com.morski.proofit.bicyclepolicy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.Year;

public class ValidAgeOfTheBicycleValidator implements ConstraintValidator<ValidAgeOfTheBicycle.Valid, Integer> {

    @Value("${max.age.of.bicycle}")
    private int maxAgeOfBicycle;

    int current_year = Year.now().getValue();

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        // leave null-checking to @NotNull
        if (value == null) {
            return true;
        }
        formatMessage(context);
        int ageOfBicycle = current_year - value;
        boolean isNotToOld = ageOfBicycle <= maxAgeOfBicycle;
        boolean isNotToYoung = ageOfBicycle >= 0;
        return isNotToOld && isNotToYoung;
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String msg = context.getDefaultConstraintMessageTemplate();
        String formattedMsg = String.format(msg, this.maxAgeOfBicycle, this.current_year);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg).addConstraintViolation();
    }
}
