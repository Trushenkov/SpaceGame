package ru.tds.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Класс экрана "Игра закончена".
 *
 * @author Трушенков Д. 15ИТ18
 */
public class GameOverScreen implements Screen {

    private final SpaceGame spaceGame;
    private OrthographicCamera camera;
    private Texture gameOverImage;
    private Texture background;
    private Texture tryAgainImage;

    GameOverScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);

        background = new Texture("space.png");
        gameOverImage = new Texture("GAMEOVER.png");
        tryAgainImage = new Texture("TRYAGAIN.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        spaceGame.spriteBatch.setProjectionMatrix(camera.combined);
        spaceGame.spriteBatch.begin();
        spaceGame.spriteBatch.draw(background, 0, 0);
        spaceGame.spriteBatch.draw(gameOverImage, 480 / 2 - gameOverImage.getWidth() / 2, 800 / 2);
        spaceGame.spriteBatch.draw(tryAgainImage, 480 / 2 - tryAgainImage.getWidth() / 2, 800 / 2 - gameOverImage.getHeight() / 2);
        spaceGame.spriteBatch.end();

        if (Gdx.input.justTouched()) {
            spaceGame.setScreen(new GameScreen(spaceGame));
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
        spaceGame.dispose();
        gameOverImage.dispose();
        background.dispose();
        tryAgainImage.dispose();
    }
}
