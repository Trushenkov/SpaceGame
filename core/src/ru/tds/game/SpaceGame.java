package ru.tds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceGame extends Game {

    public static final String TITLE = "Space Game v1.0";

    SpriteBatch spriteBatch;
    BitmapFont font;
    int timeOfPlayerLive = 0; //time from GameScreen

    void setTimeOfPlayerLive(int timeOfPlayerLive) {
        this.timeOfPlayerLive = timeOfPlayerLive;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
        font.dispose();
    }
}
