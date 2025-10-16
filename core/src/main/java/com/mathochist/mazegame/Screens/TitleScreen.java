package com.mathochist.mazegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.UI.HoverListener;
import com.mathochist.mazegame.UI.MainMenuUISkin;

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

    private MainMenuUISkin uiSkin;

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

        Table buttonTable = new Table();
        table.addActor(buttonTable);

        Table titleTable = new Table();
        table.addActor(titleTable);

        uiSkin = new MainMenuUISkin();
        batch = new SpriteBatch();
        // backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("ui/background.png")));

        // Add UI elements to the table using uiSkin
        Label presentsLabel = new Label("Mathochist Studios Presents", uiSkin.getSkin(), "presents_grey");
        titleTable.add(presentsLabel).expand().padBottom(10);
        titleTable.row();


        Label titleLabel = new Label("All Nighter", uiSkin.getSkin());
        titleTable.add(titleLabel).expand();


        Button startButton = new Button(uiSkin.getSkin(), "start_button");
        buttonTable.add(startButton).expand().width(95).height(33.333F).padBottom(5).align(Align.left);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                // game.setScreen(new GameScreen(game));
                Gdx.app.log("TitleScreen", "Start button clicked - transition to GameScreen");
            }
        });
        buttonTable.row();

        Button settingsButton = new Button(uiSkin.getSkin(), "settings_button");
        buttonTable.add(settingsButton).expand().width(126.666F).height(33.333F).padBottom(5).align(Align.left);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                // game.setScreen(new SettingsScreen(game));
                Gdx.app.log("TitleScreen", "Settings button clicked - transition to SettingsScreen");
            }
        });
        buttonTable.row();

        Button creditsButton = new Button(uiSkin.getSkin(), "credits_button");
        buttonTable.add(creditsButton).expand().width(115).height(33.333F).padBottom(5).align(Align.left);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                // game.setScreen(new CreditsScreen(game));
                Gdx.app.log("TitleScreen", "Credits button clicked - transition to CreditsScreen");
            }
        });
        buttonTable.row();

        Button exitButton = new Button(uiSkin.getSkin(), "exit_button");
        buttonTable.add(exitButton).expand().width(76.666F).height(33.333F).padBottom(5).align(Align.left);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Add hover listeners to buttons
        startButton.addListener(new HoverListener());
        settingsButton.addListener(new HoverListener());
        creditsButton.addListener(new HoverListener());
        exitButton.addListener(new HoverListener());

        // offset buttons to the left
        buttonTable.left().padRight(300);
        buttonTable.bottom();

        table.add(titleTable).expand().padTop(100);
        table.row();

        table.add(buttonTable).expand().padBottom(150);
        table.row();

        table.setDebug(false); // Set to true to see the table layout

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
