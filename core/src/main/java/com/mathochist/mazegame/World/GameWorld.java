package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameWorld {

    private GameMap currentMap;
    private TextureAtlas textureAtlas;
    private Texture[] textures;

    private SpriteBatch worldSprite;

    private Tile[][] mapArray;

    public GameWorld(FileHandle mapFile) {

        worldSprite = new SpriteBatch();

        this.currentMap = new GameMap(mapFile);
        
        textures = new Texture[currentMap.getTilesetRegionNames().length];
        textures[0] = new Texture(Gdx.files.internal("placeholder.png"));
        
        /* TOO DO
        for (String num: currentMap.getTilesetRegionNames()){
            
        }
        */
        textures[0] = new Texture(Gdx.files.internal("floor.png"));
        textures[1] = new Texture(Gdx.files.internal("wall.png"));
        textures[2] = new Texture(Gdx.files.internal("bookshelf.png"));
        textures[3] = new Texture(Gdx.files.internal("table.png"));
        textures[4] = new Texture(Gdx.files.internal("chair_right.png"));
        textures[5] = new Texture(Gdx.files.internal("chair_left.png"));
        textures[6] = new Texture(Gdx.files.internal("exit_door.png"));
        

        



        // Load texture atlas for the current map
        //this.textureAtlas = new TextureAtlas(Gdx.files.internal(this.currentMap.getTextureAtlasFile()));

        mapArray = new Tile[currentMap.getMapWidth()][currentMap.getMapHeight()];

        for (int i = 0; i < currentMap.getMapWidth(); i++) {
            for (int j = 0; j < currentMap.getMapHeight(); j++) {
                mapArray[i][j] = new Tile(
                currentMap.getTilesetRegionNames()[currentMap.getMapMatrix()[j][i]],
                false,
                new Sprite(textures[0])
                );
                for (int num: currentMap.getCollisionTiles()){
                    if (currentMap.getMapMatrix()[j][i] == num){
                        mapArray[i][j].SetCollidable(true);
                    }
                }
                mapArray[i][j].getSprite().setRegion(textures[currentMap.getMapMatrix()[j][i]]);
                mapArray[i][j].getSprite().setPosition(
                    i * currentMap.getTileWidth(),
                     Gdx.graphics.getHeight() -(j * currentMap.getTileHeight() +48)
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
        worldSprite.begin();
        // Draw tiles
        for (int i = 0; i < currentMap.getMapWidth(); i++) {
            for (int j = 0; j < currentMap.getMapHeight(); j++) {
                mapArray[i][j].getSprite().draw(worldSprite);
            }
        }
        worldSprite.end();
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
