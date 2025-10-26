package com.mathochist.mazegame.World.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Entities.MapEntity;

public class Bookcase extends MapEntity {

    public Bookcase(SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(batch, atlas, "bookcase", TileX, TileY, Width, Height, collidable);
    }

    @Override
    public void render() {

    }
}
