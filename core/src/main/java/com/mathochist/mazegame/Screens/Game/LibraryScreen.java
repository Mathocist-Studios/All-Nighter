package com.mathochist.mazegame.Screens.Game;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.World.GameWorld;

public class LibraryScreen extends BaseGameScreen {


    public LibraryScreen(Main game) {
        super(game);
        super.setWorld(new GameWorld(Gdx.files.internal("maps/library.json"), super.getScreenBatch()));

        int[] spawnPoint = super.getWorld().getSpawnPointPixels();
        System.out.println(Arrays.toString(spawnPoint));

        super.getCamera().position.set(spawnPoint[0], spawnPoint[1], 0);
        super.getCamera().update();

        super.setPlayer(new Player(super.getCamera(), super.getScreenBatch(), super.getWorld(), spawnPoint[0], spawnPoint[1]));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.getCamera().update();
        super.getScreenBatch().setProjectionMatrix(super.getCamera().combined);

        // Draw game elements here
        super.getWorld().render();
        super.getGameHud().render(delta);
        super.getPlayer().update(delta);
    }

    @Override
    public void dispose() {
        super.getGameHud().dispose();
        super.getScreenBatch().dispose();
        super.getWorld().dispose();
        super.getPlayer().dispose();
    }

}
