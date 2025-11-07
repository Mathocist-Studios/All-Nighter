package com.mathochist.mazegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.UI.ButtonHoverListener;
import com.mathochist.mazegame.UI.MainMenuUISkin;

public class EndScreen extends DefaultScreen {

    private Stage stage;
    private Table table;
    private SpriteBatch batch;

    private MainMenuUISkin uiSkin;

    public EndScreen(Main game) {
        super(game);

        game.getTimerManager().resetTimer();
        game.getPlayerInventory().clear();
    }

    @Override
    public void show() {

        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/title_screen.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        uiSkin = new MainMenuUISkin();
        batch = new SpriteBatch();

        // Game over label
        Label gameOverLabel = new Label("Game Over", uiSkin.getSkin());
        table.add(gameOverLabel).padBottom(10);
        table.row();

        Label timeUpLabel = new Label("Time's Up!", uiSkin.getSkin(), "presents_grey");
        table.add(timeUpLabel).padBottom(50);
        table.row();

        Button backToMenuButton = new Button(uiSkin.getSkin(), "back_to_MM");
        table.add(backToMenuButton).width(250).height(50);
        backToMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                backgroundMusic.stop();
                game.setScreen(new TitleScreen(game));
            }
        });
        backToMenuButton.row();

        backToMenuButton.addListener(new ButtonHoverListener());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL32.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.end();

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
        uiSkin.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

}
