package com.mathochist.mazegame.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
