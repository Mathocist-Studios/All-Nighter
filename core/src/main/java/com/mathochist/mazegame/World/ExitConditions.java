package com.mathochist.mazegame.World;

import com.mathochist.mazegame.Entities.PlayerInventory.Inventory;
import com.mathochist.mazegame.Entities.PlayerInventory.InventoryObject;

/**
 * Represents the conditions required to exit a level or area in the game.
 *
 * @param requiredItems      An array of item identifiers that the player must possess to exit.
 * @param questCompletions   An array of quest identifiers that must be completed to exit.
 * @param npcInteractions    An array of NPC identifiers that the player must have interacted with to exit.
 * @param minTimeSeconds    The minimum time in seconds that must have elapsed before exiting is allowed.
 */
public record ExitConditions(String[] requiredItems, String[] questCompletions, String[] npcInteractions, int minTimeSeconds) {
// TODO: NPC and quest identifiers have not been implemented yet

    public boolean hasRequiredItems(Inventory playerInventory) {
        for (String item : requiredItems) {
            InventoryObject object = InventoryObject.fromString(item);
            if (object == null) {
                throw new IllegalArgumentException("Invalid inventory item in exit conditions: " + item);
            }
            if (!playerInventory.hasItem(object)) {
                return false;
            }
        }
        return true;
    }

    public String[] getMissingItems(Inventory playerInventory) {
        return java.util.Arrays.stream(requiredItems)
                .filter(item -> {
                    InventoryObject object = InventoryObject.fromString(item);
                    return object != null && !playerInventory.hasItem(object);
                })
                .toArray(String[]::new);
    }

}
