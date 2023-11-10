package com.morski.proofit.bicyclepolicy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@JsonPropertyOrder({"attributes", "coverageType"})
public class Policy {
    @JsonProperty("attributes")
    private final BicycleDetails bicycleDetails;

    private final String coverageType;

    private final ArrayList<Risk> risks;

    private final double sumInsured;

    private final double premium;

    public Policy(Bicycle bicycle, String coverageType, ArrayList<Risk> risks, double sumInsured, double premium) {
        this.bicycleDetails = new BicycleDetails(bicycle);
        this.coverageType = coverageType;
        this.risks = risks;
        this.sumInsured = sumInsured;
        this.premium = premium;
    }

    @Getter
    private static class BicycleDetails {
        @JsonProperty("MANUFACTURE_YEAR")
        private final int manufactureYear;
        @JsonProperty("MODEL")
        private final String model;
        @JsonProperty("MAKE")
        private final String make;

        public BicycleDetails(Bicycle bicycle) {
            this.manufactureYear = bicycle.getManufactureYear();
            this.model = bicycle.getModel();
            this.make = bicycle.getMake();
        }

    }
}
