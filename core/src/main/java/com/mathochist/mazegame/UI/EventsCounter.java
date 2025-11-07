package com.mathochist.mazegame.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EventsCounter {

    private int positiveEvents;
    private int negativeEvents;
    private int hiddenEvents;

    private boolean librarySprintEventTriggered;
    private boolean foundKeycardEventTriggered;
    private boolean vendingMachineEventTriggered;

    public EventsCounter() {
        this.positiveEvents = 0;
        this.negativeEvents = 0;
        this.hiddenEvents = 0;

        this.librarySprintEventTriggered = false;
    }

    public void librarySprintEvent() {
        if (librarySprintEventTriggered) {
            return;
        }
        this.hiddenEvents += 1;
        this.librarySprintEventTriggered = true;
    }

    public void foundKeycardEvent() {
        if (foundKeycardEventTriggered) {
            return;
        }
        this.negativeEvents += 1;
        this.foundKeycardEventTriggered = true;
    }

    public void vendingMachineEvent() {
        if (vendingMachineEventTriggered) {
            return;
        }
        this.positiveEvents += 1;
        this.vendingMachineEventTriggered = true;
    }

    public void renderEventsCounterToHUD(SpriteBatch hudBatch, OrthographicCamera camera, BitmapFont font) {
        font.draw(hudBatch, "Positive Events: " + positiveEvents, 2, camera.viewportHeight - 15);
        font.draw(hudBatch, "Negative Events: " + negativeEvents, 2, camera.viewportHeight - 30);
        font.draw(hudBatch, "Hidden Events: " + hiddenEvents, 2, camera.viewportHeight - 45);
    }

}
