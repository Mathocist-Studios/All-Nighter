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
import com.mathochist.mazegame.World.ExitConditions;
import com.mathochist.mazegame.World.ExitTile;
import com.mathochist.mazegame.World.GameWorld;

/**
 * Represents a hole in the library floor that leads to the basement.
 * The hole is only visible and collidable if the player has the keycard item due to game timeline.
 */
public class LibraryFloorHole extends MapEntity {

    private boolean visible;

    public LibraryFloorHole(Main game, SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(game, batch, atlas, "hole_floor", TileX, TileY, Width, Height, collidable);

        visible = game.getPlayerInventory().hasItem(InventoryObject.KEYCARD); // visible if player has keycard
    }

    @Override
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        return false; // No interaction behavior
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {} // No action needed on interaction end

    @Override
    public void render(GameWorld world, RenderBuffer renderBuffer) {

//        // TESTING: Make visible only if player has keycard
//        visible = game.getPlayerInventory().hasItem(InventoryObject.KEYCARD); // update visibility each collision check
//

        if (!visible) {
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

    @Override
    public boolean onCollision(GameWorld world) {
        visible = game.getPlayerInventory().hasItem(InventoryObject.KEYCARD); // update visibility each collision check

        if (!visible) {
            return false; // No collision if not visible
        }

        ExitConditions conditions = new ExitConditions(new String[0], new String[0], new String[0], -1);
        ExitTile exit = new ExitTile(super.getTileX(), super.getTileY(), "com.mathochist.mazegame.Screens.Game.LibraryBasementScreen", 12, 5, conditions);
        world.loadNewWorld(exit);
        return true;
    }


}
