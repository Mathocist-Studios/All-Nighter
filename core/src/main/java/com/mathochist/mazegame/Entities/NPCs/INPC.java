package com.mathochist.mazegame.Entities.NPCs;

public interface INPC {

    void render();

    void renderPrompt();

    void renderSpeech(String text);

    void onPromptActivate();

}
