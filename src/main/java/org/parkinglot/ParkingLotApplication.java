package org.parkinglot;

import org.parkinglot.enums.DisplayType;
import org.parkinglot.enums.VehicleType;
import org.parkinglot.exception.InvalidTicketException;
import org.parkinglot.exception.ParkingLotFullException;
import org.parkinglot.model.Ticket;
import org.parkinglot.model.Vehicle;
import org.parkinglot.service.ParkingLotService;

import java.util.Scanner;

public class ParkingLotApplication {
    private static ParkingLotService parkingLotService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            processCommand(command);
        }
    }

    private static void processCommand(String command) {
        String[] parts = command.split(" ");
        try {
            switch (parts[0]) {
                case "create_parking_lot" -> {
                    createParkingLot(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                }
                case "park_vehicle" -> {
                    parkVehicle(VehicleType.valueOf(parts[1].toUpperCase()), parts[2], parts[3]);
                }
                case "unpark_vehicle" -> {
                    unparkVehicle(parts[1]);
                }
                case "display" -> {
                    display(DisplayType.valueOf(parts[1].toUpperCase()), VehicleType.valueOf(parts[2].toUpperCase()));
                }
                default -> {
                    System.out.println("Invalid command");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createParkingLot(String id, int floors, int slotsPerFloor) {
        parkingLotService = new ParkingLotService(id, floors, slotsPerFloor);
        System.out.println("Created parking lot with " + floors +
                " floors and " + slotsPerFloor + " slots per floor");
    }

    private static void parkVehicle(VehicleType type, String regNo, String color) {
        try {
            Vehicle vehicle = new Vehicle(type, regNo, color);
            Ticket ticket = parkingLotService.parkVehicle(vehicle);
            System.out.println("Parked vehicle. Ticket ID: " + ticket.getTicketId());
        } catch (ParkingLotFullException e) {
            System.out.println("Parking Lot Full");
        }
    }

    private static void unparkVehicle(String ticketId) {
        try {
            Vehicle vehicle = parkingLotService.unparkVehicle(ticketId);
            System.out.println("Unparked vehicle with Registration Number: " +
                    vehicle.getRegistrationNumber() + " and Color: " +
                    vehicle.getColor());
        } catch (InvalidTicketException e) {
            System.out.println("Invalid Ticket");
        }
    }

    private static void display(DisplayType displayType, VehicleType vehicleType) {
        parkingLotService.display(displayType, vehicleType);
    }
}