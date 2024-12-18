package org.parkinglot.enums;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR("Car"),
    BIKE("Bike"),
    TRUCK("Truck");

    private final String value;

    VehicleType(String value) {
        this.value = value;
    }

    // Convert string to enum safely
    public static VehicleType fromString(String text) {
        for (VehicleType type : VehicleType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
