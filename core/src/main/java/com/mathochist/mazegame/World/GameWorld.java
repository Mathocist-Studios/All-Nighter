package com.mathochist.mazegame.World;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    // For resizing
    private float deltaViewportWidth;
    private float deltaViewportHeight;

    private Boolean GameWorldFinshed = false;

    private float[] newPlayerCoords = {0,0}; 

    boolean debug = false;

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            if (debug) {
                debug=false;
            } else {
                debug=true;
            }
        }
        if (debug) {
            for (int j = 0; j < currentMap.getMapHeight(); j++) {
                for (int i = 0; i < currentMap.getMapWidth(); i++) {
                        if (mapArray[j][i].isCollidable()) {
                            shapeRenderer.setProjectionMatrix(screenBatch.getProjectionMatrix());
                            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                            shapeRenderer.setColor(new Color(1, 0, 0, 0.4f)); // semi-transparent red
                            float x = i * currentMap.getTileWidth();
                            float y = Gdx.graphics.getHeight() - (j * currentMap.getTileHeight() + tileDrawYOffset);
                            shapeRenderer.rect(x, y, currentMap.getTileWidth(), currentMap.getTileHeight());
                            shapeRenderer.end();
                        }
                    }
                }
            }
        }

    // java
    public void render_collision_layer(Player p) {

        // this has no fucking right being here but its the only place i could find with a usable player reference
        //trying to add it to loadNewWorld caused a million problems so here we are
        if (GameWorldFinshed) {
            newPlayerCoords[0] = newPlayerCoords[0] * currentMap.getTileWidth();
            newPlayerCoords[1] = Gdx.graphics.getHeight() - (newPlayerCoords[1] * currentMap.getTileHeight() + tileDrawYOffset);
            System.out.println("Detected world change, updating player position."); 
            p.setPosition(newPlayerCoords[0],newPlayerCoords[1]);
            newPlayerCoords[0] = 0;
            newPlayerCoords[1] = 0;
            GameWorldFinshed = false;
            System.out.println("Player position after world load: " + p.getX() + ", " + p.getY());
        }

        this.testPlayerBounds(p.getX(), p.getY(), Player.SPRITE_WIDTH, Player.SPRITE_HEIGHT);

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
        // Adjust playerYPixels to account for viewport height changes
        // dont look at this im tired
        float[] playerTilePos = this.pixelCoordsToTileIndex(playerXPixels, playerYPixels + deltaViewportHeight);
        System.out.println(Arrays.toString(playerTilePos));;
        try {
            return new boolean[] {
            mapArray[(int) Math.floor(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable(),
            mapArray[(int) Math.ceil(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable(),
            mapArray[Math.round(playerTilePos[1])][(int) Math.floor(playerTilePos[0])].isCollidable(),
            mapArray[Math.round(playerTilePos[1])][(int) Math.ceil(playerTilePos[0])].isCollidable()}; // above, below, left, right
        } catch (Exception e) {
            return new boolean[] {false, false, false, false};
        }
    }

    public boolean[] testPlayerBounds(float playerXPixels, float playerYPixels, float playerWidthPixels, float playerHeightPixels){
        // literally just a copy and paste of the code above
        // just so the collsion code can be changed without breaking level transitions
        float[] playerTilePos = this.pixelCoordsToTileIndex(playerXPixels, playerYPixels + deltaViewportHeight);
        System.out.println("Player tile position: X=" + playerTilePos[0] + ", Y=" + playerTilePos[1]);
        try {
            return new boolean[] {
            mapArray[(int) Math.floor(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable(),
            mapArray[(int) Math.ceil(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable(),
            mapArray[Math.round(playerTilePos[1])][(int) Math.floor(playerTilePos[0])].isCollidable(),
            mapArray[Math.round(playerTilePos[1])][(int) Math.ceil(playerTilePos[0])].isCollidable()}; // above, below, left, right
        } catch (Exception e) {
            loadNewWorld(playerTilePos);
            return new boolean[] {false, false, false, false};
        }
    }

    public void dispose() {
        textureAtlas.dispose();
    }

    private void loadNewWorld(float[] playerTilePos) {
        // right dont really know how im gonna do this yet so im gonna comment how i think i should
        // first gotta find the closest exit tile to the player
        // then load the map file for that exit tile
        // then set the player's position to the correct spawn point on the new map
        //here we go
        ExitTile[] exitTiles= currentMap.getExitPoints();
        int[][] tilecoords = new int[exitTiles.length][2];
        for (int j = 0; j < exitTiles.length; j++) {
            tilecoords[j]= new int[]{exitTiles[j].x, exitTiles[j].y};
        }
        int[] playerCoords = {Math.round(playerTilePos[0]), Math.round(playerTilePos[1])};
        int moveTileIndex = -1;
        //disgusting code but it works ig
        for (int j = 0; j < exitTiles.length; j++) {
            if(tilecoords[j][0]+1==playerCoords[0]){
                if(tilecoords[j][1]+1==playerCoords[1]){
                    moveTileIndex=j;
                }
            }
            if(tilecoords[j][0]-1==playerCoords[0]){
                if(tilecoords[j][1]-1==playerCoords[1]){
                    moveTileIndex=j;
                }
            }
            if(tilecoords[j][0]+1==playerCoords[0]){
                if(tilecoords[j][1]-1==playerCoords[1]){
                    moveTileIndex=j;
                }
            }
            if(tilecoords[j][0]-1==playerCoords[0]){
                if(tilecoords[j][1]+1==playerCoords[1]){
                    moveTileIndex=j;
                }
            }
        }
        if (moveTileIndex != -1) { 
            System.out.println("Loading new world: " + exitTiles[moveTileIndex].targetMap); 
            //load new world 
            this.currentMap = new GameMap(Gdx.files.internal(exitTiles[moveTileIndex].targetMap)); 
            //reset map array 
            mapArray = new Tile[currentMap.getMapHeight()][currentMap.getMapWidth()];
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
            newPlayerCoords[0] = exitTiles[moveTileIndex].targetX;
            newPlayerCoords[1] = exitTiles[moveTileIndex].targetY;
            GameWorldFinshed = true;
        }
    }

    public Boolean getGameWorldFinshed() {
        return GameWorldFinshed;
    }

    public void setGameWorldFinshed(Boolean status) {
        GameWorldFinshed = status;
    }

    public float[] pixelCoordsToTileIndex(float pixelCoordX, float pixelCoordY) {
        float tileX = ((pixelCoordX) / currentMap.getTileWidth());
        float tileY = ((Gdx.graphics.getHeight() - pixelCoordY - tileDrawYOffset) / currentMap.getTileHeight());
        return new float[]{tileX, tileY};
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
        deltaViewportWidth = newViewportWidth - oldViewportWidth;
        deltaViewportHeight = newViewportHeight - oldViewportHeight;
        System.out.println("Scaled world by deltaViewportWidth: " + deltaViewportWidth + ", deltaViewportHeight: " + deltaViewportHeight);
    }


}
