package com.dimar.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dimar.frontend.Background;
import com.dimar.frontend.GameManager;
import com.dimar.frontend.Ground;
import com.dimar.frontend.Player;
import com.dimar.frontend.commands.Command;
import com.dimar.frontend.commands.JetpackCommand;
import com.dimar.frontend.commands.RestartCommand;
import com.dimar.frontend.factories.ObstacleFactory;
import com.dimar.frontend.observers.ScoreUIObserver;
import com.dimar.frontend.obstacles.BaseObstacle;
import com.dimar.frontend.obstacles.HomingMissile;
import com.dimar.frontend.strategies.DifficultyStrategy;
import com.dimar.frontend.strategies.EasyDifficultyStrategy;
import com.dimar.frontend.strategies.HardDifficultyStrategy;
import com.dimar.frontend.strategies.MediumDifficultyStrategy;

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

    float currentScore;

    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;
    private int lastLoggedScore = -1;
    float cameraFocus;

    private Background background;
    private Command jetpackCommand, restartCommand;
    private ScoreUIObserver scoreUIObserver;
    private DifficultyStrategy difficultyStrategy;

    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        shapeRenderer = new ShapeRenderer();
        gameManager = GameManager.getInstance();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        player = new Player(new Vector2(100, Gdx.graphics.getHeight() / 2f));
        ground = new Ground();

        jetpackCommand = new JetpackCommand(player);

        restartCommand = new RestartCommand(player, gameManager);

        scoreUIObserver = new ScoreUIObserver();

        gameManager.addObserver(scoreUIObserver);

        background = new Background();

        obstacleFactory = new ObstacleFactory();
        setDifficulty(new EasyDifficultyStrategy());
        obstacleSpawnTimer = 0f;
        gameManager.startGame();
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

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.end();

        player.renderShape(shapeRenderer);
        ground.renderShape(shapeRenderer);

        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            obstacle.render(shapeRenderer);
        }

        scoreUIObserver.render(scoreUIObserver.getScore());
        shapeRenderer.end();
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
            gsm.set(new GameOverState(gsm));
        }

        background.update(camera.position.x);

        cameraFocus = player.getPosition().x + Gdx.graphics.getWidth() * cameraOffset;
        player.update(delta);
        updateCamera(delta);
        ground.update(camera.position.x);
        player.checkBoundaries(ground, Gdx.graphics.getHeight());
        updateObstacle(delta);
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

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        obstacleFactory.releaseAllObstacles();
        scoreUIObserver.dispose();
        background.dispose();
    }
}
