package com.mathochist.mazegame.Rendering;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderObject {

    private Sprite sprite;
    private SpriteBatch batch;
    private int zIndex;

    public RenderObject(Sprite sprite, SpriteBatch batch, int zIndex) {
        this.sprite = sprite;
        this.batch = batch;
        this.zIndex = zIndex;
    }

    public void render() {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public String toString() {
        return "RenderObject{" +
                "sprite=" + sprite +
                ", batch=" + batch +
                ", zIndex=" + zIndex +
                '}';
    }
}
