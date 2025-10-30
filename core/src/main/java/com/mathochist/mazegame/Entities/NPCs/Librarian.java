package com.mathochist.mazegame.Entities.NPCs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.UI.Hud;
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
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        // System.out.println("The librarian says: 'Shhh! This is a library!'");
        worldHud.getSpeechBubbleManager().createSpeechBubble("Librarian: Shhh! This is a library!", 2000);
        return true;
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {
        // System.out.println("You are not bothering the librarian.");
    }
}
