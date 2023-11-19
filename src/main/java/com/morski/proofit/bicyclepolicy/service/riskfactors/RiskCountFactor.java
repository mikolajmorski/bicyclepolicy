package com.morski.proofit.bicyclepolicy.service.riskfactors;

import com.morski.proofit.bicyclepolicy.service.GroovyService;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class RiskCountFactor {
    private final GroovyService groovyService;

    public double get(int riskCount) throws ScriptException, ResourceException{
        ArrayList<LinkedHashMap<String, Serializable>> riskCountFactorData = groovyService.getRiskCountFactorData();


        Optional<Double> result = riskCountFactorData.stream()
                .filter(entry -> {
                    double valueFrom = ((Number) entry.get("VALUE_FROM")).doubleValue();
                    double valueTo = ((Number) entry.get("VALUE_TO")).doubleValue();
                    return riskCount >= valueFrom && riskCount <= valueTo;
                })
                .findFirst()
                .map(entry -> calculateCountFactor(
                        (int) entry.get("VALUE_FROM"),
                        (int) entry.get("VALUE_TO"),
                        ((Number) entry.get("FACTOR_MIN")).doubleValue(),
                        ((Number) entry.get("FACTOR_MAX")).doubleValue(),
                        riskCount));

        return result.orElseThrow(() -> new IllegalArgumentException("No factor found for risk count: " + riskCount));
    }

    private static double calculateCountFactor(int valueFrom, int valueTo, double factorMin, double factorMax, int riskCount) {
        return factorMax - (factorMax - factorMin) * (valueTo - riskCount) / (valueTo - valueFrom);
    }
}
