package com.mathochist.mazegame.World.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Rendering.RenderBuffer;
import com.mathochist.mazegame.Rendering.RenderObject;
import com.mathochist.mazegame.UI.Hud;
import com.mathochist.mazegame.World.GameWorld;

public class Bookcase2x2 extends MapEntity {

    public Bookcase2x2(Main game, SpriteBatch batch, TextureAtlas atlas, Integer TileX, Integer TileY, Integer Width, Integer Height, Boolean collidable) {
        super(game, batch, atlas, "bookshelf_2x2", TileX, TileY, Width, Height, collidable);
    }

    @Override
    public void render(GameWorld world, RenderBuffer renderBuffer) {
        TextureRegion bookcaseRegion = super.getAtlas().findRegion(super.getRegionName());
        Sprite bookcaseSprite = new Sprite(bookcaseRegion);
        bookcaseSprite.setPosition(
            super.getTileX() * world.getMap().getTileWidth(),
            Gdx.graphics.getHeight() - ((super.getTileY() + 1) * world.getMap().getTileHeight() + world.tileDrawYOffset + world.getDeltaViewportHeight())
        );
        renderBuffer.addToBuffer(new RenderObject(
            bookcaseSprite,
            super.getBatch(),
            (int) super.getTileY() + 2
        ));
    }

    @Override
    public boolean onInteract(Player p, GameWorld world, Hud worldHud) {
        return false; // Bookcases are not interactable
    }

    @Override
    public void onInteractEnd(Player p, GameWorld world) {} // No action needed on interaction end

}
