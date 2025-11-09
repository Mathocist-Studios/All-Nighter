package com.mathochist.mazegame.World;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tile {
    private String type;
    private Boolean collidable;
    private Sprite sprite;

    public Tile(String type, Boolean collidable, Sprite sprite) {
        this.type = type;
        this.collidable = collidable;
        this.sprite = sprite;
    }

    public String getType() {
        return type;
    }

    public Boolean isCollidable() {
        return collidable;
    }

    public void SetCollidable(Boolean collidable) {
        this.collidable = collidable;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public String toString() {
        return "Tile [type=" + type + ", collidable=" + collidable + "]";
    }
}
