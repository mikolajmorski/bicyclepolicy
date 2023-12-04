package com.morski.proofit.bicyclepolicy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.Year;

public class ValidAgeOfTheBicycleValidator implements ConstraintValidator<ValidAgeOfTheBicycle.Valid, Integer> {

    @Value("${max.age.of.bicycle}")
    private int maxAgeOfBicycle;

    int currentYear = Year.now().getValue();

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        formatMessage(context);
        int ageOfBicycle = currentYear - value;
        boolean isNotToOld = ageOfBicycle <= maxAgeOfBicycle;
        boolean isNotToYoung = ageOfBicycle >= 0;
        return isNotToOld && isNotToYoung;
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String msg = context.getDefaultConstraintMessageTemplate();
        String formattedMsg = String.format(msg, this.maxAgeOfBicycle, this.currentYear);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg).addConstraintViolation();
    }
}
