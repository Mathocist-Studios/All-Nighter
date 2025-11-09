package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Rendering.RenderBuffer;
import com.mathochist.mazegame.World.GameWorld;

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

    /**
     * Renders the entity onto the provided RenderBuffer within the given GameWorld context.
     *
     * @param world The GameWorld context in which the entity exists.
     * @param renderBuffer The RenderBuffer to which the entity's render data will be added.
     */
    public abstract void render(GameWorld world, RenderBuffer renderBuffer);

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

    protected SpriteBatch getBatch() {
        return batch;
    }

    protected TextureAtlas getAtlas() {
        return atlas;
    }

    protected String getRegionName() {
        return regionName;
    }

}
