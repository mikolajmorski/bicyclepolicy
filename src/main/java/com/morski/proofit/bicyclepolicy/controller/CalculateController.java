package com.morski.proofit.bicyclepolicy.controller;

import com.morski.proofit.bicyclepolicy.dto.request.PolicyRequestDTO;
import com.morski.proofit.bicyclepolicy.dto.response.PolicyResponseDTO;
import com.morski.proofit.bicyclepolicy.model.Policy;
import com.morski.proofit.bicyclepolicy.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
public class CalculateController {

    private final PolicyService policyService;

    @PostMapping("/calculate")
    public PolicyResponseDTO calculate(@Valid @RequestBody PolicyRequestDTO policyRequestDTO) {
        ArrayList<Policy> policies = policyService.createPolicies(policyRequestDTO.bicycles());
        double premium = policies.stream().mapToDouble(Policy::getPremium).sum();
        return new PolicyResponseDTO(policies, premium);
    }


}
