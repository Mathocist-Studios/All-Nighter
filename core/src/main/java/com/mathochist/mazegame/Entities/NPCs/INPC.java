package com.mathochist.mazegame.Entities.NPCs;

import com.mathochist.mazegame.Entities.Player;
import com.mathochist.mazegame.Rendering.RenderBuffer;
import com.mathochist.mazegame.UI.Hud;
import com.mathochist.mazegame.World.GameWorld;

public interface INPC {

    /**
     * Renders the NPC in the game world.
     *
     * @param world The game world context.
     * @param renderBuffer The render buffer to draw the NPC onto.
     */
    void render(GameWorld world, RenderBuffer renderBuffer);

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
     * @param worldHud The HUD of the game world.
     * @return true if the interaction was successful, false otherwise.
     */
    boolean onInteract(Player p, GameWorld world, Hud worldHud);

    /**
     * Handles the end of interaction with the player.
     * <br>
     * <b>*Called every time the player attempts to interact but entity out of range*</b>
     *
     * @param p     The player ending interaction with the NPC.
     * @param world The game world context.
     */
    void onInteractEnd(Player p, GameWorld world); // recommended to have a boolean (buffer) to check if interaction was ongoing

}
