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
 * Класс экрана "Игра закончена".
 *
 * @author Трушенков Д. 15ИТ18
 */
public class GameOverScreen implements Screen {

    private SpaceGame spaceGame;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonTryAgain, buttonExit;
    private BitmapFont white, black;
    private Label heading;
    private Texture background;

//    private Texture backgroundImage;

    GameOverScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        background = new Texture("background2.jpg");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //creating fonts
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        //creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        //size of button
        textButtonStyle.up.setMinWidth(300);
        textButtonStyle.up.setMinHeight(40);
        textButtonStyle.down.setMinWidth(300);
        textButtonStyle.down.setMinHeight(40);


        //button "Try Again"
        buttonTryAgain = new TextButton("TRY AGAIN", textButtonStyle);
        buttonTryAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(spaceGame));
            }
        });
        buttonTryAgain.pad(20);

        //button "Main Menu"
        buttonExit = new TextButton("MENU", textButtonStyle);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(spaceGame));
            }
        });
        buttonExit.pad(20);

        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);

        heading = new Label("Your time: " + spaceGame.timeOfPlayerLive + " sec.", headingStyle);
        heading.setFontScale((float) 1.5);

        table.add(heading);
        table.row();
        table.getCell(heading).spaceBottom(50);
        table.add(buttonTryAgain);
        table.row();
        table.getCell(buttonTryAgain).spaceBottom(15);
        table.add(buttonExit);
        table.setFillParent(true);
        table.background(new TextureRegionDrawable(new TextureRegion(background)));
        stage.addActor(table);
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
