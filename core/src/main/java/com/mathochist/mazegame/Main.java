package com.mathochist.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mathochist.mazegame.Screens.TitleScreen;

/*
Mathochist Studios LICENSE 2025

Don't slap ur name on it or your corpo
Fork it, Spoon it, idk even knife it, idc
Redistribute it in source code, binary form, lasagna form, whatever
Modify it, convert it, blend it just maintain the LICENSE

Always retain the CREDITS file with attribution to Mathochist Studios
If you use it for commercial purposes, let us know, we might wanna collab, just make sure you:
 - Take a picture of your team in pink hats holding a turtle
   * turtle may be substituted with other shelled reptiles *

If we meet some day and you liked the code, you can buy us a round of drinks.

If you violated the license, the universe will collapse in on itself and you will be
forced to listen to Nickelback for eternity (Karma's a bitch, and we will be petty)

xoxo - Aiden, Marcus, Harri, Will, Euan, Zach, Charlie, Josh
https://mathochiststudios.com/
*/

/**
 * The Main class serves as the entry point for the game application.
 */
public class Main extends Game {

    public int WIDTH = 0;
    public int HEIGHT = 0;

    /**
     * Initializes the game by setting the initial screen to the TitleScreen.
     */
    @Override
    public void create() {
        // Set title for the application window
        Gdx.graphics.setTitle("All Nighter");

        // Set aspect ratio to 16:9
        WIDTH = Gdx.graphics.getWidth() * 2;
        HEIGHT = (WIDTH * 9) / 16;
        Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);

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
    }

}
