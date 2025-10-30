package com.mathochist.mazegame.World.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.UI.Hud;
import com.mathochist.mazegame.World.GameWorld;

public class Bookcase extends MapEntity {

    public Bookcase(SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(batch, atlas, "bookcase", TileX, TileY, Width, Height, collidable);
    }

    @Override
    public void render(GameWorld world) {

    }

    @Override
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        return false; // Bookcases are not interactable
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {} // No action needed on interaction end
}
