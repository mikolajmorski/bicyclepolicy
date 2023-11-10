package com.morski.proofit.bicyclepolicy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.morski.proofit.bicyclepolicy.validator.ValidAgeOfTheBicycle;
import com.morski.proofit.bicyclepolicy.validator.ValidSumInsured;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record Bicycle(String make,
                      String model,
                      String coverage,
                      @NotNull
                      @ValidAgeOfTheBicycle.ValidAge
                      int manufactureYear,
                      @ValidSumInsured.Valid
                      double sumInsured,
                      @JsonProperty("risks") List<String> riskNames) {

}