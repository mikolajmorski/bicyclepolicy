package com.morski.proofit.bicyclepolicy.service;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class GroovyService {

    private final String NAME_OF_SCRIPT;
    private final GroovyScriptEngine engine;

    public GroovyService(
            @Value("${script.path}") String pathToScript,
            @Value("${script.name}") String nameOfScript
    ) throws IOException {
        this.NAME_OF_SCRIPT = nameOfScript;
        this.engine = new GroovyScriptEngine(pathToScript);
    }


    public ArrayList<LinkedHashMap<String, Serializable>> getAgeFactorData() throws ScriptException, ResourceException {
        return getData("getAgeFactorData");
    }

    public ArrayList<LinkedHashMap<String, Serializable>> getRiskCountFactorData() throws ScriptException, ResourceException {
        return getData("getRiskCountFactorData");

    }

    public ArrayList<LinkedHashMap<String, Serializable>> getSumInsuredFactorData() throws ScriptException, ResourceException {
        return getData("getSumInsuredFactorData");
    }

    public ArrayList<LinkedHashMap<String, Serializable>> getRiskBasePremiumData() throws ScriptException, ResourceException {
        return getData("getRiskBasePremiumData");
    }

    private ArrayList<LinkedHashMap<String, Serializable>> getData(String methodName) throws ScriptException, ResourceException {
        Object result = engine
                .createScript(NAME_OF_SCRIPT, new Binding())
                .invokeMethod(methodName, new Object[]{});
        return (ArrayList<LinkedHashMap<String, Serializable>>) result;
    }


}
