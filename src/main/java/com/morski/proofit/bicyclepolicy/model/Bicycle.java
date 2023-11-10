package com.morski.proofit.bicyclepolicy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.morski.proofit.bicyclepolicy.validator.ValidAgeOfTheBicycle;
import com.morski.proofit.bicyclepolicy.validator.ValidCoverage;
import com.morski.proofit.bicyclepolicy.validator.ValidSumInsured;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class Bicycle {
    @NotBlank(message = "Make can not be blank.")
    private String make;
    @NotBlank(message = "Model can not be blank.")
    private String model;
    @ValidCoverage.Valid
    private String coverage;
    @NotNull
    @ValidAgeOfTheBicycle.Valid
    private int manufactureYear;
    @ValidSumInsured.Valid
    private double sumInsured;
    @JsonProperty("risks")
    private List<String> riskNames;

    public Bicycle() {
        this.coverage = (coverage == null) ? "STANDARD" : coverage;
        this.riskNames = (riskNames == null) ? List.of("THEFT") : riskNames;
    }

}