package com.mathochist.mazegame.UI.Timer;

/**
 * A class representing a timer penalty in the game.
 * This class is intended to handle penalties that affect the game's timer.
 * Is package-private to restrict access to TimerManager only.
 */
record TimerPenalty(double penaltySeconds, double timeAdded) {

    public static final double DISPLAY_DURATION = 3.0; // seconds

}
