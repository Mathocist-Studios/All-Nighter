package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mathochist.mazegame.Entities.Player;

public class GameWorld {

    private GameMap currentMap;
    private TextureAtlas textureAtlas;
    private Texture[] textures;

    private SpriteBatch screenBatch;

    private Tile[][] mapArray;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private int tileDrawYOffset = 48; // Adjust based on HUD height or other UI elements

    public GameWorld(FileHandle mapFile, SpriteBatch gameSpriteBatch) {

        screenBatch = gameSpriteBatch;
        this.currentMap = new GameMap(mapFile);

        textures = new Texture[currentMap.getTilesetRegionNames().length];
        textures[0] = new Texture(Gdx.files.internal("placeholder.png"));

        /* TOO DO
        for (String num: currentMap.getTilesetRegionNames()){

        }
        */

        // Manually loading textures for demonstration purposes
        // resize each texture to match tile size
        textures[0] = new Texture(Gdx.files.internal("floor.png"));
        textures[1] = new Texture(Gdx.files.internal("wall.png"));
        textures[2] = new Texture(Gdx.files.internal("bookshelf.png"));
        textures[3] = new Texture(Gdx.files.internal("table.png"));
        textures[4] = new Texture(Gdx.files.internal("chair_right.png"));
        textures[5] = new Texture(Gdx.files.internal("chair_left.png"));
        textures[6] = new Texture(Gdx.files.internal("exit_door.png"));

        // Resize textures to match tile size
        // TODO: Optimize this process to avoid performance issues
        // Consider using a texture atlas or pre-scaled textures
        for (int i = 0; i < textures.length; i++) {
            textures[i].setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            textures[i] = new Texture(textures[i].getTextureData());
            textures[i].getTextureData().prepare();
            Pixmap originalPixmap = textures[i].getTextureData().consumePixmap();
            Pixmap resizedPixmap = new Pixmap(
                currentMap.getTileWidth(),
                currentMap.getTileHeight(),
                originalPixmap.getFormat()
            );
            resizedPixmap.drawPixmap(
                originalPixmap,
                0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(),
                0, 0, currentMap.getTileWidth(), currentMap.getTileHeight()
            );
            originalPixmap.dispose();
            textures[i].dispose();
            textures[i] = new Texture(resizedPixmap);
            resizedPixmap.dispose();
        }


        // Load texture atlas for the current map
        //this.textureAtlas = new TextureAtlas(Gdx.files.internal(this.currentMap.getTextureAtlasFile()));

        // replace declaration
        // mapArray = new Tile[currentMap.getMapWidth()][currentMap.getMapHeight()];
        mapArray = new Tile[currentMap.getMapHeight()][currentMap.getMapWidth()];

        // fill loop: iterate rows (j) then cols (i) and write mapArray[j][i]
        for (int j = 0; j < currentMap.getMapHeight(); j++) {
            for (int i = 0; i < currentMap.getMapWidth(); i++) {
                mapArray[j][i] = new Tile(
                    currentMap.getTilesetRegionNames()[currentMap.getMapMatrix()[j][i]],
                    this.isTileCollidable(i, j),
                    new Sprite(textures[0])
                );
                mapArray[j][i].getSprite().setRegion(textures[currentMap.getMapMatrix()[j][i]]);
                mapArray[j][i].getSprite().setPosition(
                    i * currentMap.getTileWidth(),
                    Gdx.graphics.getHeight() - (j * currentMap.getTileHeight() + tileDrawYOffset)
                );
            }
        }
    }

