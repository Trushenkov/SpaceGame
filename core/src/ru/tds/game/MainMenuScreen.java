package ru.tds.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Класс экрана главного меню игры.
 *
 * @author Трушенков Д. 15ИТ18
 */
public class MainMenuScreen implements Screen {

    private static final int WIDTH = 480;
    private static final int HEIGHT = 800;

    private Texture backgroundForMenu;
    private final SpaceGame spaceGame;
    private OrthographicCamera camera;
    private Texture playButton;

    MainMenuScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;

        backgroundForMenu = new Texture("background_game_screen.png");
        playButton = new Texture("PLAY.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        spaceGame.spriteBatch.setProjectionMatrix(camera.combined);
        spaceGame.spriteBatch.begin();
        spaceGame.spriteBatch.draw(backgroundForMenu, 0, 0);
        spaceGame.spriteBatch.draw(playButton, WIDTH / 2 - playButton.getWidth() / 2, HEIGHT / 2 - playButton.getHeight() / 2);
        spaceGame.spriteBatch.end();

        if (Gdx.input.isTouched()) {
            spaceGame.setScreen(new GameScreen(spaceGame));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
