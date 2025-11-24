package com.dimar.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dimar.frontend.GameManager;

public class MenuState implements GameState {
    private GameStateManager gsm;
    private Stage stage;
    private Skin skin;
    private TextField nameField;
    private TextButton startButton;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();
        buildUI();
    }

    private void createBasicSkin() {
        skin = new Skin();
        BitmapFont bitmapFont = new BitmapFont();
        skin.add("default", bitmapFont);
        Pixmap pixmapW = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        Pixmap pixmapDG = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        Pixmap pixmapG = new Pixmap(1,1, Pixmap.Format.RGBA8888);

        pixmapW.setColor(Color.WHITE);
        pixmapW.fill();
        skin.add("white", new Texture(pixmapW));

        pixmapG.setColor(Color.GRAY);
        pixmapG.fill();
        skin.add("gray", new Texture(pixmapG));

        pixmapDG.setColor(Color.DARK_GRAY);
        pixmapDG.fill();
        skin.add("dark_gray", new Texture(pixmapDG));

        pixmapDG.dispose();
        pixmapG.dispose();
        pixmapW.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLACK;
        skin.add("labelStyle", labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bitmapFont;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("dark_gray");
        textFieldStyle.cursor = skin.newDrawable("white");
        textFieldStyle.selection = skin.newDrawable("gray");
        skin.add("textFieldStyle", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.up = skin.newDrawable("gray");
        textButtonStyle.down = skin.newDrawable("white");
        textButtonStyle.over = skin.newDrawable("dark_gray");
        skin.add("textButtonStyle", textButtonStyle);
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label judul = new Label("NETLAB JOYRIDE", skin, "labelStyle");
        judul.setFontScale(2f);
        Label prompt = new Label("Enter Your Name:", skin, "labelStyle");
        nameField = new TextField("", skin, "textFieldStyle");

        TextButton textButton = new TextButton("START GAME", skin, "textButtonStyle");
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = nameField.getText();
                if (name.isEmpty()) {
                    name = "Guest";
                }

                GameManager.getInstance().registerPlayer(name);
                gsm.set(new PlayingState(gsm));
            }
        });

        table.add(judul).padBottom(20f);
        table.row();

        table.add(prompt).padBottom(10f);
        table.row();

        table.add(nameField).width(250f).height(40f).padBottom(20f);
        table.row();

        table.add(textButton).width(200f).height(50f);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
