package com.morski.proofit.bicyclepolicy.controller;

import com.morski.proofit.bicyclepolicy.dto.request.PolicyRequestDTO;
import com.morski.proofit.bicyclepolicy.dto.response.PolicyResponseDTO;
import com.morski.proofit.bicyclepolicy.model.Bicycle;
import com.morski.proofit.bicyclepolicy.model.Policy;
import com.morski.proofit.bicyclepolicy.model.Risk;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class CalculateController {

    @PostMapping("/calculate")
    public PolicyResponseDTO calculate(@RequestBody PolicyRequestDTO policyRequestDTO) {
        ArrayList<Policy> policies = createPolicies(policyRequestDTO.bicycles());
        double premium = policies.stream().mapToDouble(Policy::getPremium).sum();
        return new PolicyResponseDTO(policies, premium);
    }

    private ArrayList<Policy> createPolicies(List<Bicycle> bicycles){
        ArrayList<Policy> policies = new ArrayList<>();
        bicycles.forEach(bicycle ->{
            ArrayList<Risk> risks = calculateRisks(bicycle);
            policies.add(
                new Policy(bicycle,
                        bicycle.coverage(),
                        risks,
                        risks.stream().mapToDouble(Risk::sumInsured).sum(),
                        risks.stream().mapToDouble(Risk::premium).sum()
                        ));
        });
        return policies;
    }
    private ArrayList<Risk> calculateRisks(Bicycle bicycle){
        ArrayList<Risk> risks = new ArrayList<>();
        bicycle.riskNames().forEach(riskName -> risks.add(
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
        return bicycle.sumInsured() * randomValue;
    }

    private double calculatePremium(Bicycle bicycle, String riskName) {
        //TODO implement logic
        return 150;
    }

}
