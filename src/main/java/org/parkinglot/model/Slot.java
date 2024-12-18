package org.parkinglot.model;

import lombok.Data;
import org.parkinglot.enums.VehicleType;

@Data
public class Slot {
    private int slotNumber;
    private VehicleType type;
    private Vehicle vehicle;
    private boolean isOccupied;

    public Slot(int slotNumber, VehicleType type) {
        this.slotNumber = slotNumber;
        this.type = type;
        this.isOccupied = false;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public Vehicle removeVehicle() {
        Vehicle removedVehicle = this.vehicle;
        this.vehicle = null;
        this.isOccupied = false;
        return removedVehicle;
    }
}