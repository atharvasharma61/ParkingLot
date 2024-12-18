package org.parkinglot.service;


import org.parkinglot.enums.DisplayType;
import org.parkinglot.enums.VehicleType;
import org.parkinglot.exception.InvalidTicketException;
import org.parkinglot.exception.ParkingLotFullException;
import org.parkinglot.model.Floor;
import org.parkinglot.model.Slot;
import org.parkinglot.model.Ticket;
import org.parkinglot.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ParkingLotService {
    private String parkingLotId;
    private List<Floor> floors;
    private Map<String, Ticket> tickets;

    public ParkingLotService(String parkingLotId, int numberOfFloors, int slotsPerFloor) {
        this.parkingLotId = parkingLotId;
        this.floors = new ArrayList<>();
        this.tickets = new HashMap<>();

        for (int i = 1; i <= numberOfFloors; i++) {
            floors.add(new Floor(i, slotsPerFloor));
        }
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (Floor floor : floors) {
            for (Slot slot : floor.getSlotsByType(vehicle.getType())) {
                if (!slot.isOccupied()) {
                    slot.parkVehicle(vehicle);
                    String ticketId = generateTicketId(floor.getFloorNumber(), slot.getSlotNumber());
                    Ticket ticket = new Ticket(ticketId, vehicle);
                    tickets.put(ticketId, ticket);
                    return ticket;
                }
            }
        }
        throw new ParkingLotFullException("Parking lot is full");
    }

    public Vehicle unparkVehicle(String ticketId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null) {
            throw new InvalidTicketException("Invalid ticket");
        }

        String[] parts = ticketId.split("_");
        int floorNo = Integer.parseInt(parts[1]);
        int slotNo = Integer.parseInt(parts[2]);

        Floor floor = floors.get(floorNo - 1);
        Slot slot = floor.getSlots().get(slotNo - 1);

        if (!slot.isOccupied()) {
            throw new InvalidTicketException("Invalid ticket");
        }

        Vehicle vehicle = slot.removeVehicle();
        tickets.remove(ticketId);
        return vehicle;
    }

    public void display(DisplayType displayType, VehicleType vehicleType) {
        for (Floor floor : floors) {
            List<Slot> typeSlots = floor.getSlotsByType(vehicleType);

            switch (displayType) {
                case FREE_COUNT -> {
                displayFreeCount(floor, typeSlots, vehicleType);
                }
                case FREE_SLOTS -> {
                    displayFreeSlots(floor, typeSlots, vehicleType);
                }
                case OCCUPIED_SLOTS -> {
                    displayOccupiedSlots(floor, typeSlots, vehicleType);
                }
            }
        }
    }

    private void displayFreeCount(Floor floor, List<Slot> slots, VehicleType vehicleType) {
        long freeCount = slots.stream()
                .filter(slot -> !slot.isOccupied())
                .count();
        System.out.println("No. of free slots for " + vehicleType +
                " on Floor " + floor.getFloorNumber() + ": " + freeCount);
    }

    private void displayFreeSlots(Floor floor, List<Slot> slots, VehicleType vehicleType) {
        String freeSlots = slots.stream()
                .filter(slot -> !slot.isOccupied())
                .map(slot -> String.valueOf(slot.getSlotNumber()))
                .collect(Collectors.joining(", "));
        System.out.println("Free slots for " + vehicleType +
                " on Floor " + floor.getFloorNumber() + ": " + freeSlots);
    }

    private void displayOccupiedSlots(Floor floor, List<Slot> slots, VehicleType vehicleType) {
        String occupiedSlots = slots.stream()
                .filter(Slot::isOccupied)
                .map(slot -> String.valueOf(slot.getSlotNumber()))
                .collect(Collectors.joining(", "));
        System.out.println("Occupied slots for " + vehicleType +
                " on Floor " + floor.getFloorNumber() + ": " + occupiedSlots);
    }

    private String generateTicketId(int floorNumber, int slotNumber) {
        return String.format("%s_%d_%d", parkingLotId, floorNumber, slotNumber);
    }
}