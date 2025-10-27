package com.mathochist.mazegame.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Screens.DefaultScreen;
import com.mathochist.mazegame.UI.Hud;
import com.mathochist.mazegame.World.GameWorld;

public abstract class BaseGameScreen extends DefaultScreen {

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Hud gameHud;

    private static float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
    private static float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

    private Player player;
    private SpriteBatch screenBatch;
    private GameWorld world;

    public BaseGameScreen(Main game) {
        super(game);
        // GDX setup
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        Gdx.input.setInputProcessor(this);

        // Camera setup
        gameHud = new Hud();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0); // Center camera
        camera.update();
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

        // Batch setup
        screenBatch = new SpriteBatch();
    }

    public BaseGameScreen(Main game, Float spawnX, Float spawnY) {
        super(game);
        // GDX setup
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        Gdx.input.setInputProcessor(this);

        // Camera setup
        gameHud = new Hud();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0); // Center camera
        camera.update();
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

        // Batch setup
        screenBatch = new SpriteBatch();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float delta);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        world.scaleWorld(VIEWPORT_HEIGHT, height);
    }

    @Override
    public abstract void dispose();

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Hud getGameHud() {
        return gameHud;
    }

    // Input Processing methods can be added here if needed
    @Override
    public boolean keyDown(int keycode) {
        player.getKeyBuffer().addKeyPressed(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.getKeyBuffer().removeKeyPressed(keycode);
        return true;
    }

    public SpriteBatch getScreenBatch() {
        return screenBatch;
    }

    public void setWorld(GameWorld world) {
        this.world = world;

        // Ensure world scaling for collisions
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public GameWorld getWorld() {
        return this.world;
    }

    public FitViewport getViewport() {
        return viewport;
    }

}
