package ru.tds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Класс экрана "Настройки" игры.
 *
 * @author Трушенков Дмитрий 15ИТ18
 */
public class SettingsScreen implements Screen {

    //constants
    private static final String BACKGROUND = "background2.jpg";
    private static final String PACK_FILE = "ui/button.pack";
    private static final String FONT_WHITE = "font/white.fnt";
    private static final String FONT_BLACK = "font/black.fnt";
    private static final String BUTTON_UP = "button.up";
    private static final String BUTTON_DOWN = "button.down";
    private static final String MUSIC_ON = "MUSIC: ON";
    private static final String MUSIC_OFF = "MUSIC: OFF";
    private static final String GAME_SETTINGS = "Game settings";

    //fields
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonMenu, buttonMusic; // кнопка "Menu" и "Music: off/on"
    private BitmapFont white, black; // font
    private Label heading;  // Label "Settings"
    private Texture background; // background

    SettingsScreen(final SpaceGame spaceGame) {
        background = new Texture(BACKGROUND);
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(PACK_FILE);
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //creating fonts
        white = new BitmapFont(Gdx.files.internal(FONT_WHITE), false);
        black = new BitmapFont(Gdx.files.internal(FONT_BLACK), false);

        //creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable(BUTTON_UP);
        textButtonStyle.down = skin.getDrawable(BUTTON_DOWN);
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        //size of button
        textButtonStyle.up.setMinWidth(340);
        textButtonStyle.up.setMinHeight(70);
        textButtonStyle.down.setMinWidth(340);
        textButtonStyle.down.setMinHeight(70);

        buttonMenu = new TextButton("MAIN MENU", textButtonStyle);
        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(spaceGame));
            }
        });
        buttonMenu.pad(20);


        buttonMusic = new TextButton(MUSIC_ON, textButtonStyle);
        buttonMusic.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (spaceGame.music.isPlaying()) {
                    spaceGame.music.pause();
                    buttonMusic.setText(MUSIC_OFF);
                } else {
                    spaceGame.music.play();
                    buttonMusic.setText(MUSIC_ON);
                }
            }
        });

        buttonMusic.pad(20);

        heading = new Label(GAME_SETTINGS, new LabelStyle(white, Color.WHITE));
        heading.setFontScale((float) 1.5);

        //putting stuff together
        table.add(heading).spaceBottom(70).row();
        table.add(buttonMenu).spaceBottom(20).row();
        table.add(buttonMusic).spaceBottom(20);
        table.setFillParent(true);
        table.background(new TextureRegionDrawable(new TextureRegion(background)));
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setClip(true);
        table.setSize(width, height);
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
