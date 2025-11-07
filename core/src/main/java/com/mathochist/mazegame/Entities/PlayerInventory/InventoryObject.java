package com.mathochist.mazegame.Entities.PlayerInventory;

public enum InventoryObject {

    KEYCARD(false, "keycard"),
    FLASHLIGHT(true, "flashlight"),
    BATTERY(true, "battery"),
    MAP(false, "map"),
    MEDKIT(true, "medkit");

    private final boolean allowMultiple;
    private final String objectName;

    InventoryObject(boolean allowMultiple, String objectName) {
        this.objectName = objectName;
        this.allowMultiple = allowMultiple;
    }

    public boolean allowsMultiple() {
        return allowMultiple;
    }

    public String getObjectName() {
        return objectName;
    }

    public static InventoryObject fromString(String name) {
        for (InventoryObject obj : InventoryObject.values()) {
            if (obj.getObjectName().equalsIgnoreCase(name)) {
                return obj;
            }
        }
        return null;
    }

}
