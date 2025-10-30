package com.mathochist.mazegame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochist.mazegame.Main;

public class Hud {

    private OrthographicCamera hudCamera;
    private SpriteBatch hudBatch;
    private BitmapFont small_font;
    private BitmapFont main_font;

    private SpeechBubbleManager speechBubbleManager;

    public Hud(Main game) {

        hudBatch = new SpriteBatch();
        hudCamera = new OrthographicCamera(game.WIDTH, game.HEIGHT);
        hudCamera.position.set(game.WIDTH / 2.0f, game.HEIGHT / 2.0f, 1.0f);

        small_font = new BitmapFont(Gdx.files.internal("fonts/game_font.fnt"), Gdx.files.internal("fonts/game_font.png"), false);
        small_font.getData().setScale(0.5f);

        main_font = new BitmapFont(Gdx.files.internal("fonts/game_font.fnt"), Gdx.files.internal("fonts/game_font.png"), false);
        main_font.getData().setScale(1.0f);

        speechBubbleManager = new SpeechBubbleManager(hudBatch, hudCamera, main_font);

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
        small_font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 2, hudCamera.viewportHeight - 2);

        hudBatch.end();

        speechBubbleManager.render();
    }

    public SpeechBubbleManager getSpeechBubbleManager() {
        return speechBubbleManager;
    }

}
