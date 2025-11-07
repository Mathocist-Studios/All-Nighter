package com.mathochist.mazegame.Entities.PlayerInventory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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

    public void render(SpriteBatch batch, OrthographicCamera camera, TextureAtlas gameUIAtlas) {
        int startX = 10;
        int startY = 10;
        int itemSize = 32;
        for (int i = 0; i < items.size(); i++) {
            InventoryObject item = items.get(i);
            batch.draw(gameUIAtlas.findRegion(item.getObjectName()), startX + i * (itemSize + 5), startY, itemSize, itemSize);
        }
    }

}
