package com.morski.proofit.bicyclepolicy;

public enum RiskEnum {
    THEFT("Theft"),
    DAMAGE("Damage"),
    THIRD_PARTY_DAMAGE("THIRD_PARTY_DAMAGE");

    private final String name;

    RiskEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RiskEnum fromName(String name) {
        for (RiskEnum risk : RiskEnum.values()) {
            if (risk.getName().equalsIgnoreCase(name)) {
                return risk;
            }
        }
        throw new IllegalArgumentException("Unknown risk name: " + name);
    }
}
