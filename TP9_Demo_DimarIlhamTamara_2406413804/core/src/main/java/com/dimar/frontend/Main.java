package com.dimar.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    // TODO: Deklarasikan Attribute untuk Player (Receiver)
    private Player player;

    // TODO: Deklarasikan Attribute untuk ScoreSystem (Subject) dan ScoreDisplay
    //(Observer)
    private ScoreSystem scoreSystem;
    private ScoreDisplay scoreDisplay;

    // TODO: Deklarasikan Attribute Map untuk menyimpan Command input (Invoker)
    private Map<Integer, Command> inputCommands;

    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
        player = new Player(Gdx.graphics.getWidth() / 2f,Gdx.graphics.getHeight() / 2f);
        scoreSystem = new ScoreSystem();
        scoreDisplay = new ScoreDisplay();

        scoreSystem.registerObserver(scoreDisplay);
        inputCommands = new HashMap<>();

        inputCommands.put(Input.Keys.W, new MoveCommand(player, 0, 5));
        inputCommands.put(Input.Keys.A, new MoveCommand(player, -5, 0));
        inputCommands.put(Input.Keys.S, new MoveCommand(player, 0, -5));
        inputCommands.put(Input.Keys.D, new MoveCommand(player, 5, 0));
        System.out.println("Press W, A, S, D to move. Press SPACE to add score.");
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            inputCommands.get(Input.Keys.W).execute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            inputCommands.get(Input.Keys.A).execute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            inputCommands.get(Input.Keys.S).execute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            inputCommands.get(Input.Keys.D).execute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            scoreSystem.addScore(10);
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.circle(player.getPosition().x, player.getPosition().y, 20);

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
