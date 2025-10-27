package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Arrays;

public class GameMap {

    private FileHandle mapFile;
    private JsonReader jsonReader;
    private JsonValue mapData;

    public GameMap(FileHandle mapFile) {

        this.mapFile = mapFile;
        this.jsonReader = new JsonReader();
        this.mapData = jsonReader.parse(this.mapFile);

    }

    public FileHandle getMapFile() {
        return mapFile;
    }

    public JsonValue getMapData() {
        return mapData;
    }

    public String getMapName() {
        return mapData.get("metadata").getString("name");
    }

    public String getMapAuthor() {
        return mapData.get("metadata").getString("author");
    }

    public String getMapVersion() {
        return mapData.get("metadata").getString("version");
    }

    public int getTileWidth() {
        return mapData.get("metadata").get("tilesets").getInt("tile_width");
    }

    public int getTileHeight() {
        return mapData.get("metadata").get("tilesets").getInt("tile_height");
    }

    public int getMapWidth() {
        return mapData.get("metadata").getInt("map_width");
    }

    public int getMapHeight() {
        return mapData.get("metadata").getInt("map_height");
    }

    public int getSpawnX() {
        return mapData.get("metadata").get("spawn_point").getInt("x");
    }

    public int getSpawnY() {
        return mapData.get("metadata").get("spawn_point").getInt("y");
    }

    public Music getBackgroundMusic() {
        String musicFile = mapData.get("metadata").getString("background_music");
        return Gdx.audio.newMusic(Gdx.files.internal(musicFile));
    }

    public String[] getTilesetRegionNames() {
        JsonValue tileset = mapData.get("tiles");
        Json json = new Json();
        return json.readValue(String[].class, tileset);
    }

    public int[] getCollisionTiles() {
        JsonValue collisionTiles = mapData.get("collision_tiles");
        Json json = new Json();
        return json.readValue(int[].class, collisionTiles);
    }

    public int[][] getMapMatrix() {
        JsonValue matrixData = this.mapData.get("map_matrix");
        Json json = new Json();
        return json.readValue(int[][].class,  matrixData);
    }

    public String getTextureAtlasFile() {
        return mapData.get("metadata").getString("texture_atlas");
    }

    public JsonValue getObjectsLayer() {
        return mapData.get("metadata").get("map_objects");
    }

    public int getNumberOfMapEntities() {
        return mapData.get("metadata").getInt("map_entity_number");
    }

    public ExitTile[] getExitPoints() {
        JsonValue exit_points = mapData.get("metadata").get("exit_points");
        ExitTile[] exitTiles = new ExitTile[exit_points.size];
        //System.out.println("exit points: "+mapData.get("metadata").get("exit_points").size);
        for (int j = 0; j < exit_points.size; j++) {
            JsonValue exitPoint = exit_points.get(j);
            String targetMap = exitPoint.getString("target_map");
            int tileX = exitPoint.getInt("x");
            int tileY = exitPoint.getInt("y");
            int targetX = exitPoint.getInt("target_x");
            int targetY = exitPoint.getInt("target_y");
            exitTiles[j] = new ExitTile(tileX, tileY, targetMap, targetX, targetY);
        }
        return exitTiles;
    }

}
