package com.mathochist.mazegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Screens.Game.LibraryScreen;
import com.mathochist.mazegame.UI.MainMenuUISkin;

public class IntroScreen extends DefaultScreen {

    private Stage stage;
    private Table table;
    private SpriteBatch batch;

    private MainMenuUISkin uiSkin;
    private Music backgroundMusic;

    private Texture keyBindsTexture;

    public IntroScreen(Main game, Music backgroundMusic) {
        super(game);

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        this.backgroundMusic = backgroundMusic;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        uiSkin = new MainMenuUISkin();
        batch = new SpriteBatch();

        Table textTable = new Table();
        table.addActor(textTable);

        Table escapeTable = new Table();
        table.addActor(escapeTable);

        Label settingSceneLabel = new Label("You are studying in the Morrell Library after closing hours not during exam season, and the dean sees you!", uiSkin.getSkin(), "game_font");
        textTable.add(settingSceneLabel).padBottom(10);
        textTable.row();

        Label instructionLabel = new Label("Find your way out before you get caught!", uiSkin.getSkin(), "game_font");
        textTable.add(instructionLabel).padBottom(10);
        textTable.row();

        Label timeLabel = new Label("You have 5 minutes to escape!", uiSkin.getSkin(), "game_font");
        textTable.add(timeLabel).padBottom(10);
        textTable.row();

        keyBindsTexture = new Texture(Gdx.files.internal("MainMenu/KeyBinds.png"));

        // add label at bottom right corner that says "Press Esc to skip"
        Label skipLabel = new Label("Press Any Button to continue..", uiSkin.getSkin(), "game_font");
        escapeTable.add(skipLabel).expand().bottom().right().pad(10);
        escapeTable.row();

        table.add(textTable).expand();
        table.row();
        table.add(escapeTable).expand().bottom().right().pad(10);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL32.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(new TextureRegion(keyBindsTexture), Gdx.graphics.getWidth() / 2.0f - 300, Gdx.graphics.getHeight() / 2.0f - 150, 600, 200);
        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            backgroundMusic.dispose();
            game.getTimerManager().startTimer();
            game.setScreen(new LibraryScreen(game));
        }

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
