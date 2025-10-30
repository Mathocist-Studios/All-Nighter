# World File Specifications

File written as of 28 Oct 2025 - updated commit ea8ce6b

<hr />

## Overview
World files define the layout, entities, and properties of a game world.
All files are in JSON format.
All world files must have every field present even if not used in the world.
Some fields may have keywords, defaults, or restrictions layed out below.

You may refer to example world files in the /assets/maps/ directory.

## Markdown Conventions
- **Bold**: Indicates a field name in the JSON file.
- *Italics*: Indicates a keyword, type, or special value.
- `Code`: Indicates code snippets or JSON examples.
- <span style="color:grey;">Gray Text</span>: Indicates comments or explanations.

## Meta Data:
World files contain a header of metadata that defines everything from the author to the entity placement rules.
All metadata fields are stored in the metadata object in the json file.
Below is a breakdown of all the metadata fields found in world files:
All fields listed are required.
All files should be in the `metadata` object in the json file.

- **name**: The name of the world.
  - Type: string
  - Example: "Mystic Forest"
  - <span style="color:grey;">Note: This is not essential for world generation</span>
- **author**: The creator of the world.
  - Type: string
  - Example: "John Doe"
  - <span style="color:grey;">Note: This is not essential for world generation</span>
- **version**: The version of the world file format.
  - Type: string
  - Example: "1.0.0"
  - <span style="color:grey;">Note: This is not essential for world generation</span>
- **tilesets**: A json object defining the dimensions of the tile grid
  - Type: object
  - Fields:
    - **tile_height**: The height of each tile in pixels.
      - Type: integer
      - Example: 32
    - **tile_width**: The width of each tile in pixels.
      - Type: integer
      - Example: 32
<span id="map_width"></span>
- **map_width**: The width of the map in tiles.
  - Type: integer
  - Example: 100
  - <span style="color:grey;">Note: This defines how many tiles fit horizontally in the world. It is vital these align with the <a href="#map_matrix">map matrix below</a></span>
<span id="map_height"></span>
- **map_height**: The height of the map in tiles.
  - Type: integer
  - Example: 100
  - <span style="color:grey;">Note: This defines how many tiles fit vertically in the world. It is vital these align with the <a href="#map_matrix">map matrix below</a></span>
- **spawn_point**: The starting position of the player in tile coordinates.
  - Type: object
  - Fields:
    - **x**: The x-coordinate of the spawn point in tiles.
      - Type: integer
      - Example: 10
    - **y**: The y-coordinate of the spawn point in tiles.
      - Type: integer
      - Example: 15
  - <span style="color:grey;">Note: when swapping between worlds the game will place the player on the <a href="#exit_points">exit tile target location</a> not the spawn point</span>
- **background_music**: The background music track for the world.
  - Type: string
  - Example: "mystic_forest_theme.mp3"
  - Keywords:
    - *none*: No background music.
    - *continue*: Continue playing the previous world's background music.
  - <span style="color:grey;">Note: This value should be an internal file path</span>
<span id="texture_atlas"></span>
- **texture_atlas**: The texture atlas used for rendering the world. Holds all tile and entity textures.
  - Type: string
  - Example: "forest_atlas.atlas"
  - <span style="color:grey;">Note: This value should be an internal file path</span>
<span id="exit_points"></span>
- **exit_points**: An array of exit points in the world that lead to other worlds.
  - Type: array of objects
  - Fields:
    - **x**: The x-coordinate of the exit point in tiles.
      - Type: integer
      - Example: 95
    - **y**: The y-coordinate of the exit point in tiles.
      - Type: integer
      - Example: 50
    - **target_map**: The object class for the target world to load when the player uses this exit point.
      - Type: string
      - Example: "com.mathochist.mazegame.worlds.ForestWorld"
    - **target_x**: The x-coordinate in tiles where the player will spawn in the target world.
      - Type: integer
      - Example: 5
    - **target_y**: The y-coordinate in tiles where the player will spawn in the target world.
      - Type: integer
      - Example: 10
  - <span style="color:grey;">Note: Exit points are used to transition between different worlds in the game</span>
