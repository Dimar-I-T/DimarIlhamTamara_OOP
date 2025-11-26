package com.dimar.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.*;
import com.dimar.frontend.commands.Command;
import com.dimar.frontend.commands.JetpackCommand;
import com.dimar.frontend.commands.RestartCommand;
import com.dimar.frontend.factories.CoinFactory;
import com.dimar.frontend.factories.ObstacleFactory;
import com.dimar.frontend.observers.ScoreUIObserver;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.strategies.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PlayingState implements GameState{
    private final GameStateManager gsm;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Ground ground;
    private GameManager gameManager;

    private ObstacleFactory obstacleFactory;
    private float obstacleSpawnTimer;
    private float lastObstacleSpawnX = 0f;
    static final float OBSTACLE_SPAWN_INTERVAL = 2.5f;
    static final int OBSTACLE_DENSITY = 1;
    static final float SPAWN_AHEAD_DISTANCE = 300f;
    static final float MIN_OBSTACLE_GAP = 200f;
    static final float OBSTACLE_CLUSTER_SPACING = 250f;
    private CoinFactory coinFactory;
    private float coinSpawnTimer = 0f;
    private Random random = new Random();

    float currentScore;

    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;
    private int lastLoggedScore = -1;
    float cameraFocus;

    private Background background;
    private Command jetpackCommand, restartCommand;
    private ScoreUIObserver scoreUIObserver;
    private DifficultyStrategy difficultyStrategy;

    private final List<CoinPattern> coinPatterns;

    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        shapeRenderer = new ShapeRenderer();
        gameManager = GameManager.getInstance();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        player = new Player(new Vector2(100, Gdx.graphics.getHeight() / 2f));
        ground = new Ground();
        coinFactory = new CoinFactory();
        coinPatterns = new ArrayList<>();
        coinPatterns.add(new LinePattern());
        coinPatterns.add(new RectanglePattern());

        jetpackCommand = new JetpackCommand(player);

        restartCommand = new RestartCommand(player, gameManager);

        scoreUIObserver = new ScoreUIObserver();

        gameManager.addObserver(scoreUIObserver);

        background = new Background();

        obstacleFactory = new ObstacleFactory();
        setDifficulty(new EasyDifficultyStrategy());
        obstacleSpawnTimer = 0f;
        gameManager.startGame();
        System.out.println("Di playing state");
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.529f, 0.808f, 0.922f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (batch == null) {
            batch = new SpriteBatch();
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.render(batch);

        player.render(batch);
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle instanceof HomingMissile){
                obstacle.render(batch);
            }
        }

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ground.renderShape(shapeRenderer);
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (!(obstacle instanceof HomingMissile)){
                obstacle.render(shapeRenderer);
            }
        }

        for (Coin coin : coinFactory.getInUse()) {
            coin.renderShape(shapeRenderer);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        scoreUIObserver.render(scoreUIObserver.getScore(), gameManager.getCoins());
    }

    public void setDifficulty(DifficultyStrategy newStrategy) {
        difficultyStrategy = newStrategy;
        obstacleFactory.setWeights(newStrategy.getObstacleWeights());
        System.out.println("The difficulty has changed");
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jetpackCommand.execute();
        }

        if (player.getIsDead()) {
            obstacleFactory.releaseAllObstacles();
            obstacleSpawnTimer = 0f;
            lastObstacleSpawnX = 0f;
            camera.position.set(cameraFocus, camera.viewportHeight / 2f, 0);
            lastLoggedScore = -1;
            GameManager.getInstance().endGame();
            gsm.set(new GameOverState(gsm));
        }

        background.update(camera.position.x);

        cameraFocus = player.getPosition().x + Gdx.graphics.getWidth() * cameraOffset;
        player.update(delta);
        updateCamera(delta);
        ground.update(camera.position.x);
        player.checkBoundaries(ground, Gdx.graphics.getHeight());
        updateObstacle(delta);
        updateCoins(delta);

        checkCollisions();

        int currentScoreMeters = (int)player.getDistanceTravelled();
        int previousScoreMeters = gameManager.getScore();

        if (currentScoreMeters > previousScoreMeters) {
            if (currentScoreMeters != lastLoggedScore) {
                System.out.println("Distance: " + currentScoreMeters + "m");
                lastLoggedScore = currentScoreMeters;
                currentScore = currentScoreMeters;
            }

            gameManager.setScore(currentScoreMeters);
        }

        updateDifficulty(currentScoreMeters);

        //System.out.println("Distance Travelled = " + player.getDistanceTravelled());
    }

    private void updateCoins(float delta) {
        coinSpawnTimer += delta;
        float spawnInterval = 2f + random.nextFloat()*2f;
        if (coinSpawnTimer > spawnInterval) {
            float cameraRightEdge = camera.position.x + Gdx.graphics.getWidth() / 2f;
            float spawnX = cameraRightEdge + 100f;
            if (!coinPatterns.isEmpty()) {
                CoinPattern pattern = coinPatterns.get(random.nextInt(coinPatterns.size()));
                pattern.spawn(coinFactory, ground.getTopY(), spawnX, Gdx.graphics.getHeight());
            }

            coinSpawnTimer = 0;
        }

        Iterator<Coin> iterator = coinFactory.getInUse().iterator();
        float tepiKiri = camera.position.x - Gdx.graphics.getWidth() / 2f;
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.update(delta);
            if (coin.getPosition().x < tepiKiri) {
                coin.setActive(false);
                iterator.remove();
                coinFactory.release(coin);
            }
        }

        checkCoinCollisions();
    }

    private void updateDifficulty(int score) {
        if (score > 1000 && !(difficultyStrategy instanceof MediumDifficultyStrategy) && !(difficultyStrategy instanceof HardDifficultyStrategy)) {
            gsm.push(new DifficultyTransitionState(gsm, this, new MediumDifficultyStrategy()));
        }else if (score > 2000 && !(difficultyStrategy instanceof HardDifficultyStrategy)) {
            gsm.push(new DifficultyTransitionState(gsm, this, new HardDifficultyStrategy()));
        }
    }

    private void updateCamera(float delta) {
        camera.position.set(cameraFocus, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    private void updateObstacle(float delta) {
        obstacleSpawnTimer += delta;
        //System.out.println(obstacleSpawnTimer);
        if (obstacleSpawnTimer > difficultyStrategy.getSpawnInterval()) {
            spawnObstacle();
            obstacleSpawnTimer = 0f;
        }

        float tepiKiri = camera.position.x - Gdx.graphics.getWidth() / 2f;
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle instanceof HomingMissile) {
                HomingMissile homingMissile = (HomingMissile) obstacle;
                homingMissile.setTarget(player);
                homingMissile.update(delta);
            }

            if (obstacle.getPosition().x < tepiKiri) {
                obstacleFactory.releaseObstacle(obstacle);
            }
        }
    }

    private void spawnObstacle() {
        float tepiKanan = camera.position.x + Gdx.graphics.getWidth() / 2f;
        float spawnAheadOfCamera = tepiKanan + SPAWN_AHEAD_DISTANCE;
        float spawnAfterLastObstacle = lastObstacleSpawnX + difficultyStrategy.getMinGap();
        float baseSpawnX = Math.max(spawnAheadOfCamera, spawnAfterLastObstacle);

        for (int x = 0; x < difficultyStrategy.getDensity(); x++) {
            obstacleFactory.createRandomObstacle(ground.getTopY(), baseSpawnX, player.getHeight());
            lastObstacleSpawnX = baseSpawnX;
        }
    }

    private void checkCollisions() {
        Rectangle colliderPlayer = player.getCollider();
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle.isColliding(colliderPlayer)) {
                player.die();
                return;
            }
        }
    }

    private void checkCoinCollisions() {
        Rectangle colliderPlayer = player.getCollider();

        Iterator<Coin> iterator = coinFactory.getInUse().iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            if (coin.isColliding(colliderPlayer)) {
                coin.setActive(false);
                iterator.remove();
                coinFactory.release(coin);
                gameManager.addCoin();
            }
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        obstacleFactory.releaseAllObstacles();
        scoreUIObserver.dispose();
        background.dispose();
        coinFactory.releaseAll();
    }
}
