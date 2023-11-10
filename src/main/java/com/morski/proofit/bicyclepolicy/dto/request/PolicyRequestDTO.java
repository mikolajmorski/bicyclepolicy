package com.morski.proofit.bicyclepolicy.dto.request;

import com.morski.proofit.bicyclepolicy.model.Bicycle;
import jakarta.validation.Valid;

import java.util.List;

public record PolicyRequestDTO(@Valid
                               List<Bicycle> bicycles) {

}

