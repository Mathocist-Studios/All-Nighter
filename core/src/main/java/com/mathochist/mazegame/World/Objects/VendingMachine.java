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

public class VendingMachine extends MapEntity {

    private boolean used;
    private static final long INTERACTION_COOLDOWN_MS = 2000;
    private long lastInteractionTime = 0;

    public VendingMachine(Main game, SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(game, batch, atlas, "vending_machine", TileX, TileY, Width, Height, collidable);

        used = game.getPlayerInventory().hasItem(InventoryObject.ENERGY_DRINK); // If player already has energy drink, mark as used
    }

    @Override
    public void render(GameWorld world, RenderBuffer renderBuffer) {
        TextureRegion vmRegion = super.getAtlas().findRegion(super.getRegionName());
        Sprite vmSprite = new Sprite(vmRegion);
        vmSprite.setPosition(
            super.getTileX() * world.getMap().getTileWidth(),
            Gdx.graphics.getHeight() - ((super.getTileY() + 1) * world.getMap().getTileHeight() + world.tileDrawYOffset + world.getDeltaViewportHeight())
        );
        renderBuffer.addToBuffer(new RenderObject(
            vmSprite,
            super.getBatch(),
            (int) super.getTileY() + 2
        ));
    }

    @Override
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastInteractionTime < INTERACTION_COOLDOWN_MS) {
            return true;
        }
        lastInteractionTime = currentTime;
        if (used) {
            worldHud.getSpeechBubbleManager().removeBubblesOfType(SpeechType.NPC_SPEECH);
            worldHud.getSpeechBubbleManager().createSpeechBubble(SpeechType.NPC_SPEECH, "The vending machine is empty.", 2000);
            return true;
        }
        used = true;
        super.game.getPlayerInventory().addItem(InventoryObject.ENERGY_DRINK);
        worldHud.getSpeechBubbleManager().removeBubblesOfType(SpeechType.NPC_SPEECH);
        worldHud.getSpeechBubbleManager().createSpeechBubble(SpeechType.NPC_SPEECH, "You got an energy drink, You are suddenly feeling fast!", 2000);
        super.game.getEventsCounter().vendingMachineEvent();
        return true;
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {} // No action needed on interaction end

}
