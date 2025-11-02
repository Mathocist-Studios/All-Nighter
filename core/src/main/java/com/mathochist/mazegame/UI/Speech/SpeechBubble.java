package com.mathochist.mazegame.UI.Speech;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SpeechBubble {

    private SpeechType type;
    private String message;
    private int durationMs;
    private SpriteBatch hudBatch;
    private OrthographicCamera camera;
    private BitmapFont font;

    private ShapeRenderer shapeRenderer;
    private double startTime;

    public SpeechBubble(SpeechType type, String message, int durationMs, SpriteBatch hudBatch, OrthographicCamera camera, BitmapFont font) {
        this.type = type;
        this.message = message;
        this.durationMs = durationMs;
        this.hudBatch = hudBatch;
        this.camera = camera;
        this.font = font;

        this.shapeRenderer = new ShapeRenderer();
    }

    private void render_npc_bubble() {
        GlyphLayout layout = new GlyphLayout(font, message);

        // draw speech bubble in center top
        float padding = 40.0f;
        float bubble_width = layout.width + padding * 2;
        float bubble_height = layout.height + padding * 2;
        float bubble_x = (camera.viewportWidth - bubble_width) / 2.0f;
        float bubble_y = camera.viewportHeight - bubble_height - 40.0f;
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(bubble_x, bubble_y, bubble_width, bubble_height);
        shapeRenderer.end();

        hudBatch.begin();
        font.draw(hudBatch, message, bubble_x + padding, bubble_y + bubble_height - padding);
        hudBatch.end();

        // draw carriage return arrow below speech bubble using shapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        float arrow_x = camera.viewportWidth / 2.0f;
        float arrow_y = (float) (bubble_y + 5.0f * Math.sin(System.currentTimeMillis() / 200.0));
        shapeRenderer.triangle(arrow_x - 15, arrow_y + 15, arrow_x + 15, arrow_y + 15, arrow_x, arrow_y);
        shapeRenderer.end();
    }

    private void pass() {}

    // TODO: Add more speech bubble types and their render methods
    public void render() {
        switch (type) {
            case NPC_SPEECH -> render_npc_bubble();
            default -> pass();
        }
    }

    public SpeechType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public SpriteBatch getHudBatch() {
        return hudBatch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - startTime) >= durationMs;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

}
