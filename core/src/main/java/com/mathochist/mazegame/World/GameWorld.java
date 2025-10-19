package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameWorld {

    private GameMap currentMap;
    private TextureAtlas textureAtlas;

    public GameWorld(FileHandle mapFile) {

        this.currentMap = new GameMap(mapFile);

        // Load texture atlas for the current map
        this.textureAtlas = new TextureAtlas(Gdx.files.internal(this.currentMap.getTextureAtlasFile()));

    }

    public GameMap getMap() {
        return currentMap;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void render() {
        // Rendering logic for the game world goes here
    }

    public int[] getCollisionLayer(int playerXPixels, int playerYPixels) {
        // Logic to get collision layer data based on player position
        return null;
    }

    public void dispose() {
        textureAtlas.dispose();
    }

    public int[] pixelCoordsToTileIndex(int pixelCoordX, int pixelCoordY) {
        int tileX = pixelCoordX / currentMap.getTileWidth();
        int tileY = pixelCoordY / currentMap.getTileHeight();
        return new int[]{tileX, tileY};
    }

    public int[] getPlayerTilePosition(int playerXPixels, int playerYPixels) {
        return pixelCoordsToTileIndex(playerXPixels, playerYPixels);
    }

    public boolean isTileCollidable(int tileX, int tileY) {
        int tileIndex = currentMap.getMapMatrix()[tileY][tileX];
        return java.util.Arrays.stream(currentMap.getCollisionTiles()).anyMatch(tile -> tile == tileIndex);
    }

    public int[] getPixelBoundsOfTile(int tileX, int tileY) {
        int pixelX = tileX * currentMap.getTileWidth();
        int pixelY = tileY * currentMap.getTileHeight();
        return new int[]{pixelX, pixelY, currentMap.getTileWidth(), currentMap.getTileHeight()};
    }

}
