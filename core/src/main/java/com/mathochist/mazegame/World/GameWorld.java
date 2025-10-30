package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochist.mazegame.Entities.MapEntity;
import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Main;
import com.mathochist.mazegame.Screens.Game.BaseGameScreen;
import com.mathochist.mazegame.Screens.Shader;
import com.mathochist.mazegame.World.Objects.Utils;

import java.util.ArrayList;

/**
 * Represents the game world, including the map, tiles, and entities.
 * Handles rendering and collision detection.
 */
public class GameWorld {

    private final Main game;
    private final GameMap currentMap;
    private final TextureAtlas textureAtlas;

    private final SpriteBatch screenBatch;
    private Shader shader;
    private ShaderProgram shaderProgram;

    private final Tile[][] mapArray;
    private final MapEntity[] mapEntities;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public int tileDrawYOffset = 0; // Adjust based on HUD height or other UI elements

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

        shader = this.currentMap.getMapShader();
        if (shader != null) {
            this.setShader(shader);
        }

    }

    public GameMap getMap() {
        return currentMap;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setShader(Shader shader) {
        ShaderProgram.pedantic = false;

        this.shader = shader;
        this.shaderProgram = new ShaderProgram(shader.getVertexShaderCode(), shader.getFragmentShaderCode());
        screenBatch.setShader(this.shaderProgram);
        System.out.println(shaderProgram.getLog());
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void render(FitViewport viewport) {
        // If a custom shader program is active for the batch, set the screen resolution uniform
        if (shaderProgram != null && shaderProgram.isCompiled()) {
            shaderProgram.setUniformf("u_viewport",
                viewport.getScreenX(),
                viewport.getScreenY(),
                viewport.getScreenWidth(),
                viewport.getScreenHeight()
            );

        }

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
                entity.render(this);
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

    // Debug function TODO: REMOVE IN RELEASE PLEASEEEEEEE
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

    /**
     * Get the collision layer around the player based on their pixel coordinates.
     * <br>
     * <b>Note:</b> This can be used for other objects, not just the player. Slightly misnamed for convenience.
     *
     * @param playerXPixels the player's x coordinate in pixels
     * @param playerYPixels the player's y coordinate in pixels
     * @return a boolean array representing collisions in the order: above, below, left, right
     */
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
            if (entity == null) {
                continue;
            }
            if (!entity.isCollidable()) {
                continue;
            }

            float[] entityBBox = entity.getBBox();

            // brief explanation of how the bbox interacts with the player
            // bbox: x, y, width, height in tile coords
            // playerTilePos: x, y in tile coords (center of player)
            // each direction 'or's with any previous collision detected
            // we check a direction by seeing if the player's tile position plus 1 in that direction
            // would be within the entity's bbox
            // we 'and' that result with 3 parameters to prevent collisions to either side of the collision
            // edge, and on the other side of the entity.
            // These are rounded so we have 0.5 error s.t. we do not get collisions when up against the
            // object on a different axis.

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
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
    }

    /**
     * Convert pixel coordinates to tile indices.
     *
     * @param pixelCoordX the x coordinate in pixels
     * @param pixelCoordY the y coordinate in pixels
     * @return an array containing the x and y coordinates in tile units
     */
    public float[] pixelCoordsToTileIndex(float pixelCoordX, float pixelCoordY) {
        float tileX = ((pixelCoordX) / currentMap.getTileWidth());
        float tileY = ((Gdx.graphics.getHeight() - pixelCoordY - tileDrawYOffset) / currentMap.getTileHeight());
        return new float[]{tileX, tileY};
    }


    private boolean isTileCollidable(int tileX, int tileY) {
        int tileIndex = currentMap.getMapMatrix()[tileY][tileX];
        return java.util.Arrays.stream(currentMap.getCollisionTiles()).anyMatch(tile -> tile == tileIndex);
    }

    /**
     * Get the pixel bbox of a tile given its tile coordinates.
     *
     * @param tileX the x coordinate in tile units
     * @param tileY the y coordinate in tile units
     * @return an array containing the x, y, width, and height of the tile in pixels
     */
    public float[] getPixelBoundsOfTile(int tileX, int tileY) {
        float pixelX = tileX * currentMap.getTileWidth();
        float pixelY = tileY * currentMap.getTileHeight();
        return new float[]{pixelX, pixelY, currentMap.getTileWidth(), currentMap.getTileHeight()};
    }

    /**
     * Get the spawn point of the current map in pixel coordinates, adjusting for viewport height changes.
     *
     * @return an array containing the x and y coordinates of the spawn point in pixels
     */
    public int[] getSpawnPointPixels() {
        float[] spawnPixelCoords = this.getPixels(currentMap.getSpawnX(), currentMap.getSpawnY());
        return new int[]{(int) spawnPixelCoords[0], (int) spawnPixelCoords[1]};
    }

    /**
     * Convert tile coordinates to pixel coordinates, adjusting for viewport height changes.
     * <br>
     * <b>Note:</b> This returns float for increased precision when dealing with entities that may not be
     * aligned to the tile grid. for tile-aligned positions, consider using getPixelBoundsOfTile instead.
     * or take the floor/ceil of the returned values. (See collision detection for an example of this)
     *
     * @param tileX the x coordinate in tile units
     * @param tileY the y coordinate in tile units
     * @return an array containing the x and y coordinates in pixels
     */
    public float[] getPixels(float tileX, float tileY) {
        float x = tileX * currentMap.getTileWidth();
        float y = Gdx.graphics.getHeight() - (tileY * currentMap.getTileHeight() + tileDrawYOffset);
        return new float[]{x, y - deltaViewportHeight};
    }

    /**
     * Scale the world based on viewport height changes. (Scales collision layers and entity positions)<br>
     * <b>Only used by BaseGameScreen, do not call unless you know what you are doing</b>
     *
     * @param oldViewportHeight the previous height of the viewport
     * @param newViewportHeight the new height of the viewport
     */
    public void scaleWorld(float oldViewportHeight, float newViewportHeight) {
        deltaViewportHeight = newViewportHeight - oldViewportHeight;
    }

    /**
     * Calculate the Euclidean distance between two points in tile coordinates.
     *
     * @param tileX1 the x coordinate of the first point in tile units
     * @param tileY1 the y coordinate of the first point in tile units
     * @param tileX2 the x coordinate of the second point in tile units
     * @param tileY2 the y coordinate of the second point in tile units
     * @return the Euclidean distance between the two points
     */
    public static double getTileDistance(float tileX1, float tileY1, float tileX2, float tileY2) {
        return Math.sqrt((tileX1 - tileX2) * (tileX1 - tileX2) + (tileY1 - tileY2) * (tileY1 - tileY2));
    }

    /**
     * Calculate the distance from a point to the nearest edge of an entity's bounding box.
     * <br>
     * <br>
     * <b>CREDITS: <a href="https://stackoverflow.com/questions/5254838/calculating-distance-between-a-point-and-a-rectangular-box-nearest-point">StackOverflow</a></b>
     *
     * @param entityBBox the bounding box of the entity in tile coordinates [x, y, width, height]
     * @param tileX1 the x coordinate of the point in tile units
     * @param tileY1 the y coordinate of the point in tile units
     * @return the distance from the point to the nearest edge of the bounding box
     */
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

    /**
     * Get all map entities within a certain radius of the given tile coordinates.
     *
     * @param tileX the x coordinate in tile units
     * @param tileY the y coordinate in tile units
     * @param radius the radius within which to check for entities (in tile units)
     * @return an array of MapEntity objects within the specified radius
     */
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

    /**
     * Trigger interaction with map entities within a certain radius of the given pixel coordinates.
     *
     * @param pixelX the x coordinate in pixels
     * @param pixelY the y coordinate in pixels
     * @param tileRadius the radius within which to check for interactions (in tile units)
     * @param p the player triggering the interaction
     * @return true if an interaction occurred, false otherwise
     */
    public boolean triggerInteractionByPixel(float pixelX, float pixelY, double tileRadius, Player p) {
        float[] tileCoords = this.pixelCoordsToTileIndex(pixelX, pixelY + deltaViewportHeight);
        return triggerInteraction(tileCoords[0], tileCoords[1], tileRadius, p);
    }

    /**
     * Trigger interaction with map entities within a certain radius of the given tile coordinates.
     *
     * @param tileX the x coordinate in tile units
     * @param tileY the y coordinate in tile units
     * @param radius the radius within which to check for interactions (in tile units)
     * @param p the player triggering the interaction
     * @return true if an interaction occurred, false otherwise
     */
    public boolean triggerInteraction(float tileX, float tileY, double radius, Player p) {
        MapEntity[] nearbyEntities = this.getMapEntitiesInRadius(tileX, tileY, radius);
        MapEntity[] entitiesOutOfRange = Utils.complementOfSubArray(mapEntities, nearbyEntities);
        // end any interactions with entities out of range
        for (MapEntity entity : entitiesOutOfRange) {
            entity.onInteractEnd(p, this);
        }
        for (MapEntity entity : nearbyEntities) {
            if (entity.onInteract(p, this)) {
                return true; // interaction occurred
            }
        }
        return false; // no interaction occurred
    }


}
