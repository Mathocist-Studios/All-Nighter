package com.mathochist.mazegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.UI.DefaultUISkin;

/**
 * The TitleScreen class represents the title screen of the game.
 * It extends DefaultScreen and implements the necessary methods to display
 * a stage with UI elements such as buttons.
 */
public class TitleScreen extends DefaultScreen {

    private Stage stage;
    private Table table;
    private SpriteBatch batch;
    private TextureRegion backgroundTexture;

    private DefaultUISkin uiSkin;

    public TitleScreen(Main game) {
        super(game);
    }

    /**
     * Sets up the stage and UI elements for the title screen.
     * This includes creating a table layout and adding buttons with listeners.
     * Using a FitViewport to ensure the stage scales correctly with the window size.
     */
    @Override
    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        uiSkin = new DefaultUISkin();
        batch = new SpriteBatch();
        // backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("ui/background.png")));

        // Add UI elements to the table using uiSkin
        TextButton startButton = new TextButton("Start", uiSkin.getSkin());
        table.add(startButton).expand().width(300).height(100);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                // game.setScreen(new GameScreen(game));
                Gdx.app.log("TitleScreen", "Start button clicked - transition to GameScreen");
            }
        });
    }

    /**
     * Renders the stage and its actors.
     * Clears the screen with a black color before drawing the stage.
     * TODO: Work out the picture background for the title screen.
     */
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
    }

    public Table getTable() {
        return table;
    }

    public Stage getStage() {
        return stage;
    }


    // InputProcessor methods //
    // unnecessary because we use stage as input processor

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

}
