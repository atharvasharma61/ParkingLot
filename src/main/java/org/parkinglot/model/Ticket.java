package org.parkinglot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
}