- **map_entity_number**: The total number of entities present in the world.
  - Type: integer
  - Example: 50
  - <span style="color:grey;">Note: This value should match the number of entities defined in the <a href="#entities">entities array</a> below</span>
<span id="entities"></span>
- **map_objects**: An object defining all entities present in the world.
  - Type: object
  - Key: The object class of the entity. (e.g., "com.mathochist.mazegame.entities.TreeEntity")
  - Fields:
    - **width**: The width of the entity in tiles.
      - Type: integer
      - Example: 2
    - **height**: The height of the entity in tiles.
      - Type: integer
      - Example: 3
    - **collidable**: Whether the entity is collidable.
      - Type: boolean
      - Example: true
    - **positions**: A 2D ```int[][]``` array defining the positions of the entity in tile coordinates
      - Example:
      ```json
        "positions": [
          [10, 15], // x=10, y=15
          [20, 25],
          [30, 35]
        ]
        ```

## Map Shader
World files can define a shader to be applied to the entire map.
This shader affects the visual appearance of the map, such as lighting and color effects.
All shader fields are stored in the `shader` object in the json file.
- **name**: The name of the shader to be applied to the map.
  - Type: string
  - Example: "night_shader"
  - Keywords:
    - *default*: No shader applied to the map. You may leave the other shader fields blank.
  - <span style="color:grey;">Note: This is not technically essential yet but a shader manager is planned where shaders can be referenced by name</span>
- **vertex**: The file path to the vertex shader code.
  - Type: string
  - Example: "shaders/night_vertex.glsl"
  - <span style="color:grey;">Note: This value should be an internal file path</span>
- **fragment**: The file path to the fragment shader code.
  - Type: string
  - Example: "shaders/night_fragment.glsl"
  - <span style="color:grey;">Note: This value should be an internal file path</span>

<span id="tiles"></span>
## Tiles
To define the map matrix bit map, we define a list of tiles where the name corresponds to the name of the region in the <a href="#texture_atlas">texture atlas</a>, 
and the index corresponds to the id in the <a href="#map_matrix">map matrix below</a>.
- **tiles**: An array of tile definitions used in the map matrix.
  - Type: array of strings
  - Example:
  ```json
    "tiles": [
      "grass_tile",   // index 0
      "water_tile",   // index 1
      "sand_tile"     // index 2
    ]
    ```
  - <span style="color:grey;">Note: The order of tiles in this array determines their corresponding index in the <a href="#map_matrix">map matrix below</a>.</span>
  - <span style="color:grey;">Note: The names must match the region names in the <a href="#texture_atlas">texture atlas</a></span>

## Collision Tiles
To define which tiles are collidable, we define a list of integers where the value corresponds to the index in the <a href="#tiles">tiles array above</a>.
- **collision_tiles**: An array of integers defining which tiles are collidable.
  - Type: array of integers
  - Example:
  ```json
    "collision_tiles": [
      1, // water_tile is collidable
      2  // sand_tile is collidable
    ]
    ```
  - <span style="color:grey;">Note: The values in this array must correspond to valid indices in the <a href="#tiles">tiles array above</a></span>

<span id="map_matrix"></span>
## Map Matrix
The map matrix defines the layout of the world using a 2D array of integers.
Each integer corresponds to an index in the <a href="#tiles">tiles array above</a>.
- **map_matrix**: A 2D array defining the layout of the world using tile indices.
  - Type: 2D array of integers
  - Example:
  ```json
    "map_matrix": [
      [0, 0, 1, 0, 2], // Row 0
      [0, 1, 1, 2, 2], // Row 1
      [0, 0, 0, 0, 0], // Row 2
      [2, 2, 1, 0, 0], // Row 3
      [1, 0, 0, 0, 0]  // Row 4
    ]
    ```
  - <span style="color:grey;">Note: Each integer in the matrix must correspond to a valid index in the <a href="#tiles">tiles array above</a></span>
  - <span style="color:grey;">Note: The dimensions of the matrix must match the <a href="#map_width">map_width</a> and <a href="#map_height">map_height</a> defined in the metadata</span>
  - <span style="color:grey;">Note: The first array represents the top row of the map, and each subsequent array represents the next row down</span>

<hr />

([Back to Top](#))
