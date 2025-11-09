package com.mathochist.mazegame.UI.Timer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TimerManager {

    public static double START_TIME = 0;
    public static final double TIME_LIMIT = 300; // 5 mins in seconds

    private final ArrayList<TimerPenalty> penalties;

    public TimerManager() {
        penalties = new ArrayList<>();
    }

    public void startTimer() {
        START_TIME = System.currentTimeMillis();
    }

    public double getTimeLeft() {
        double timeElapsed = (System.currentTimeMillis() - START_TIME) / 1000;
        return TIME_LIMIT - timeElapsed;
    }

    public boolean isTimeUp() {
        return getTimeLeft() <= 0;
    }

    public void addPenalty(double seconds) {
        START_TIME -= seconds * 1000; // Subtract penalty time from start time
        penalties.add(new TimerPenalty(seconds, System.currentTimeMillis()));
    }

    public void resetTimer() {
        START_TIME = 0;
        penalties.clear();
    }

    public void renderToHUD(BitmapFont font, SpriteBatch batch, OrthographicCamera camera) {
        double timeLeft = this.getTimeLeft();
        double minutesLeft = Math.floor(timeLeft / 60.0);
        double secondsLeft = Math.floor(timeLeft % 60.0);
        font.draw(batch, "Time: " + (int) minutesLeft + ":" + String.format("%02d", (int) secondsLeft), camera.viewportWidth - 120, camera.viewportHeight - 2);

        font.setColor(255, 0, 0, 1);

        // Render penalties
        float penaltyY = camera.viewportHeight - 30;
        long currentTime = System.currentTimeMillis();
        ArrayList<TimerPenalty> expiredPenalties = new ArrayList<>();
        for (TimerPenalty penalty : penalties) {
            double timeSinceAdded = (currentTime - penalty.timeAdded()) / 1000.0;
            if (timeSinceAdded <= TimerPenalty.DISPLAY_DURATION) {
                font.draw(batch, "-" + (int) penalty.penaltySeconds() + "s", camera.viewportWidth - 120, penaltyY);
                penaltyY -= 25; // Move up for next penalty
            } else {
                expiredPenalties.add(penalty);
            }
        }

        font.setColor(1, 1, 1, 1);

        // Remove expired penalties
        penalties.removeAll(expiredPenalties);

    }

}
