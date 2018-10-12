package ru.tds.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Класс игрового экрана игры.
 *
 * @author Трушенков Д. 15ИТ18
 */
public class GameScreen implements Screen {

    private final SpaceGame spaceGame;
    private OrthographicCamera camera;
    private Texture meteoritImage;
    private Texture planeImage;
    private Rectangle ship;
    private Vector3 touchPosition;
    private Array<Rectangle> asteroids;
    private long lastDropTime;
    private Texture background;
    private int distance;

    GameScreen(final SpaceGame spaceGame) {

        this.spaceGame = spaceGame;
        background = new Texture("space.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);

        meteoritImage = new Texture("meteorit.png");

        planeImage = new Texture("space_shuttle.png");

        ship = new Rectangle();
        ship.x = 480 / 2 - 64 / 2;
        ship.y = 50;
        ship.width = 64;
        ship.height = 64;

        touchPosition = new Vector3();

        asteroids = new Array<Rectangle>();
        spawnAsteroids();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        spaceGame.spriteBatch.setProjectionMatrix(camera.combined);
        spaceGame.spriteBatch.begin();
        spaceGame.spriteBatch.draw(background, 0, 0);
        spaceGame.font.draw(spaceGame.spriteBatch,"SCORE: " + distance, 10,20);
        spaceGame.spriteBatch.draw(planeImage, ship.x, ship.y);
        for (Rectangle asteroid : asteroids) {
            spaceGame.spriteBatch.draw(meteoritImage, asteroid.x, asteroid.y);
        }
        spaceGame.spriteBatch.end();

        //управления для Android
        if (Gdx.input.isTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            ship.x = touchPosition.x - 64 / 2;
            ship.y = touchPosition.y;
        }

        //управление для Desktop
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) ship.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) ship.y -= 200 * Gdx.graphics.getDeltaTime();

        //для того, чтобы не вылетать за пределы экрана
        if (ship.x < 0) ship.x = 0;
        if (ship.x > 480 - 64) ship.x = 480 - 64;
        if (ship.y > 800) ship.y = 800;
        if (ship.y < 0) ship.y = 0;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnAsteroids();

        Iterator<Rectangle> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Rectangle rectangle = iterator.next();
            rectangle.y -= 300 * Gdx.graphics.getDeltaTime();
            if (rectangle.y + 64 < 0) iterator.remove();
            if (rectangle.overlaps(ship)) {
                spaceGame.setScreen(new GameOverScreen(spaceGame));
                dispose();
            }
        }
        if (TimeUtils.nanoTime() - Gdx.graphics.getDeltaTime() > 2000000000) {
            distance++;
        }
    }

    /**
     * Метод для размещения астероидов
     */
    private void spawnAsteroids() {
        Rectangle asteroid = new Rectangle();
        asteroid.x = MathUtils.random(0, 480 - 64);
        asteroid.y = 800;
        asteroid.width = 64;
        asteroid.height = 64;
        asteroids.add(asteroid);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        planeImage.dispose();
        meteoritImage.dispose();
        touchPosition.setZero();
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


}
