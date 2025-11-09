package com.mathochist.mazegame.Movement;

import java.util.ArrayList;

/**
 * A buffer to store key presses and camera movement.
 */
public class KeyBuffer {

    private final float[] camera_move = {0, 0};
    private final ArrayList<Integer> keys_pressed = new ArrayList<>();

    public KeyBuffer() {}

    private void normalize() {
        if (camera_move[0] != 0 || camera_move[1] != 0) {
            float length = (float) Math.sqrt(camera_move[0] * camera_move[0] + camera_move[1] * camera_move[1]);
            camera_move[0] /= length;
            camera_move[1] /= length;
        }
    }

    public float[] getCameraMove() {
        normalize();
        return camera_move;
    }

    public void setCameraX(float x) {
        camera_move[0] = x;
    }

    public void setCameraY(float y) {
        camera_move[1] = y;
    }

    public void addCameraX(float x) {
        camera_move[0] += x;
    }

    public void addCameraY(float y) {
        camera_move[1] += y;
    }

    public void clear() {
        camera_move[0] = 0;
        camera_move[1] = 0;
    }

    public ArrayList<Integer> getKeysPressed() {
        return keys_pressed;
    }

    public void addKeyPressed(int keycode) {
        if (!keys_pressed.contains(keycode)) {
            keys_pressed.add(keycode);
        }
    }

    public void removeKeyPressed(int keycode) {
        keys_pressed.remove((Integer) keycode);
    }

    public void updateCameraMovementFromKeys() {
        clear();
        for (int keycode : keys_pressed) {
            if (keycode == KeyBinds.MOVE_UP || keycode == KeyBinds.MOVE_UP_ALT) {
                addCameraY(1);
            }
            if (keycode == KeyBinds.MOVE_DOWN || keycode == KeyBinds.MOVE_DOWN_ALT) {
                addCameraY(-1);
            }
            if (keycode == KeyBinds.MOVE_LEFT || keycode == KeyBinds.MOVE_LEFT_ALT) {
                addCameraX(-1);
            }
            if (keycode == KeyBinds.MOVE_RIGHT || keycode == KeyBinds.MOVE_RIGHT_ALT) {
                addCameraX(1);
            }
        }
    }

    public boolean isKeyPressed(int keycode) {
        return keys_pressed.contains(keycode);
    }

}
