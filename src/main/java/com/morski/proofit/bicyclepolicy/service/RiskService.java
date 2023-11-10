package com.morski.proofit.bicyclepolicy.service;

import com.morski.proofit.bicyclepolicy.model.Bicycle;
import com.morski.proofit.bicyclepolicy.model.Risk;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class RiskService {

    public ArrayList<Risk> calculateRisks(Bicycle bicycle) {
        ArrayList<Risk> risks = new ArrayList<>();
        bicycle.getRiskNames().forEach(riskName -> risks.add(
                new Risk(riskName,
                        calculateSumInsured(bicycle, riskName),
                        calculatePremium(bicycle, riskName)
                )));
        return risks;
    }
    private double calculateSumInsured(Bicycle bicycle, String riskName) {
        //TODO implement logic
        Random rand = new Random();
        double randomValue = 0.2 + (0.9 - 0.2) * rand.nextDouble();
        return bicycle.getSumInsured() * randomValue;
    }

    private double calculatePremium(Bicycle bicycle, String riskName) {
        //TODO implement logic
        return 150;
    }

}
