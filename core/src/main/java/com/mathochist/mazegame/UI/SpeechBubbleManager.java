package com.mathochist.mazegame.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SpeechBubbleManager {

    private ShapeRenderer shapeRenderer;
    private SpriteBatch hudBatch;
    private OrthographicCamera hudCamera;

    private BitmapFont main_font;

    private String speech_text = "";
    private int speech_duration_ms = 0;
    private long speech_start_time = 0;

    public SpeechBubbleManager(SpriteBatch hudBatch, OrthographicCamera hudCamera, BitmapFont main_font) {
        this.shapeRenderer = new ShapeRenderer();
        this.hudBatch = hudBatch;
        this.hudCamera = hudCamera;
        this.main_font = main_font;
    }

    public void render() {
        // render speech bubble if active
        if (speech_text.isEmpty()) {
            return;
        }
        long current_time = System.currentTimeMillis();
        if (current_time - speech_start_time < speech_duration_ms) {
            render_speech_bubble(speech_text);
        } else {
            speech_text = "";
        }
    }

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

    public void createSpeechBubble(String text, int duration_ms) {
        speech_text = text;
        speech_duration_ms = duration_ms;
        speech_start_time = System.currentTimeMillis();

        render_speech_bubble(speech_text);
    }

}
