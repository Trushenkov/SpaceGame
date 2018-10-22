package ru.tds.game;

import com.badlogic.gdx.Gdx;
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

    //constants
    private static final int SCREEN_WIDTH = 480;
    private static final int SHIP_WIDTH = 64;
    private static final int SCREEN_HEIGHT = 800;

    private SpaceGame spaceGame;
    private OrthographicCamera camera;
    private Texture meteoritImage;
    private Texture planeImage;
    private Rectangle ship;
    private Vector3 touchPosition;
    private Array<Rectangle> asteroids;
    private long lastDropTime;
    private Texture background;
    private int time;
    private float currentTime = 0;

    GameScreen(SpaceGame spaceGame){

        this.spaceGame = spaceGame;
        background = new Texture("background2.jpg");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        meteoritImage = new Texture("meteorit.png");
        planeImage = new Texture("space_shuttle.png");

        ship = new Rectangle();
        ship.x = SCREEN_WIDTH / 2 - SHIP_WIDTH / 2;
        ship.y = 50;
        ship.width = 64;
        ship.height = 64;

        touchPosition = new Vector3();

        asteroids = new Array<Rectangle>();
        spawnAsteroids();
    }

    @Override
    public void render(float delta) {
        camera.update();

        spaceGame.spriteBatch.setProjectionMatrix(camera.combined);
        spaceGame.spriteBatch.begin();
        spaceGame.spriteBatch.draw(background, 0, 0);
        spaceGame.font.draw(spaceGame.spriteBatch, "Time: " + time + " sec.", 10, 20);
        spaceGame.spriteBatch.draw(planeImage, ship.x, ship.y);
        for (Rectangle met : asteroids) {
            spaceGame.spriteBatch.draw(meteoritImage, met.x, met.y);
        }
        spaceGame.spriteBatch.end();

        //управление для Android
        if (Gdx.input.isTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);

            ship.x = touchPosition.x - 64 / 2;
            ship.y = touchPosition.y;
        }

        //для того, чтобы не вылетать за пределы экрана
        if (ship.x < 0) ship.x = 0;
        if (ship.x > 480 - 64) ship.x = 480 - 64;
        if (ship.y > 800-64) ship.y = 800 - 64;
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
            if (time > 15){
                rectangle.y -= 50 * Gdx.graphics.getDeltaTime();
            }
            if (time > 25){
                rectangle.y -= 50 * Gdx.graphics.getDeltaTime();
            }
        }

        incrementTimeVariable();
    }

    /**
     * Метод для икрементирования переменной счетчика времени каждую секунду
     */
    private void incrementTimeVariable(){
        currentTime += Gdx.graphics.getRawDeltaTime();
        float periodOfTime = 1;
        if (currentTime > periodOfTime){
            currentTime -= periodOfTime;
            time++;
            spaceGame.setTimeOfPlayerLive(time);
        }
    }

    /**
     * Метод для размещения метеоритов
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
