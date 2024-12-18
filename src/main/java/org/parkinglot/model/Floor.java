package org.parkinglot.model;

import lombok.Data;
import org.parkinglot.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Floor {
    private int floorNumber;
    private List<Slot> slots;

    public Floor(int floorNumber, int numberOfSlots) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
        initializeSlots(numberOfSlots);
    }

    private void initializeSlots(int numberOfSlots) {
        // First slot for truck
        slots.add(new Slot(1, VehicleType.TRUCK));

        // Next 2 slots for bikes
        for (int i = 2; i <= 3; i++) {
            slots.add(new Slot(i, VehicleType.BIKE));
        }

        // Remaining slots for cars
        for (int i = 4; i <= numberOfSlots; i++) {
            slots.add(new Slot(i, VehicleType.CAR));
        }
    }

    public List<Slot> getSlotsByType(VehicleType type) {
        return slots.stream()
                .filter(slot -> slot.getType() == type)
                .collect(Collectors.toList());
    }
}
