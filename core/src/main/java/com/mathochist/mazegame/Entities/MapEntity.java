package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.UI.Hud;
import com.mathochist.mazegame.World.GameWorld;

/**
 * Abstract class representing an entity on the game map.
 * Extends the base Entity class and adds map-specific functionality. <br><br>
 * <b>All map entities must extend this class to be part of the game world.</b>
 */
public abstract class MapEntity extends Entity {

    protected Main game;

    public MapEntity(Main game, SpriteBatch batch, TextureAtlas atlas, String regionName, float TileX, float TileY, float TileWidth, float TileHeight, boolean collidable) {
        super(batch, atlas, regionName, TileX, TileY, TileWidth, TileHeight, collidable);

        this.game = game;
    }

    /**
     * Returns the bounding box of the entity as an array of floats.
     * The array contains the following values in order:
     * [TileX, TileY, Width, Height]
     *
     * @return float[] An array representing the bounding box of the entity.
     */
    public float[] getBBox() {
        float[] bbox = new float[4];
        bbox[0] = this.getTileX();
        bbox[1] = this.getTileY();
        bbox[2] = this.getWidth();
        bbox[3] = this.getHeight();
        return bbox;
    }

    /**
     * Called when a player interacts with this entity.
     *
     * @param p The player interacting with the entity.
     * @param world The game world context.
     * @param worldHud The HUD of the game world.
     * @return boolean True if the interaction was successful, false otherwise.
     * <br>
     * <b>*return false if you do not need interactions on your entity e.g. a bookcase*</b>
     */
    public abstract boolean onInteract(Player p, GameWorld world, Hud worldHud);

    /**
     * Called when a player ends interaction with this entity.
     * <br>
     * <b>*Called every time the player attempts to interact but entity out of range*</b>
     *
     * @param p The player ending interaction with the entity.
     * @param world The game world context.
     */
    public abstract void onInteractEnd(Player p, GameWorld world);

}
