package com.mathochist.mazegame.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A class to manage the main menu UI skin for the game.
 * Loads the skin from the specified JSON file and provides access to it.
 * Also handles disposal of the skin when no longer needed.
 * TODO: Expand this class to include more UI-related utilities as needed.
 */
public class MainMenuUISkin {

    private final Skin skin;

    public MainMenuUISkin() {
        skin = new Skin(com.badlogic.gdx.Gdx.files.internal("MainMenu/main_menu_ui.json"));
    }

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        skin.dispose();
    }

}
