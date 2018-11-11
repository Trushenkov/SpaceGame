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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Класс экрана главного меню игры.
 *
 * @author Трушенков Д. 15ИТ18
 */
public class MainMenuScreen implements Screen {

    //constants
    private static final String BACKGROUND = "background2.jpg";
    private static final String PACK_FILE = "ui/button.pack";
    private static final String FONT_WHITE = "font/white.fnt";
    private static final String FONT_BLACK = "font/black.fnt";
    private static final String BUTTON_UP = "button.up";
    private static final String BUTTON_DOWN = "button.down";
    private static final String EXIT = "EXIT";
    private static final String PLAY = "PLAY";
    private static final String SETTINGS = "SETTINGS";

    //fields
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonExit, buttonSettings;
    private BitmapFont white, black;
    private Label heading;
    private Texture background;

    MainMenuScreen(final SpaceGame spaceGame){
        background = new Texture(BACKGROUND);
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(PACK_FILE);
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

        buttonExit = new TextButton(EXIT, textButtonStyle);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(20);

        buttonPlay = new TextButton(PLAY, textButtonStyle);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(spaceGame));
            }
        });
        buttonPlay.pad(20);

        buttonSettings = new TextButton(SETTINGS, textButtonStyle);
        buttonSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen(spaceGame));
            }
        });
        buttonPlay.pad(20);

        //creating heading
        Label.LabelStyle headingStyle = new Label.LabelStyle(white,Color.WHITE);
        heading = new Label(SpaceGame.TITLE, headingStyle);
        heading.setFontScale((float) 1.5);


        //putting stuff together
        table.add(heading).spaceBottom(70).row();
        table.add(buttonPlay).spaceBottom(20).row();
        table.add(buttonSettings).spaceBottom(20).row();
        table.add(buttonExit).spaceBottom(20);
        table.setFillParent(true);
        table.background(new TextureRegionDrawable(new TextureRegion(background)));
        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
        table.setClip(true);
        table.setSize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public   void resume() {

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
