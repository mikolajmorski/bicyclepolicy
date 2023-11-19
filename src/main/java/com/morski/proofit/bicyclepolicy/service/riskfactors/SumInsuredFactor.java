package com.morski.proofit.bicyclepolicy.service.riskfactors;

import com.morski.proofit.bicyclepolicy.service.GroovyService;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class SumInsuredFactor {

    private final GroovyService groovyService;
    public double get(double sumInsuredActual) throws ScriptException, ResourceException {
        ArrayList<LinkedHashMap<String, Serializable>> sumInsuredFactorData = groovyService.getSumInsuredFactorData();

        return sumInsuredFactorData.stream()
                .filter(entry -> {
                    double valueFrom = ((Number) entry.get("VALUE_FROM")).doubleValue();
                    double valueTo = ((Number) entry.get("VALUE_TO")).doubleValue();
                    return sumInsuredActual >= valueFrom && sumInsuredActual <= valueTo;
                })
                .findFirst()
                .map(entry -> calculateSumInsuredFactor(
                        (int) entry.get("VALUE_FROM"),
                        (int) entry.get("VALUE_TO"),
                        ((Number) entry.get("FACTOR_MIN")).doubleValue(),
                        ((Number) entry.get("FACTOR_MAX")).doubleValue(),
                        sumInsuredActual))
                .orElseThrow(() -> new IllegalArgumentException("No factor found for sum insured: " + sumInsuredActual));
    }
    private static double calculateSumInsuredFactor(int valueFrom, int valueTo, double factorMin, double factorMax, double sumInsuredActual) {
        return factorMax - (factorMax - factorMin) * (valueTo - sumInsuredActual) / (valueTo - valueFrom);
    }
}
