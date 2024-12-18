package org.parkinglot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.parkinglot.enums.VehicleType;

@Data
@AllArgsConstructor
public class Vehicle {
    private VehicleType type;
    private String registrationNumber;
    private String color;
}
