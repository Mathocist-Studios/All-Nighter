package com.mathochist.mazegame.Movement;

import com.badlogic.gdx.Input;

/**
 * Class containing key bindings for player movement and interaction.
 * <br>
 * <br>
 * To add new key binds, add a new static integer variable, add the relevant enum to KeyAction,
 * and update the setKeyBind method accordingly. You will be able to check for the key press using
 * KeyBuffer.isKeyPressed(KeyBinds.YOUR_NEW_KEY_BIND_VARIABLE).
 * <br>
 * <br>
 * If you want to add more complex functionality (like toggling sprint), you will need to
 * implement that in KeyBuffer.java.
 */
public class KeyBinds {

    public static int MOVE_UP = Input.Keys.UP; // W key
    public static int MOVE_DOWN = Input.Keys.DOWN; // S key
    public static int MOVE_LEFT = Input.Keys.LEFT; // A key
    public static int MOVE_RIGHT = Input.Keys.RIGHT; // D key

    public static int MOVE_UP_ALT = Input.Keys.W; // UP key
    public static int MOVE_DOWN_ALT = Input.Keys.S; // DOWN key
    public static int MOVE_LEFT_ALT = Input.Keys.A; // LEFT key
    public static int MOVE_RIGHT_ALT = Input.Keys.D; // RIGHT key

    public static int SPRINT = Input.Keys.SHIFT_LEFT; // Left Shift key
    public static int INTERACT = Input.Keys.E; // E key

    public static int ESCAPE_GAME = Input.Keys.ESCAPE; // ESC key

    public static void setKeyBind(KeyAction action, int keycode) {
        switch (action) {
            case MOVE_UP:
                MOVE_UP = keycode;
                break;
            case MOVE_DOWN:
                MOVE_DOWN = keycode;
                break;
            case MOVE_LEFT:
                MOVE_LEFT = keycode;
                break;
            case MOVE_RIGHT:
                MOVE_RIGHT = keycode;
                break;
            case SPRINT:
                SPRINT = keycode;
                break;
            case INTERACT:
                INTERACT = keycode;
                break;
            case ESCAPE_GAME:
                ESCAPE_GAME = keycode;
                break;
        }
    }

}
