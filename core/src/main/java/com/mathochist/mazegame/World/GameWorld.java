package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Screens.Game.BaseGameScreen;
import com.mathochist.mazegame.World.Objects.Utils;

import java.util.ArrayList;

public class GameWorld {

    private final Main game;
    private final GameMap currentMap;
    private final TextureAtlas textureAtlas;

    private final SpriteBatch screenBatch;

    private final Tile[][] mapArray;
    private final MapEntity[] mapEntities;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final int tileDrawYOffset = 48; // Adjust based on HUD height or other UI elements

    // For resizing
    private float deltaViewportHeight;

    // Debug
    private boolean debug = false;

    public GameWorld(Main game, FileHandle mapFile, SpriteBatch gameSpriteBatch) {

        this.game = game;
        screenBatch = gameSpriteBatch;
        this.currentMap = new GameMap(mapFile);

        // textures = new Texture[currentMap.getTilesetRegionNames().length];
        // textures[0] = new Texture(Gdx.files.internal("placeholder.png"));

        /* TOO DO
        for (String num: currentMap.getTilesetRegionNames()){

        }
        */

        // Manually loading textures for demonstration purposes
        // resize each texture to match tile size
//        textures[0] = new Texture(Gdx.files.internal("floor.png"));
//        textures[1] = new Texture(Gdx.files.internal("wall.png"));
//        textures[2] = new Texture(Gdx.files.internal("bookshelf.png"));
//        textures[3] = new Texture(Gdx.files.internal("table.png"));
//        textures[4] = new Texture(Gdx.files.internal("chair_right.png"));
//        textures[5] = new Texture(Gdx.files.internal("chair_left.png"));
//        textures[6] = new Texture(Gdx.files.internal("exit_door.png"));

        textureAtlas = new TextureAtlas(Gdx.files.internal(this.currentMap.getTextureAtlasFile()));
        TextureRegion[] tileTextures = new TextureRegion[currentMap.getTilesetRegionNames().length];
        for (int i = 0; i < currentMap.getTilesetRegionNames().length; i++) {
            System.out.println(currentMap.getTilesetRegionNames()[i]);
            tileTextures[i] = textureAtlas.findRegion(currentMap.getTilesetRegionNames()[i]);
        }

        // replace declaration
        // mapArray = new Tile[currentMap.getMapWidth()][currentMap.getMapHeight()];
        mapArray = new Tile[currentMap.getMapHeight()][currentMap.getMapWidth()];

        // fill loop: iterate rows (j) then cols (i) and write mapArray[j][i]
        for (int j = 0; j < currentMap.getMapHeight(); j++) {
            for (int i = 0; i < currentMap.getMapWidth(); i++) {
                mapArray[j][i] = new Tile(
                    currentMap.getTilesetRegionNames()[currentMap.getMapMatrix()[j][i]],
                    this.isTileCollidable(i, j),
                    new Sprite(tileTextures[0])
                );
                mapArray[j][i].getSprite().setRegion(tileTextures[currentMap.getMapMatrix()[j][i]]);
                mapArray[j][i].getSprite().setPosition(
                    i * currentMap.getTileWidth(),
                    Gdx.graphics.getHeight() - (j * currentMap.getTileHeight() + tileDrawYOffset)
                );
            }
        }

        mapEntities = new MapEntity[currentMap.getNumberOfMapEntities()];
        // Load map entities (NPCs, items, etc.)
        JsonValue entitiesData = currentMap.getObjectsLayer();
        for (JsonValue entityData : entitiesData) {
            // Create MapEntity instances based on entityData
            // and add them to mapEntities array

            // get key
            String entityKey = entityData.name(); // e.g. com.mathochist.mazegame.World.Objects.Bookcase
            System.out.println("Loaded entity: " + entityKey);

            int width = entityData.getInt("width");
            int height = entityData.getInt("height");
            boolean collidable = entityData.getBoolean("collidable");

            JsonValue positionData = entityData.get("positions");
            Json json = new Json();
            int[][] positions = json.readValue(int[][].class, positionData);

            for (int[] pos : positions) {
                // Instantiate specific MapEntity subclasses based on entityKey
                MapEntity entity = Utils.instantiate(entityKey, MapEntity.class,
                    screenBatch,
                    this.textureAtlas,
                    pos[0],
                    pos[1],
                    width,
                    height,
                    collidable
                );

                // Add entity to mapEntities array
                // For simplicity, we just add to the first available slot
                for (int k = 0; k < mapEntities.length; k++) {
                    if (mapEntities[k] == null) {
                        mapEntities[k] = entity;
                        break;
                    }
                }
            }

            System.out.println("Total entities loaded: " + mapEntities.length);

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
        // Draw map entities
        for (MapEntity entity : mapEntities) {
            if (entity != null) {
                entity.render();
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
            // Optionally whole render collision layer for debugging
            for (int j = 0; j < currentMap.getMapHeight(); j++) {
                for (int i = 0; i < currentMap.getMapWidth(); i++) {
                    if (mapArray[j][i].isCollidable()) {
                        shapeRenderer.setProjectionMatrix(screenBatch.getProjectionMatrix());
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                        shapeRenderer.setColor(new Color(1, 0, 0, 0.4f)); // semi-transparent red
                        float x = i * currentMap.getTileWidth();
                        float y = Gdx.graphics.getHeight() - (j * currentMap.getTileHeight() + tileDrawYOffset);
                        shapeRenderer.rect(x, y - deltaViewportHeight, currentMap.getTileWidth(), currentMap.getTileHeight());
                        shapeRenderer.end();
                    }
                }
            }

            // highlight all entities for debugging
            for (MapEntity entity : mapEntities) {
                if (entity != null) {
                    float[] bbox = entity.getBBox();
                    shapeRenderer.setProjectionMatrix(screenBatch.getProjectionMatrix());
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(new Color(0, 0, 1, 0.4f)); // semi-transparent blue
                    // bbox: x, y, width, height in tile coords
                    float x = bbox[0] * currentMap.getTileWidth();
                    float y = Gdx.graphics.getHeight() - (bbox[1] * currentMap.getTileHeight() + tileDrawYOffset) - (bbox[3] * currentMap.getTileHeight());
                    shapeRenderer.rect(x, y - deltaViewportHeight, bbox[2] * currentMap.getTileWidth(), bbox[3] * currentMap.getTileHeight());
                    shapeRenderer.end();
                }
            }
        }

    }

    // Debug function TODO: REMOVE IN RELEASE
    public void render_collision_layer(Player p) {
        float[] playerTilePos = this.pixelCoordsToTileIndex(p.getX(), p.getY());
        boolean[] collisionLayer = this.getCollisionLayer(p.getX(), p.getY());
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

    public boolean[] getCollisionLayer(float playerXPixels, float playerYPixels) {
        // Adjust playerYPixels to account for viewport height changes (because the viewport scales height during resizing)
        float[] playerTilePos = this.pixelCoordsToTileIndex(playerXPixels, playerYPixels + deltaViewportHeight);
        boolean isTransition = this.checkForWorldTransition(playerTilePos);
        if (isTransition) {
            // If a world transition occurred, return no collisions to avoid issues during transition
            return new boolean[] {false, false, false, false};
        }
        boolean[] entityCollisionLayer = this.getCollisionLayerWithEntities(playerTilePos);
        return new boolean[] {
            mapArray[(int) Math.floor(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable() || entityCollisionLayer[0],
            mapArray[(int) Math.ceil(playerTilePos[1])][Math.round(playerTilePos[0])].isCollidable() || entityCollisionLayer[1],
            mapArray[Math.round(playerTilePos[1])][(int) Math.floor(playerTilePos[0])].isCollidable() || entityCollisionLayer[2],
            mapArray[Math.round(playerTilePos[1])][(int) Math.ceil(playerTilePos[0])].isCollidable() || entityCollisionLayer[3]
        }; // above, below, left, right
    }

    private boolean checkForWorldTransition(float[] playerTilePos) {
        ExitTile[] exitTiles = currentMap.getExitPoints();
        for (ExitTile exitTile : exitTiles) {
            if (Math.round(exitTile.getX()) == Math.round(playerTilePos[0]) &&
                Math.round(exitTile.getY()) == Math.round(playerTilePos[1])) {
                System.out.println("On exit tile");
                System.out.println(exitTile);
                loadNewWorld(exitTile);
                return true;
            }
        }
        return false;
    }

    private boolean[] getCollisionLayerWithEntities(float[] playerTilePos) {
        boolean[] collisionLayer = new boolean[]{false, false, false, false}; // above, below, left, right

        for (MapEntity entity : mapEntities) {
            if (!entity.isCollidable()) {
                continue;
            }

            float[] entityBBox = entity.getBBox();

            // brief explanation of how the bbox interacts with the player
            // bbox: x, y, width, height in tile coords
            // playerTilePos: x, y in tile coords (center of player)
            // each direction 'or's with any previous collision detected
            // we check a direction by seeing if the player's tile position plus/minus 1 in that direction
            // would still be within the entity's bbox
            // we 'and' that with 3 parameters to prevent collisions to either side of the collision
            // edge, and on the other side of the entity.
            // These are rounded so we have 0.5 error s.t. we do not get collisions when up against the
            // object.

            // above
            collisionLayer[0] = collisionLayer[0] || Math.floor(playerTilePos[1]) <= entityBBox[1] + entityBBox[3] &&
                    Math.round(playerTilePos[0]) >= entityBBox[0] &&
                    Math.round(playerTilePos[0]) < entityBBox[0] + entityBBox[2] &&
                    Math.floor(playerTilePos[1]) - 1 >= entityBBox[1];

            // below
            collisionLayer[1] = collisionLayer[1] || Math.floor(playerTilePos[1]) >= entityBBox[1] &&
                    Math.round(playerTilePos[0]) >= entityBBox[0] &&
                    Math.round(playerTilePos[0]) < entityBBox[0] + entityBBox[2] &&
                    Math.floor(playerTilePos[1]) + 1 <= entityBBox[1] + entityBBox[3];

            // left
            collisionLayer[2] = collisionLayer[2] || Math.ceil(playerTilePos[0]) <= entityBBox[0] + entityBBox[2] &&
                    Math.round(playerTilePos[1]) > entityBBox[1] &&
                    Math.round(playerTilePos[1]) <= entityBBox[1] + entityBBox[3] &&
                    Math.ceil(playerTilePos[0]) - 1 >= entityBBox[0];

            // right
            collisionLayer[3] = collisionLayer[3] || Math.ceil(playerTilePos[0]) >= entityBBox[0] &&
                    Math.round(playerTilePos[1]) > entityBBox[1] &&
                    Math.round(playerTilePos[1]) <= entityBBox[1] + entityBBox[3] &&
                    Math.ceil(playerTilePos[0]) + 1 <= entityBBox[0] + entityBBox[2];

        }

        return collisionLayer;

    }

    public void dispose() {
        textureAtlas.dispose();
    }

    public float[] pixelCoordsToTileIndex(float pixelCoordX, float pixelCoordY) {
        float tileX = ((pixelCoordX) / currentMap.getTileWidth());
        float tileY = ((Gdx.graphics.getHeight() - pixelCoordY - tileDrawYOffset) / currentMap.getTileHeight());
        return new float[]{tileX, tileY};
    }

    private boolean isTileCollidable(int tileX, int tileY) {
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
        return new int[]{x, (int) (y - deltaViewportHeight)};
    }

    public float[] getPixels(float tileX, float tileY) {
        float x = tileX * currentMap.getTileWidth();
        float y = Gdx.graphics.getHeight() - (tileY * currentMap.getTileHeight() + tileDrawYOffset);
        return new float[]{x, y - deltaViewportHeight};
    }

    public void scaleWorld(float oldViewportHeight, float newViewportHeight) {
        System.out.println(deltaViewportHeight);
        deltaViewportHeight = newViewportHeight - oldViewportHeight;
    }

    public static double getTileDistance(float tileX1, float tileY1, float tileX2, float tileY2) {
        return Math.sqrt((tileX1 - tileX2) * (tileX1 - tileX2) + (tileY1 - tileY2) * (tileY1 - tileY2));
    }

    public static double distanceFromBBox(float[] entityBBox, float tileX1, float tileY1) {
        float rx_min = entityBBox[0];
        float rx_max = entityBBox[0] + entityBBox[2];
        float ry_min = entityBBox[1];
        float ry_max = entityBBox[1] + entityBBox[3];

        if (rx_min <= tileX1 && tileX1 <= rx_max && ry_min <= tileY1 && tileY1 <= ry_max) {
            return Math.min(Math.min(tileX1 - rx_min, rx_max - tileX1), Math.min(tileY1 - ry_min, ry_max - tileY1));
        }

        double dx = Math.max(0, Math.max(rx_min - tileX1, tileX1 - rx_max));
        double dy = Math.max(0, Math.max(ry_min - tileY1, tileY1 - ry_max));
        return Math.sqrt(dx*dx + dy*dy);
    }

    public MapEntity[] getMapEntitiesInRadius(float tileX, float tileY, double radius) {
        ArrayList<MapEntity> array_output = new ArrayList<>();
        for (MapEntity entity : mapEntities) {
            double dist = distanceFromBBox(entity.getBBox(), tileX, tileY);
            if (dist <= radius) {
                array_output.add(entity);
            }
        }
        MapEntity[] output = new MapEntity[array_output.size()];
        array_output.toArray(output);
        return output;
    }

    private void loadNewWorld(ExitTile targetExit) {
        // right dont really know how im gonna do this yet so im gonna comment how i think i should
        // first gotta find the closest exit tile to the player
        // then load the map file for that exit tile
        // then set the player's position to the correct spawn point on the new map
        // here we go

        System.out.println("Loading new world: " + targetExit.getTargetMap());
        // load new screen
        BaseGameScreen newScreen = Utils.instantiate(targetExit.getTargetMap(), BaseGameScreen.class,
            game,
            targetExit.getTargetX(),
            targetExit.getTargetY()
        );
        game.setScreen(newScreen);
    }


}
