package com.mathochist.mazegame.UI.Speech;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class SpeechBubbleManager {

    private SpriteBatch hudBatch;
    private OrthographicCamera hudCamera;
    private BitmapFont main_font;

    private ArrayList<SpeechBubble> bubbles;

    public SpeechBubbleManager(SpriteBatch hudBatch, OrthographicCamera hudCamera, BitmapFont main_font) {
        this.main_font = main_font;
        this.hudCamera = hudCamera;
        this.hudBatch = hudBatch;

        this.bubbles = new ArrayList<>();
    }

    public void render() {
        ArrayList<SpeechBubble> bubblesToRemove = new ArrayList<>();
        for (SpeechBubble bubble : bubbles) {
            bubble.render();
            if (bubble.isExpired()) {
                bubble.dispose();
                bubblesToRemove.add(bubble);
            }
        }
        bubbles.removeAll(bubblesToRemove);
    }

    public void createSpeechBubble(SpeechBubble bubble) {
        bubble.setStartTime(System.currentTimeMillis());
        bubbles.add(bubble);
    }

    public void createSpeechBubble(SpeechType type, String message, int durationMs) {
        SpeechBubble bubble = new SpeechBubble(type, message, durationMs, hudBatch, hudCamera, main_font);
        bubble.setStartTime(System.currentTimeMillis());
        bubbles.add(bubble);
    }

    public void createSpeechBubble(SpeechType type, String message, int durationMs, BitmapFont font) {
        SpeechBubble bubble = new SpeechBubble(type, message, durationMs, hudBatch, hudCamera, font);
        bubble.setStartTime(System.currentTimeMillis());
        bubbles.add(bubble);
    }

    public void dispose() {
        for (SpeechBubble bubble : bubbles) {
            bubble.dispose();
        }
        bubbles.clear();
    }

    public void removeBubblesOfType(SpeechType type) {
        ArrayList<SpeechBubble> bubblesToRemove = new ArrayList<>();
        for (SpeechBubble bubble : bubbles) {
            if (bubble.getType() == type) {
                bubble.dispose();
                bubblesToRemove.add(bubble);
            }
        }
        bubbles.removeAll(bubblesToRemove);
    }

    public SpriteBatch getHudBatch() {
        return hudBatch;
    }

    public OrthographicCamera getHudCamera() {
        return hudCamera;
    }

    public BitmapFont getMainFont() {
        return main_font;
    }

    public void clear() {
        for (SpeechBubble bubble : bubbles) {
            bubble.dispose();
        }
        bubbles.clear();
    }

}
