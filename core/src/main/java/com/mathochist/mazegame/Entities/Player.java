package com.mathochist.mazegame.Entities;

import java.util.Dictionary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochist.mazegame.Movement.KeyBuffer;
import com.mathochist.mazegame.World.GameWorld;

public class Player {
    private Texture spriteSheet;
    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private TextureRegion currentFrame;
    private Sprite playerSprite;

    private SpriteBatch screenBatch;

    private Dictionary<String, Texture> spriteTable;

    private final int FRAME_COLS = 4;
    private final int FRAME_ROWS = 4;
    private float stateTime = 0f; // time tracker for animation

    private KeyBuffer keyBuffer;
    private float x, y;

    private OrthographicCamera camera;
    private GameWorld world;

    private final static float MOVE_SPEED = 100; // pixels per second
    private final static float SPRITE_WIDTH = 19;
    private final static float SPRITE_HEIGHT = 25;

    public Player(OrthographicCamera camera, SpriteBatch batch, GameWorld world) {

        this.camera = camera;
        this.screenBatch = batch;
        this.world = world;

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

        // Center player on screen
        x = Gdx.graphics.getWidth() / 2f - SPRITE_WIDTH / 2f;
        y = Gdx.graphics.getHeight() / 2f - SPRITE_HEIGHT / 2f;

        playerSprite = new Sprite(spriteSheet);
        keyBuffer = new KeyBuffer();
    }

    public void update(float game_delta) {
//        moving=false;
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
//            x -= speed * game_delta;
//            //playerSprite.setTexture(spriteTable.get("left"));
//            direction="left";
//            moving=true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
//            x += speed * game_delta;
//            //playerSprite.setTexture(spriteTable.get("right"));
//            direction="right";
//            moving=true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
//            y += speed * game_delta;
//            //playerSprite.setTexture(spriteTable.get("up"));
//            direction="up";
//            moving=true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
//            y -= speed * game_delta;
//            //playerSprite.setTexture(spriteTable.get("down"));
//            direction="down";
//            moving=true;
//        }
//
//        // Only animate when moving
//        if (moving) {
//            stateTime += game_delta;
//        } else {
//            stateTime = 0; // reset to first frame when idle
//        }
//
//        // Choose correct frame
//        switch (direction) {
//            case "up" -> currentFrame = walkUp.getKeyFrame(stateTime, true);
//            case "down" -> currentFrame = walkDown.getKeyFrame(stateTime, true);
//            case "left" -> currentFrame = walkLeft.getKeyFrame(stateTime, true);
//            case "right" -> currentFrame = walkRight.getKeyFrame(stateTime, true);
//        }
//        playerSprite.setRegion(currentFrame);
//
//        // Keep player on screen
//        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - 19));
//        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - (spriteSheet.getHeight() / FRAME_ROWS)));
//
//        this.render(game_delta);

        float delta_time = Gdx.graphics.getDeltaTime();
        keyBuffer.updateCameraMovementFromKeys();
        float[] move = keyBuffer.getCameraMove();

        // Calculate new position and basic collision detection
        float deltaX = move[0] * delta_time * MOVE_SPEED;
        float deltaY = move[1] * delta_time * MOVE_SPEED;

        // Use player's position for collision, not camera
        boolean[] collisionLayer = world.getCollisionLayer(x + SPRITE_WIDTH / 2f, y + SPRITE_HEIGHT / 2f);
        if (deltaY > 0 && collisionLayer[0]) { // moving up
            deltaY = 0;
        }
        if (deltaY < 0 && collisionLayer[1]) { // moving down
            deltaY = 0;
        }
        if (deltaX < 0 && collisionLayer[2]) { // moving left
            deltaX = 0;
        }
        if (deltaX > 0 && collisionLayer[3]) { // moving right
            deltaX = 0;
        }

        if (move[0] != 0 || move[1] != 0) {
            camera.translate(deltaX, deltaY);
            camera.update();
            stateTime += game_delta;
            // Choose correct frame
            if (move[1] > 0) {
                currentFrame = walkUp.getKeyFrame(stateTime, true);
            } else if (move[1] < 0) {
                currentFrame = walkDown.getKeyFrame(stateTime, true);
            } else if (move[0] < 0) {
                currentFrame = walkLeft.getKeyFrame(stateTime, true);
            } else if (move[0] > 0) {
                currentFrame = walkRight.getKeyFrame(stateTime, true);
            }
        } else {
            stateTime = 0; // reset to first frame when idle
            currentFrame = walkDown.getKeyFrame(stateTime, true);
        }
        playerSprite.setRegion(currentFrame);

        keyBuffer.clear();

//        System.out.println(camera.position.x);
//        System.out.println(camera.position.y);
        this.x = camera.position.x - SPRITE_WIDTH / 2f;
        this.y = camera.position.y - SPRITE_HEIGHT / 2f;

        this.render(game_delta);
    }

    public void render(float delta) {
        playerSprite.setPosition(x, y);

        screenBatch.begin();
        playerSprite.draw(screenBatch);
        screenBatch.end();
    }

    public void dispose() {
        spriteSheet.dispose();
        screenBatch.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public KeyBuffer getKeyBuffer() {
        return keyBuffer;
    }

}
