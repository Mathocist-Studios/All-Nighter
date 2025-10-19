package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MapEntity extends Entity {

    public MapEntity(SpriteBatch batch, TextureAtlas atlas, String regionName, float PixelX, float PixelY) {
        super(batch, atlas, regionName, PixelX, PixelY);
    }

    @Override
    public void render(float delta) {

    }

}
