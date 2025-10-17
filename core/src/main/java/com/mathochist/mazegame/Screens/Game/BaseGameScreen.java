package com.mathochist.mazegame.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Screens.DefaultScreen;
import com.mathochist.mazegame.UI.Hud;

public abstract class BaseGameScreen extends DefaultScreen {

    private OrthographicCamera camera;
    private Hud gameHud;

    private static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
    private static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

    private Sprite playerSprite;
    private SpriteBatch playerBatch;

    public BaseGameScreen(Main game) {
        super(game);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        playerBatch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);

        gameHud = new Hud();
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float delta);

    @Override
    public void resize(int width, int height) {
        // Default implementation (can be overridden by subclasses)
    }

    @Override
    public abstract void dispose();

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Hud getGameHud() {
        return gameHud;
    }

    public SpriteBatch getPlayerBatch() {
        return playerBatch;
    }

    // Input Processing methods can be added here if needed
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

}
