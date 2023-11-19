package com.morski.proofit.bicyclepolicy.service.riskfactors;

import com.morski.proofit.bicyclepolicy.service.GroovyService;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

@RequiredArgsConstructor
public class RiskBasePremiumFactor {
    private final GroovyService groovyService;

    public Integer get(String riskType) throws ScriptException, ResourceException {
        ArrayList<LinkedHashMap<String, Serializable>> riskBasePremiumData = groovyService.getRiskBasePremiumData();

        Optional<Integer> result = riskBasePremiumData.stream()
                .filter(entry -> riskType.equalsIgnoreCase((String) entry.get("RISK_TYPE")))
                .findFirst()
                .map(entry -> (Integer) entry.get("PREMIUM"));

        return result.orElseThrow(() -> new IllegalArgumentException("No premium found for risk type: " + riskType));
    }
}
