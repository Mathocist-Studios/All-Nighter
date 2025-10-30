package com.mathochist.mazegame.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mathochist.mazegame.Main;

public class Hud {

    private OrthographicCamera hudCamera;
    private SpriteBatch hudBatch;
    private BitmapFont small_font;
    private BitmapFont main_font;
    private ShapeRenderer shapeRenderer;

    private String speech_text = "";
    private int speech_duration_ms = 0;
    private long speech_start_time = 0;

    public Hud(Main game) {

        hudBatch = new SpriteBatch();
        hudCamera = new OrthographicCamera(game.WIDTH, game.HEIGHT);
        hudCamera.position.set(game.WIDTH / 2.0f, game.HEIGHT / 2.0f, 1.0f);

        small_font = new BitmapFont(Gdx.files.internal("fonts/game_font.fnt"), Gdx.files.internal("fonts/game_font.png"), false);
        small_font.getData().setScale(0.5f);

        main_font = new BitmapFont(Gdx.files.internal("fonts/game_font.fnt"), Gdx.files.internal("fonts/game_font.png"), false);
        main_font.getData().setScale(1.0f);

        shapeRenderer = new ShapeRenderer();

    }

    public SpriteBatch getHudBatch() {
        return hudBatch;
    }

    public OrthographicCamera getHudCamera() {
        return hudCamera;
    }

    public void dispose() {
        hudBatch.dispose();
    }

    public void render(float delta) {
        hudCamera.update();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        hudBatch.begin();

        // draw fps in top left corner
        small_font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 2, hudCamera.viewportHeight - 2);

        hudBatch.end();

        // render speech bubble if active
        if (!speech_text.isEmpty()) {
            long current_time = System.currentTimeMillis();
            if (current_time - speech_start_time < speech_duration_ms) {
                render_speech_bubble(speech_text);
            } else {
                speech_text = "";
            }
        }
    }

    // TODO: refactor to use a SpeechBubble class
    private void render_speech_bubble(String text) {
        GlyphLayout layout = new GlyphLayout(main_font, text);

        // draw speech bubble in center top
        float padding = 40.0f;
        float bubble_width = layout.width + padding * 2;
        float bubble_height = layout.height + padding * 2;
        float bubble_x = (hudCamera.viewportWidth - bubble_width) / 2.0f;
        float bubble_y = hudCamera.viewportHeight - bubble_height - 40.0f;
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(bubble_x, bubble_y, bubble_width, bubble_height);
        shapeRenderer.end();

        hudBatch.begin();
        main_font.draw(hudBatch, text, bubble_x + padding, bubble_y + bubble_height - padding);
        hudBatch.end();

        // draw carriage return arrow below speech bubble using shapeRenderer
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        float arrow_x = hudCamera.viewportWidth / 2.0f;
        float arrow_y = (float) (bubble_y + 5.0f * Math.sin(System.currentTimeMillis() / 200.0));
        shapeRenderer.triangle(arrow_x - 15, arrow_y + 15, arrow_x + 15, arrow_y + 15, arrow_x, arrow_y);
        shapeRenderer.end();
    }

    public void render_speech_text(String text, int duration_ms) {

        speech_text = text;
        speech_duration_ms = duration_ms;
        speech_start_time = System.currentTimeMillis();

        render_speech_bubble(speech_text);

    }

}
