package com.mathochist.mazegame.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mathochist.mazegame.Main;

public class LibraryScreen extends BaseGameScreen {


    public LibraryScreen(Main game) {
        super(game);
        super.setWorld(new com.mathochist.mazegame.World.GameWorld(Gdx.files.internal("maps/library.json")));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.getCamera().update();
        super.getPlayBatch().setProjectionMatrix(super.getCamera().combined);

        super.getGameHud().render(delta);
        super.getPlayBatch().begin();
        // Draw game elements here
        super.getPlayer().update(delta);
        super.getWorld().render();
        super.getPlayBatch().end();
    }

    @Override
    public void dispose() {
        super.getGameHud().dispose();
        super.getPlayBatch().dispose();
    }

}
