package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class Entity {

    private SpriteBatch batch;
    private TextureAtlas atlas;
    private String regionName;

    private float tileX;
    private float tileY;
    private float width;
    private float height;
    private boolean collidable;

    public Entity(SpriteBatch batch, TextureAtlas atlas, String regionName, float tileX, float tileY, float width, float height, boolean collidable) {
        this.batch = batch;
        this.atlas = atlas;
        this.regionName = regionName;
        this.tileX = tileX;
        this.tileY = tileY;
        this.width = width;
        this.height = height;
        this.collidable = collidable;
    }

    public abstract void render();

    public float getTileX() {
        return tileX;
    }

    public float getTileY() {
        return tileY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isCollidable() {
        return collidable;
    }

}
