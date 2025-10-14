package com.mathochist.mazegame.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A class to manage the default UI skin for the game.
 * Loads the skin from the specified JSON file and provides access to it.
 * Also handles disposal of the skin when no longer needed.
 * TODO: Expand this class to include more UI-related utilities as needed.
 */
public class DefaultUISkin {

    private final Skin skin;

    public DefaultUISkin() {
        skin = new Skin(com.badlogic.gdx.Gdx.files.internal("ui/ui_skin.json"));
    }

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        skin.dispose();
    }

}
