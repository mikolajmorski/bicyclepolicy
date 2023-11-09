package com.morski.proofit.bicyclepolicy.dto.request;

import com.morski.proofit.bicyclepolicy.model.Bicycle;

import java.util.List;

public record PolicyRequestDTO(List<Bicycle> bicycles) {

}
