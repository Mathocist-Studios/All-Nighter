package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

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

    public ExitTile[] getExitPoints() {
        ExitTile[] exitTiles = new ExitTile[mapData.get("metadata").get("exit_points").size];
        //System.out.println("exit points: "+mapData.get("metadata").get("exit_points").size);
        for (int j = 0; j < mapData.get("metadata").get("exit_points").size; j++) {
            exitTiles[j] = new ExitTile();
            exitTiles[j].x = mapData.get("metadata").get("exit_points").get(j).getInt("x");
            exitTiles[j].y = mapData.get("metadata").get("exit_points").get(j).getInt("y");
            exitTiles[j].targetX = mapData.get("metadata").get("exit_points").get(j).getInt("target_x");
            exitTiles[j].targetY = mapData.get("metadata").get("exit_points").get(j).getInt("target_y");
            exitTiles[j].targetMap = mapData.get("metadata").get("exit_points").get(j).getString("target_map");
        }
        return exitTiles;
    }

    public JsonValue getNPCs() {
        return mapData.get("metadata").get("npcs");
    }

    public JsonValue getNPC(String npcName) {
        JsonValue npcs = getNPCs();
        for (JsonValue npc : npcs) {
            if (npc.getString("name").equals(npcName)) {
                return npc;
            }
        }
        return null;
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

}
