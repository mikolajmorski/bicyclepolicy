package com.morski.proofit.bicyclepolicy.service.riskfactors;

import com.morski.proofit.bicyclepolicy.model.Bicycle;
import com.morski.proofit.bicyclepolicy.service.GroovyService;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AgeFactor {

    private final GroovyService groovyService;
    public double get(Bicycle bicycle) throws ScriptException, ResourceException {
        ArrayList<LinkedHashMap<String, Serializable>> ageFactorData = groovyService.getAgeFactorData();

        int ageActual = Calendar.getInstance().get(Calendar.YEAR) - bicycle.getManufactureYear();

        Optional<Double> result = ageFactorData.stream()
                .filter(entry -> isMakeModelAgeMatching(bicycle.getMake(), bicycle.getModel(), ageActual, entry))
                .findFirst()
                .flatMap(entry -> calculateAgeFactorForEntry(ageActual, entry))
                .or(() ->
                        ageFactorData.stream()
                                .filter(entry -> isMakeAndAgeMatching(bicycle.getMake(), ageActual, entry))
                                .findFirst()
                                .flatMap(entry -> calculateAgeFactorForEntry(ageActual, entry)))
                .or(() ->
                        ageFactorData.stream()
                                .filter(entry -> isAgeMatchingOnly(ageActual, entry))
                                .findFirst()
                                .flatMap(entry -> calculateAgeFactorForEntry(ageActual, entry)));

        return result.orElseThrow(() -> new IllegalArgumentException("No factor found for make: " + bicycle.getMake() + ", model: " + bicycle.getModel() + ", age: " + ageActual));
    }
    private static boolean isMakeModelAgeMatching(String make, String model, int ageActual, LinkedHashMap<String, Serializable> entry) {
        String entryMake = (String) entry.get("MAKE");
        String entryModel = (String) entry.get("MODEL");
        int entryValueFrom = (int) entry.get("VALUE_FROM");
        int entryValueTo = (int) entry.get("VALUE_TO");

        boolean makeMatches =  make.equalsIgnoreCase(entryMake);
        boolean modelMatches = model.equalsIgnoreCase(entryModel);
        boolean ageMatches = ageActual >= entryValueFrom && ageActual <= entryValueTo;

        return makeMatches && modelMatches && ageMatches;
    }

    private static boolean isMakeAndAgeMatching(String make, int ageActual, LinkedHashMap<String, Serializable> entry) {
        String entryMake = (String) entry.get("MAKE");
        int entryValueFrom = (int) entry.get("VALUE_FROM");
        int entryValueTo = (int) entry.get("VALUE_TO");

        boolean makeMatches =  make.equalsIgnoreCase(entryMake);
        boolean ageMatches = ageActual >= entryValueFrom && ageActual <= entryValueTo;

        return makeMatches && ageMatches;
    }

    private static boolean isAgeMatchingOnly(int ageActual, LinkedHashMap<String, Serializable> entry) {
        String entryMake = (String) entry.get("MAKE");
        String entryModel = (String) entry.get("MODEL");

        if ((entryMake == null || entryMake.isEmpty()) && (entryModel == null || entryModel.isEmpty())) {
            int entryValueFrom = (int) entry.get("VALUE_FROM");
            int entryValueTo = (int) entry.get("VALUE_TO");

            return ageActual >= entryValueFrom && ageActual <= entryValueTo;
        }

        return false;
    }

    private static Optional<Double> calculateAgeFactorForEntry(int ageActual, LinkedHashMap<String, Serializable> entry) {
        return Optional.ofNullable(entry)
                .map(e -> calculateAgeFactor(
                        (int) e.get("VALUE_FROM"),
                        (int) e.get("VALUE_TO"),
                        ((Number) e.get("FACTOR_MIN")).doubleValue(),
                        ((Number) e.get("FACTOR_MAX")).doubleValue(),
                        ageActual));
    }

    private static double calculateAgeFactor(int valueFrom, int valueTo, double factorMin, double factorMax, int ageActual) {
        return factorMax - (factorMax - factorMin) * (valueTo - ageActual) / (valueTo - valueFrom);
    }

}
