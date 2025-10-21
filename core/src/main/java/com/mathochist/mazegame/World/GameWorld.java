package com.mathochist.mazegame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameWorld {

    private GameMap currentMap;
    private TextureAtlas textureAtlas;
    private Texture[] textures;

    private SpriteBatch screenBatch;

    private Tile[][] mapArray;

    private BitmapFont font = new BitmapFont();
    private GlyphLayout layout = new GlyphLayout();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

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
                     Gdx.graphics.getHeight() - (j * currentMap.getTileHeight() + 48)
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
        for (int i = 0; i < currentMap.getMapWidth(); i++) {
            for (int j = 0; j < currentMap.getMapHeight(); j++) {
                mapArray[i][j].getSprite().draw(screenBatch);


                // Draw coordinates
                String coord = "(" + i + "," + j + ")";
                float x = i * currentMap.getTileWidth();
                float y = Gdx.graphics.getHeight() - (j * currentMap.getTileHeight() + 48);

                layout.setText(font, coord);
                float textX = x + (currentMap.getTileWidth() - layout.width) / 2f;
                float textY = y + currentMap.getTileHeight() - 4; // 4px padding from top

                font.draw(screenBatch, coord, textX, textY);
            }
        }
        screenBatch.end();

        shapeRenderer.setProjectionMatrix(screenBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.3f)); // semi-transparent green
        for (int i = 0; i < currentMap.getMapWidth(); i++) {
            for (int j = 0; j < currentMap.getMapHeight(); j++) {
                if (mapArray[i][j].isCollidable()) {
                    float x = i * currentMap.getTileWidth();
                    float y = Gdx.graphics.getHeight() - (j * currentMap.getTileHeight());
                    shapeRenderer.rect(x, y, currentMap.getTileWidth(), currentMap.getTileHeight());
                }
            }
        }
        shapeRenderer.end();
    }

    public boolean[] getCollisionLayer(float playerXPixels, float playerYPixels) {
        int[] tileCoords = pixelCoordsToTileIndex(playerXPixels, playerYPixels);
        Tile tile_above = mapArray[tileCoords[0]][tileCoords[1] + 1];
        Tile tile_below = mapArray[tileCoords[0]][tileCoords[1] - 1];
        Tile tile_left = mapArray[tileCoords[0] - 1][tileCoords[1]];
        Tile tile_right = mapArray[tileCoords[0] + 1][tileCoords[1]];
        return new boolean[]{
            tile_above.isCollidable(),
            tile_below.isCollidable(),
            tile_left.isCollidable(),
            tile_right.isCollidable()
        };
    }

    public void dispose() {
        textureAtlas.dispose();
    }

    public int[] pixelCoordsToTileIndex(float pixelCoordX, float pixelCoordY) {
        int tileX = (int) Math.floor(pixelCoordX / currentMap.getTileWidth());
        int tileY = (int) Math.floor(pixelCoordY / currentMap.getTileHeight());
        return new int[]{tileX, tileY};
    }

    public int[] getPlayerTilePosition(float playerXPixels, float playerYPixels) {
        return pixelCoordsToTileIndex(playerXPixels, playerYPixels);
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

}
