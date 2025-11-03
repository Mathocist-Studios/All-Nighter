package com.mathochist.mazegame.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochist.mazegame.Movement.KeyBinds;
import com.mathochist.mazegame.Movement.KeyBuffer;
import com.mathochist.mazegame.Rendering.RenderBuffer;
import com.mathochist.mazegame.Rendering.RenderObject;
import com.mathochist.mazegame.World.GameWorld;


/**
 * Player entity controlled by user input.
 * Handles movement, animation, and interaction.
 */
// TODO: Refactor to extend Entity class
public class Player {
    private Texture spriteSheet;
    private final Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private TextureRegion currentFrame;
    private final Sprite playerSprite;

    private final SpriteBatch screenBatch;

    private final int FRAME_COLS = 3;
    private final int FRAME_ROWS = 4;
    private final int PADDING_BELOW = 4;
    private float stateTime = 0f; // time tracker for animation

    private final KeyBuffer keyBuffer;
    private float x, y;

    private final OrthographicCamera camera;
    private final GameWorld world;

    private boolean is_sprinting = false;

    public final static float MOVE_SPEED = 300; // pixels per second
    public final static float SPRITE_WIDTH = 19;
    public final static float SPRITE_HEIGHT = 25;
    public final static float INTERACTION_RANGE = 3; // tiles

    public Player(OrthographicCamera camera, SpriteBatch batch, GameWorld world, float startX, float startY) {

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

        // remove padding below frames
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                tmp[i][j].setRegion(
                    tmp[i][j].getRegionX(),
                    tmp[i][j].getRegionY(),
                    tmp[i][j].getRegionWidth(),
                    tmp[i][j].getRegionHeight() - PADDING_BELOW
                );
            }
        }

        // Flatten into 1D array
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Create animations for each direction
        walkUp    = new Animation<>(0.15f, tmp[3]);
        walkLeft  = new Animation<>(0.15f, tmp[1]);
        walkDown  = new Animation<>(0.15f, tmp[0]);
        walkRight = new Animation<>(0.15f, tmp[2]);

        // Start with first frame facing down
        currentFrame = tmp[2][0];

        spriteSheet = new Texture("player.png");

        // Center player on screen
        x = startX;
        y = startY;

        playerSprite = new Sprite(spriteSheet);
        keyBuffer = new KeyBuffer();
    }

    public void update(float game_delta, RenderBuffer renderBuffer) {
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

        // base move speed buffer
        int moveSpeed = (int) MOVE_SPEED;

        // check extra keys
        if (keyBuffer.isKeyPressed(KeyBinds.SPRINT)) {
            // sprinting logic
            moveSpeed += 300;
            is_sprinting = true;
        } else {
            is_sprinting = false;
        }
        if (keyBuffer.isKeyPressed(KeyBinds.INTERACT)) {
            // interaction logic
            world.triggerInteractionByPixel(x, y, INTERACTION_RANGE, this);
        }
        if (keyBuffer.isKeyPressed(KeyBinds.ESCAPE_GAME)) {
            // escape logic
            Gdx.app.exit();
        }

        float[] move = keyBuffer.getCameraMove();

        // Calculate new position and basic collision detection
        float deltaX = move[0] * delta_time * moveSpeed;
        float deltaY = move[1] * delta_time * moveSpeed;

        // Use player's position for collision, not camera
        // System.out.println("Player X: " + (x + SPRITE_WIDTH / 2f) + " Y: " + (y + SPRITE_HEIGHT / 2f));
        boolean[] collisionLayer = world.getCollisionLayer(x, y);
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
            camera.translate(deltaX, deltaY); // translate camera not player
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

        // Update player position based on camera
        this.x = camera.position.x - SPRITE_WIDTH / 2f;
        this.y = camera.position.y - SPRITE_HEIGHT / 2f;

        this.render(game_delta, renderBuffer);
    }

    public void render(float delta, RenderBuffer renderBuffer) {
        playerSprite.setPosition(x, y);

//        screenBatch.begin();
//        playerSprite.draw(screenBatch);
//        screenBatch.end();
        renderBuffer.addToBuffer(
            new RenderObject(
                playerSprite,
                screenBatch,
                (int) Math.ceil(world.pixelCoordsToTileIndex(x, y)[1]) // use y for z-index sorting
            )
        );

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

    public KeyBuffer getKeyBuffer() {
        return keyBuffer;
    }

    public void setPosition(float newX, float newY) {
        camera.position.set(newX, newY, 0); // Center camera
        camera.update();
    }

    public boolean isSprinting() {
        return is_sprinting;
    }

}
