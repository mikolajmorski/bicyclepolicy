package com.morski.proofit.bicyclepolicy.validator;

import com.morski.proofit.bicyclepolicy.configuration.CoverageProperties;
import com.morski.proofit.bicyclepolicy.configuration.Coverage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ValidCoverageValidator implements ConstraintValidator<ValidCoverage.Valid, String> {

    private final CoverageProperties coverageProperties;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        formatMessage(context);
        List<Coverage> allowedCoverages = coverageProperties.types();
        return allowedCoverages.stream().map(Coverage::name).anyMatch(name -> name.equals(value));
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String msg = context.getDefaultConstraintMessageTemplate();
        String allCoverages = coverageProperties.types()
                            .stream()
                            .map(Coverage::name)
                            .collect(Collectors.joining(", "));
        String formattedMsg = MessageFormat.format(msg, allCoverages);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg).addConstraintViolation();
    }

}
