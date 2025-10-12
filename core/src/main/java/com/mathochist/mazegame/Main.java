package com.mathochist.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mathochist.mazegame.Screens.TitleScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.exit();
        System.exit(0);
    }

}
