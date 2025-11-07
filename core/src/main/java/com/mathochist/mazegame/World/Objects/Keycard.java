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

public class Keycard extends MapEntity {

    private boolean collected;

    public Keycard(Main game, SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(game, batch, atlas, "keycard", TileX, TileY, Width, Height, collidable);

        collected = game.getPlayerInventory().hasItem(InventoryObject.KEYCARD); // If player already has keycard, mark as collected
    }

    @Override
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        if (collected) {
            return false; // Already collected
        }
        collected = true;
        super.game.getPlayerInventory().addItem(InventoryObject.KEYCARD);
        worldHud.getSpeechBubbleManager().removeBubblesOfType(SpeechType.NPC_SPEECH);
        worldHud.getSpeechBubbleManager().createSpeechBubble(SpeechType.NPC_SPEECH, "Keycard collected!", 2000);
        game.getEventsCounter().foundKeycardEvent();
        return true; // Keycards are collectible
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {} // No action needed on interaction end

    @Override
    public void render(GameWorld world, RenderBuffer renderBuffer) {
        if (collected) {
            return;
        }
        TextureRegion keycardRegion = super.getAtlas().findRegion(super.getRegionName());
        Sprite keycardSprite = new Sprite(keycardRegion);
        keycardSprite.setPosition(
            super.getTileX() * world.getMap().getTileWidth(),
            Gdx.graphics.getHeight() - ((super.getTileY() + 1) * world.getMap().getTileHeight() + world.tileDrawYOffset + world.getDeltaViewportHeight())
        );
        renderBuffer.addToBuffer(new RenderObject(
            keycardSprite,
            super.getBatch(),
            (int) super.getTileY() + 1
        ));
    }

}
