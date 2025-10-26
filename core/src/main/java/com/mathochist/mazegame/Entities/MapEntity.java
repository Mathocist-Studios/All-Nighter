package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class MapEntity extends Entity {

    public MapEntity(SpriteBatch batch, TextureAtlas atlas, String regionName, float TileX, float TileY, float TileWidth, float TileHeight, boolean collidable) {
        super(batch, atlas, regionName, TileX, TileY, TileWidth, TileHeight, collidable);
    }

    public float[] getBBox() {
        float[] bbox = new float[4];
        bbox[0] = this.getTileX();
        bbox[1] = this.getTileY();
        bbox[2] = this.getWidth();
        bbox[3] = this.getHeight();
        return bbox;
    }

}
