package org.parkinglot.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum DisplayType {
    FREE_COUNT("free_count"),
    FREE_SLOTS("free_slots"),
    OCCUPIED_SLOTS("occupied_slots");

    private final String value;

    DisplayType(String value) {
        this.value = value;
    }

    public static DisplayType fromString(String text) {
        for (DisplayType type : DisplayType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}