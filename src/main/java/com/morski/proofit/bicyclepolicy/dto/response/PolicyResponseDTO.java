package com.morski.proofit.bicyclepolicy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.morski.proofit.bicyclepolicy.model.Policy;

import java.util.ArrayList;

@JsonPropertyOrder({"objects", "premium"})
public record PolicyResponseDTO(@JsonProperty("objects") ArrayList<Policy> policies, double premium) {

}
