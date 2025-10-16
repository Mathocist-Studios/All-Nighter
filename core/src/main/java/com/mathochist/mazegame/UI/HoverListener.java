package com.mathochist.mazegame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A ClickListener that changes the cursor to a hand when hovering over an actor
 * and back to the default arrow when not hovering.
 */
public class HoverListener extends ClickListener {

    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        // make pointer hand2
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        super.enter(event, x, y, pointer, fromActor);
    }

    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        // make pointer arrow
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        super.exit(event, x, y, pointer, toActor);
    }

}
