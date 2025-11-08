package com.mathochist.mazegame.World.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Entities.PlayerInventory.InventoryObject;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Rendering.RenderBuffer;
import com.mathochist.mazegame.Rendering.RenderObject;
import com.mathochist.mazegame.UI.Hud;
import com.mathochist.mazegame.UI.Speech.SpeechType;
import com.mathochist.mazegame.World.GameWorld;

public class BasementKey extends MapEntity {

    private boolean collected;

    public BasementKey(Main game, SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(game, batch, atlas, "basement_key", TileX, TileY, Width, Height, collidable);

        collected = game.getPlayerInventory().hasItem(InventoryObject.BASEMENT_KEY); // If player already has basement key, mark as collected
    }

    @Override
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        if (collected) {
            return false; // Already collected
        }
        collected = true;
        super.game.getPlayerInventory().addItem(InventoryObject.BASEMENT_KEY);
        worldHud.getSpeechBubbleManager().removeBubblesOfType(SpeechType.NPC_SPEECH);
        worldHud.getSpeechBubbleManager().createSpeechBubble(SpeechType.NPC_SPEECH, "Basement key collected!", 2000);
        game.getEventsCounter().foundKeycardEvent();
        return true; // Keys are collectible
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {} // No action needed on interaction end

    @Override
    public void render(GameWorld world, RenderBuffer renderBuffer) {
        if (collected) {
            return;
        }
        TextureRegion keyRegion = super.getAtlas().findRegion(super.getRegionName());
        Sprite keySprite = new Sprite(keyRegion);
        keySprite.setPosition(
            super.getTileX() * world.getMap().getTileWidth(),
            Gdx.graphics.getHeight() - ((super.getTileY() + 1) * world.getMap().getTileHeight() + world.tileDrawYOffset + world.getDeltaViewportHeight())
        );
        renderBuffer.addToBuffer(new RenderObject(
            keySprite,
            super.getBatch(),
            (int) super.getTileY() + 1
        ));
    }

}
