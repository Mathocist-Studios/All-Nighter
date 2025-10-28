package com.mathochist.mazegame.Entities.NPCs;

import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.World.GameWorld;

public interface INPC {

    /**
     * Renders the NPC in the game world.
     *
     * @param world The game world context.
     */
    void render(GameWorld world);

    /**
     * Renders a prompt for interaction with the NPC.
     *
     * @param world The game world context.
     */
    void renderPrompt(GameWorld world);

    /**
     * Renders speech text from the NPC.
     *
     * @param text  The speech text to render.
     * @param world The game world context.
     */
    void renderSpeech(String text, GameWorld world);

    /**
     * Handles interaction with the player.
     *
     * @param p     The player interacting with the NPC.
     * @param world The game world context.
     * @return true if the interaction was successful, false otherwise.
     */
    boolean onInteract(Player p, GameWorld world);

}