    public GameMap getMap() {
        return currentMap;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void render() {
        // Rendering logic for the game world goes here
        screenBatch.begin();
        // Draw tiles
        for (int j = 0; j < currentMap.getMapHeight(); j++) {
            for (int i = 0; i < currentMap.getMapWidth(); i++) {
                mapArray[j][i].getSprite().draw(screenBatch);
            }
        }
        screenBatch.end();
    }

    // java
    public void render_collision_layer(Player p) {
        float[] playerTilePos = this.pixelCoordsToTileIndex(p.getX(), p.getY());
        boolean[] collisionLayer = this.getCollisionLayer(p.getX(), p.getY(), Player.SPRITE_WIDTH, Player.SPRITE_HEIGHT);
        shapeRenderer.setProjectionMatrix(screenBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.4f)); // semi-transparent green

        float px = playerTilePos[0];
        float py = playerTilePos[1];

        // Above (row index decreases)
        if (collisionLayer[0]) {
            float ty = py - 1;
            if (ty >= 0) {
                float x = px * currentMap.getTileWidth();
                float y = Gdx.graphics.getHeight() - (ty * currentMap.getTileHeight() + tileDrawYOffset);
                shapeRenderer.rect(x, y, currentMap.getTileWidth(), currentMap.getTileHeight());
            }
        }
        // Below (row index increases)
        if (collisionLayer[1]) {
            float ty = py + 1;
            if (ty < currentMap.getMapHeight()) {
                float x = px * currentMap.getTileWidth();
                float y = Gdx.graphics.getHeight() - (ty * currentMap.getTileHeight() + tileDrawYOffset);
                shapeRenderer.rect(x, y, currentMap.getTileWidth(), currentMap.getTileHeight());
            }
        }
        // Left
        if (collisionLayer[2]) {
            float tx = px - 1;
            if (tx >= 0) {
                float x = tx * currentMap.getTileWidth();
                float y = Gdx.graphics.getHeight() - (py * currentMap.getTileHeight() + tileDrawYOffset);
                shapeRenderer.rect(x, y, currentMap.getTileWidth(), currentMap.getTileHeight());
            }
        }
        // Right
        if (collisionLayer[3]) {
            float tx = px + 1;
            if (tx < currentMap.getMapWidth()) {
                float x = tx * currentMap.getTileWidth();
                float y = Gdx.graphics.getHeight() - (py * currentMap.getTileHeight() + tileDrawYOffset);
                shapeRenderer.rect(x, y, currentMap.getTileWidth(), currentMap.getTileHeight());
            }
        }
        shapeRenderer.end();
    }


    public boolean[] getCollisionLayer(float playerXPixels, float playerYPixels, float playerWidthPixels, float playerHeightPixels) {
        float[] playerTilePos = this.pixelCoordsToTileIndex(playerXPixels, playerYPixels);
        return new boolean[] {
            mapArray[(int) Math.floor(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable(),
            mapArray[(int) Math.ceil(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable(),
            mapArray[Math.round(playerTilePos[1])][(int) Math.floor(playerTilePos[0])].isCollidable(),
            mapArray[Math.round(playerTilePos[1])][(int) Math.ceil(playerTilePos[0])].isCollidable()}; // above, below, left, right
    }

    public void dispose() {
        textureAtlas.dispose();
    }

    public float[] pixelCoordsToTileIndex(float pixelCoordX, float pixelCoordY) {
        float tileX = (pixelCoordX / currentMap.getTileWidth());
        float tileY = ((Gdx.graphics.getHeight() - pixelCoordY - tileDrawYOffset) / currentMap.getTileHeight());
        return new float[]{tileX, tileY};
    }

    public int[] getPlayerTilePosition(float playerXPixels, float playerYPixels) {
        int tileX = (int) (playerXPixels / currentMap.getTileWidth());
        int tileY = (int) ((Gdx.graphics.getHeight() - playerYPixels - tileDrawYOffset) / currentMap.getTileHeight());
        return new int[]{tileX, tileY};
    }

    public boolean isTileCollidable(int tileX, int tileY) {
        int tileIndex = currentMap.getMapMatrix()[tileY][tileX];
        return java.util.Arrays.stream(currentMap.getCollisionTiles()).anyMatch(tile -> tile == tileIndex);
    }

    public float[] getPixelBoundsOfTile(int tileX, int tileY) {
        float pixelX = tileX * currentMap.getTileWidth();
        float pixelY = tileY * currentMap.getTileHeight();
        return new float[]{pixelX, pixelY, currentMap.getTileWidth(), currentMap.getTileHeight()};
    }

    public int[] getSpawnPointPixels() {
        int x = currentMap.getSpawnX() * currentMap.getTileWidth();
        int y = Gdx.graphics.getHeight() - (currentMap.getSpawnY() * currentMap.getTileHeight() + tileDrawYOffset);
        return new int[]{x, y};
    }

    public void scaleWorld(float oldViewportWidth, float oldViewportHeight, float newViewportWidth, float newViewportHeight) {
        float scaleX = newViewportWidth / oldViewportWidth;
        float scaleY = newViewportHeight / oldViewportHeight;

    }


}
