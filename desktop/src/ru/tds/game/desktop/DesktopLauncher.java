package ru.tds.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.tds.game.SpaceGame;

/**
 * Класс для запуска игры в десктопном режиме
 *
 * @author Трушенков Д. 15ИТ18
 */
public class DesktopLauncher {

    private static final int WIDTH = 480;
    private static final int HEIGHT = 800;
    private static final String TITLE = "Space Game";

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = WIDTH;
        config.height = HEIGHT;
        config.title = TITLE;
        new LwjglApplication(new SpaceGame(), config);
    }
}
