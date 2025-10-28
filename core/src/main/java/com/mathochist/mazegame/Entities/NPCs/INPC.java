package com.mathochist.mazegame.Entities.NPCs;

import com.mathochist.mazegame.World.GameWorld;

public interface INPC {

    void render(GameWorld world);

    void renderPrompt(GameWorld world);

    void renderSpeech(String text, GameWorld world);

    void onPromptActivate(GameWorld world);

}
