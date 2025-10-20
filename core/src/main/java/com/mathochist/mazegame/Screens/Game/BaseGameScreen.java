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

    private static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
    private static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

    private Player player;
    private SpriteBatch playBatch;
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

        // Player and Batch setup
        playBatch = new SpriteBatch();
        player = new Player();
        player.setX(50);
        player.setY(50);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float delta);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0); // Re-center camera
        camera.update();
    }

    @Override
    public abstract void dispose();

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Hud getGameHud() {
        return gameHud;
    }

    public void newTextBox(){

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

    public SpriteBatch getPlayBatch() {
        return playBatch;
    }

    public void setWorld(GameWorld world) {
        this.world = world;
    }

    public GameWorld getWorld() {
        return this.world;
    }

}
