package com.mathochist.mazegame.Entities.PlayerInventory;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<InventoryObject> items;

    public Inventory() {
        items = new ArrayList<InventoryObject>();
    }

    public boolean addItem(InventoryObject item) {
        if (hasItem(item) && !item.allowsMultiple()) {
            return false;
        }
        items.add(item);
        return true;
    }

    public boolean hasItem(InventoryObject item) {
        return items.contains(item);
    }

    public void clear() {
        items.clear();
    }

}
