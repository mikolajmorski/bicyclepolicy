package com.morski.proofit.bicyclepolicy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record CoverageProperties(@Value("#{'${coverage.types}'.split(',')}") List<Coverage> types) {}
