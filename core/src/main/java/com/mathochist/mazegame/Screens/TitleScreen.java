package com.mathochist.mazegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.UI.DefaultUISkin;

public class TitleScreen extends DefaultScreen {

    private Stage stage;
    private Table table;

    public TitleScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        DefaultUISkin uiSkin = new DefaultUISkin();

        // Add UI elements to the table using uiSkin
        Button startButton = new Button(uiSkin.getSkin());
        table.add(startButton).expand().width(300).height(100);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                // game.setScreen(new GameScreen(game));
                Gdx.app.log("TitleScreen", "Start button clicked - transition to GameScreen");
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL32.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Table getTable() {
        return table;
    }

    public Stage getStage() {
        return stage;
    }


    // InputProcessor methods //

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

}
