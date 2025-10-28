package com.mathochist.mazegame.Entities.NPCs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.World.GameWorld;

public class Librarian extends MapEntity implements INPC {

    public Librarian(SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(batch, atlas, "librarian", TileX, TileY, Width, Height, collidable);
    }

    @Override
    public void render(GameWorld world) {

    }

    @Override
    public void renderPrompt(GameWorld world) {

    }

    @Override
    public void renderSpeech(String text, GameWorld world) {

    }

    @Override
    public void onPromptActivate(GameWorld world) {

    }

    @Override
    public boolean onInteract(Player p, GameWorld world) {
        System.out.println("The librarian says: 'Shhh! This is a library!'");
        return true;
    }
}
