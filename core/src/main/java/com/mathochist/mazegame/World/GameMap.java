package com.mathochist.mazegame.World;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {

    private FileHandle mapFile;
    private JsonReader jsonReader;
    private JsonValue mapData;

    public GameMap(FileHandle mapFile) {

        this.mapFile = mapFile;
        this.jsonReader = new JsonReader();
        this.mapData = jsonReader.parse(this.mapFile);
        JsonValue matrixData = this.mapData.get("map_matrix");

        Json json = new Json();

        int[][] mapMatrix = json.readValue(int[][].class,  matrixData);
        System.out.println(mapMatrix.length);

    }

}
