package com.mathochist.mazegame.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Screens.EndScreen;
import com.mathochist.mazegame.World.GameWorld;

public class OutdoorScreen extends BaseGameScreen {

    public OutdoorScreen(Main game) {
        super(game);
        super.setWorld(new GameWorld(game, this, Gdx.files.internal("maps/outdoor.json"), super.getScreenBatch()));

        super.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        super.getWorld().tileDrawYOffset -= Gdx.graphics.getHeight() - game.HEIGHT;

        int[] spawnPoint = super.getWorld().getSpawnPointPixels();
        super.getCamera().position.set(spawnPoint[0], spawnPoint[1], 0);
        super.getCamera().update();

        super.setPlayer(new Player(game, super.getCamera(), super.getScreenBatch(), super.getWorld(), spawnPoint[0], spawnPoint[1]));
    }

    public OutdoorScreen(Main game, Float spawnX, Float spawnY) {
        super(game);
        super.setWorld(new GameWorld(game, this, Gdx.files.internal("maps/outdoor.json"), super.getScreenBatch()));

        // Adjust for different screen resizes because of viewport
        // I hate viewports
        super.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        super.getWorld().tileDrawYOffset -= Gdx.graphics.getHeight() - game.HEIGHT;
        /// ///

        float[] spawnPixels = super.getWorld().getPixels(spawnX, spawnY);
        super.getCamera().position.set(spawnPixels[0], spawnPixels[1], 0);
        super.getCamera().update();

        super.setPlayer(new Player(game, super.getCamera(), super.getScreenBatch(), super.getWorld(), spawnPixels[0], spawnPixels[1]));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        try {
            Gdx.gl.glClearColor(0f,0f,0f,1.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            super.getCamera().update();
            super.getScreenBatch().setProjectionMatrix(super.getCamera().combined);

            // Draw game elements here
            super.getWorld().render(super.getViewport(), super.getRenderBuffer());
            super.getPlayer().update(delta, super.getRenderBuffer());

            // dump render buffer to screen
            for (var obj : super.getRenderBuffer().getBufferOrderedByZIndex()) {
                obj.render();
            }

            super.getRenderBuffer().clearBuffer();

            super.getWorld().renderDebugLayer();

            // TODO: Remove debug render call
            //  Used to visualize collision layer
            //  Remove or toggle off in production
            // super.getWorld().render_collision_layer(super.getPlayer());

            super.getGameHud().render(delta);

        } catch (Exception ignored) {}

        if (game.getTimerManager().isTimeUp()) {
            super.getWorld().getBackgroundMusic().stop();
            game.setScreen(new EndScreen(game));
        }

    }

    @Override
    public void dispose() {
        super.getGameHud().dispose();
        super.getScreenBatch().dispose();
        super.getWorld().dispose();
        super.getPlayer().dispose();
    }
}
