package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class Entity {

    private SpriteBatch batch;
    private TextureAtlas atlas;
    private String regionName;

    private float pixelX;
    private float pixelY;

    public Entity(SpriteBatch batch, TextureAtlas atlas, String regionName, float PixelX, float PixelY) {
        this.batch = batch;
        this.atlas = atlas;
        this.regionName = regionName;
        this.pixelX = PixelX;
        this.pixelY = PixelY;
    }

    public abstract void render(float delta);

    public float getPixelX() {
        return pixelX;
    }

    public float getPixelY() {
        return pixelY;
    }

}
