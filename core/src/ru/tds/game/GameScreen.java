package ru.tds.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private static final int SCREEN_HEIGHT = 800;
    private static final String BACKGROUND = "background2.jpg";
    private static final String ASTEROID = "meteorit.png";
    private static final String SHIP = "space_shuttle.png";
    private static final int POSITION_SHIP_Y = 50;
    private static final int WIDTH_SHIP = 64;
    private static final int HEIGHT_SHIP = 64;
    private static final int SPEED_ASTEROID = 300;
    private static final int ASTEROID_WIDTH = 64;
    private static final int ASTEROID_HEIGHT = 64;

    //fields
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
        background = new Texture(BACKGROUND);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        meteoritImage = new Texture(ASTEROID);
        planeImage = new Texture(SHIP);

        ship = new Rectangle();
        ship.x = SCREEN_WIDTH / 2 - WIDTH_SHIP / 2;
        ship.y = POSITION_SHIP_Y;
        ship.width = WIDTH_SHIP;
        ship.height = HEIGHT_SHIP;

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

            ship.x = touchPosition.x - WIDTH_SHIP / 2;
            ship.y = touchPosition.y;
        }

        //для того, чтобы не вылетать за пределы экрана
        if (ship.x < 0) ship.x = 0;
        if (ship.x > SCREEN_WIDTH - WIDTH_SHIP) ship.x = SCREEN_WIDTH - WIDTH_SHIP;
        if (ship.y > SCREEN_HEIGHT - HEIGHT_SHIP) ship.y = SCREEN_HEIGHT - HEIGHT_SHIP;
        if (ship.y < 0) ship.y = 0;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnAsteroids();

        Iterator<Rectangle> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Rectangle rectangle = iterator.next();
            rectangle.y -= SPEED_ASTEROID * Gdx.graphics.getDeltaTime();
            if (rectangle.y + ASTEROID_HEIGHT < 0) iterator.remove();
            if (rectangle.overlaps(ship)) {
                spaceGame.setScreen(new GameOverScreen(spaceGame));
                dispose();
            }
            if (time > 10){
                rectangle.y -= 50 * Gdx.graphics.getDeltaTime();
            }
            if (time > 20){
                rectangle.y -= 100 * Gdx.graphics.getDeltaTime();
            }
            if (time > 30){
                rectangle.y -= 100 * Gdx.graphics.getDeltaTime();
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
        asteroid.x = MathUtils.random(0, SCREEN_WIDTH - ASTEROID_WIDTH);
        asteroid.y = SCREEN_HEIGHT;
        asteroid.width = ASTEROID_WIDTH;
        asteroid.height = ASTEROID_HEIGHT;
        asteroids.add(asteroid);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        spaceGame.dispose();
        background.dispose();
        meteoritImage.dispose();
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
