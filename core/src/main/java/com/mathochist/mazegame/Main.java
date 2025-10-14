package com.mathochist.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mathochist.mazegame.Screens.TitleScreen;

/**
 * The Main class serves as the entry point for the game application.
 */
public class Main extends Game {

    /**
     * Initializes the game by setting the initial screen to the TitleScreen.
     */
    @Override
    public void create() {
        // Set title for the application window
        Gdx.graphics.setTitle("All Nighter");

        // Set aspect ratio to 16:9
        int width = Gdx.graphics.getWidth() * 2;
        int height = (width * 9) / 16;
        Gdx.graphics.setWindowedMode(width, height);

        // Set the initial screen to the title screen
        setScreen(new TitleScreen(this));
    }

    /**
     * Disposes of resources and exits the application when the game is closed.
     */
    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.exit();
        System.exit(0);
    }

}
