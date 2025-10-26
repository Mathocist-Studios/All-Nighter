package com.mathochist.mazegame.Entities.NPCs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Entities.MapEntity;

public class Librarian extends MapEntity {

    public Librarian(SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(batch, atlas, "librarian", TileX, TileY, Width, Height, collidable);
    }

    @Override
    public void render() {

    }

}
