package com.mathochist.mazegame.Entities;

import java.util.Dictionary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private Texture spriteSheet;
    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private TextureRegion currentFrame;
    private float x, y;
    private float speed = 100f; // pixels per second
    private Sprite playerSprite;

    private SpriteBatch playerBatch;

    private Dictionary<String, Texture> spriteTable;

    private final int FRAME_COLS = 4;
    private final int FRAME_ROWS = 4;
    private float stateTime = 0f; // time tracker for animation

    private boolean moving;

    private String direction = "down";

    public Player() {
        // Load the sprite sheet as a Texture
        spriteSheet = new Texture(Gdx.files.internal("player.png"));
        TextureRegion[][] tmp = TextureRegion.split(
            spriteSheet,
            spriteSheet.getWidth() / FRAME_COLS,
            spriteSheet.getHeight() / FRAME_ROWS
        );

        // Flatten into 1D array
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Create animations for each direction
        walkUp    = new Animation<>(0.15f, tmp[2]);
        walkLeft  = new Animation<>(0.15f, tmp[1]);
        walkDown  = new Animation<>(0.15f, tmp[0]);
        walkRight = new Animation<>(0.15f, tmp[3]);

        // Start with first frame facing down
        currentFrame = tmp[2][0];

        spriteSheet = new Texture("player.png");
        x = Gdx.graphics.getWidth() / 2f - 19 / 2f;
        y = Gdx.graphics.getHeight() / 2f - 25 / 2f;

        playerBatch = new SpriteBatch();

        playerSprite = new Sprite(spriteSheet);
    }

    public void update(float delta) {
        moving=false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;
            //playerSprite.setTexture(spriteTable.get("left"));
            direction="left";
            moving=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;
            //playerSprite.setTexture(spriteTable.get("right"));
            direction="right";
            moving=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;
            //playerSprite.setTexture(spriteTable.get("up"));
            direction="up";
            moving=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed * delta;
            //playerSprite.setTexture(spriteTable.get("down"));
            direction="down";
            moving=true;
        }

        // Only animate when moving
        if (moving) {
            stateTime += delta;
        } else {
            stateTime = 0; // reset to first frame when idle
        }

        // Choose correct frame
        switch (direction) {
            case "up" -> currentFrame = walkUp.getKeyFrame(stateTime, true);
            case "down" -> currentFrame = walkDown.getKeyFrame(stateTime, true);
            case "left" -> currentFrame = walkLeft.getKeyFrame(stateTime, true);
            case "right" -> currentFrame = walkRight.getKeyFrame(stateTime, true);
        }
        playerSprite.setRegion(currentFrame);

        // Keep player on screen
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - 19));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - (spriteSheet.getHeight() / FRAME_ROWS)));

        this.render(delta);
    }

    public void render(float delta) {
        playerSprite.setPosition(x, y);

        playerBatch.begin();
        playerSprite.draw(playerBatch);
        playerBatch.end();
    }

    public void draw(SpriteBatch batch) {
    }

    public void dispose() {
        spriteSheet.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }   
}
