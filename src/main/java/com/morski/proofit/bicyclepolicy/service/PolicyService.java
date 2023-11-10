package com.morski.proofit.bicyclepolicy.service;

import com.morski.proofit.bicyclepolicy.model.Bicycle;
import com.morski.proofit.bicyclepolicy.model.Policy;
import com.morski.proofit.bicyclepolicy.model.Risk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final RiskService riskService;
    public ArrayList<Policy> createPolicies(List<Bicycle> bicycles){
        ArrayList<Policy> policies = new ArrayList<>();
        bicycles.forEach(bicycle ->{
            ArrayList<Risk> risks = riskService.calculateRisks(bicycle);
            policies.add(
                    new Policy(bicycle,
                            bicycle.getCoverage(),
                            risks,
                            risks.stream().mapToDouble(Risk::sumInsured).sum(),
                            risks.stream().mapToDouble(Risk::premium).sum()
                    ));
        });
        return policies;
    }

}
