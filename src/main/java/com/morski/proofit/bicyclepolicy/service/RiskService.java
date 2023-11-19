package com.morski.proofit.bicyclepolicy.service;

import com.morski.proofit.bicyclepolicy.RiskEnum;
import com.morski.proofit.bicyclepolicy.model.Bicycle;
import com.morski.proofit.bicyclepolicy.model.Risk;
import com.morski.proofit.bicyclepolicy.service.riskfactors.AgeFactor;
import com.morski.proofit.bicyclepolicy.service.riskfactors.RiskBasePremiumFactor;
import com.morski.proofit.bicyclepolicy.service.riskfactors.RiskCountFactor;
import com.morski.proofit.bicyclepolicy.service.riskfactors.SumInsuredFactor;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RiskService {
    private final RiskBasePremiumFactor riskBasePremiumFactor;
    private final SumInsuredFactor sumInsuredFactor;
    private final AgeFactor ageFactor;
    private final RiskCountFactor riskCountFactor;

    public ArrayList<Risk> calculateRisks(Bicycle bicycle) {
        ArrayList<Risk> risks = new ArrayList<>();
        bicycle.getRiskNames().forEach(riskName -> {
            double sumInsured = calculateSumInsured(bicycle, riskName);
            try {
                risks.add(
                    new Risk(riskName,
                            sumInsured,
                            calculatePremium(bicycle, riskName, sumInsured)
                    ));
            } catch (ScriptException | ResourceException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        return risks;
    }
    private double calculateSumInsured(Bicycle bicycle, String riskName) {
        RiskEnum risk = RiskEnum.fromName(riskName);

        return switch (risk) {
            case THEFT -> bicycle.getSumInsured();
            case DAMAGE -> bicycle.getSumInsured() * 0.5;
            case THIRD_PARTY_DAMAGE -> 100;
        };
    }
    private double calculatePremium(Bicycle bicycle, String riskName, double sumInsured) throws ScriptException, ResourceException, IOException {
        RiskEnum risk = RiskEnum.fromName(riskName);

        return switch (risk) {
            case THEFT -> riskBasePremiumFactor.get(riskName) * sumInsuredFactor.get(sumInsured);
            case DAMAGE ->
                    riskBasePremiumFactor.get(riskName) * sumInsuredFactor.get(sumInsured) * ageFactor.get(bicycle);
            case THIRD_PARTY_DAMAGE ->
                    riskBasePremiumFactor.get(riskName) * sumInsuredFactor.get(sumInsured) * riskCountFactor.get(bicycle.getRiskNames().size());
        };

    }
}