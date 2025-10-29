package com.mathochist.mazegame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochist.mazegame.Main;

public class Hud {

    private OrthographicCamera hudCamera;
    private SpriteBatch hudBatch;
    private BitmapFont font;

    public Hud(Main game) {

        hudBatch = new SpriteBatch();
        hudCamera = new OrthographicCamera(game.WIDTH, game.HEIGHT);
        hudCamera.position.set(game.WIDTH / 2.0f, game.HEIGHT / 2.0f, 1.0f);

        font = new BitmapFont(Gdx.files.internal("fonts/game_font.fnt"), Gdx.files.internal("fonts/game_font.png"), false);
        font.getData().setScale(0.5f);

    }

    public SpriteBatch getHudBatch() {
        return hudBatch;
    }

    public OrthographicCamera getHudCamera() {
        return hudCamera;
    }

    public void dispose() {
        hudBatch.dispose();
    }

    public void render(float delta) {
        hudCamera.update();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        hudBatch.begin();

        // draw fps in top left corner
        font.draw(hudBatch, "Upper left, FPS=" + Gdx.graphics.getFramesPerSecond(), 2, hudCamera.viewportHeight - 2);
        font.draw(hudBatch, "Lower left", 2, font.getLineHeight() + 2);

        hudBatch.end();
    }

}
