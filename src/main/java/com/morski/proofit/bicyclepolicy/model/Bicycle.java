package com.morski.proofit.bicyclepolicy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Bicycle( String make,
                      String model,
                       String coverage,
                       int manufactureYear,
                       double sumInsured,
                      @JsonProperty("risks") List<String> riskNames) {
}