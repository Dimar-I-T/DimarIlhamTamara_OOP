package com.dimar.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dimar.frontend.observers.Observer;
import com.dimar.frontend.observers.ScoreManager;
import com.dimar.frontend.services.BackendService;

public class GameManager {
    private static GameManager instance;
    private int score;
    private ScoreManager scoreManager;
    private boolean gameActive;
    private BackendService backendService;
    private String currentPlayerId = null;
    private int coinsCollected = 0;

    private GameManager() {
        score = 0;
        scoreManager = new ScoreManager();
        scoreManager.setScore(0);
        gameActive = false;
        backendService = new BackendService();
    }

    public void registerPlayer(String username) {
        backendService.createPlayer(username, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JsonValue parse = new JsonReader().parse(response);
                    currentPlayerId = parse.name;
                    Gdx.app.log("id", "ID tersimpan");
                } catch (Exception exception){

                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("id", "error");
            }
        });
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void startGame() {
        score = 0;
        scoreManager.setScore(0);
        gameActive = true;
        System.out.println("Game Started!");
        coinsCollected = 0;
    }

    public void endGame() {
        if (currentPlayerId == null) {
            Gdx.app.error("i", "Cannot submit score");
            return;
        }

        int score = scoreManager.getScore() + coinsCollected*10;
//        backendService.submitScore(currentPlayerId, score, coinsCollected);
    }

    public void setScore(int newScore) {
        if (gameActive) {
            scoreManager.setScore(newScore);
        }
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public void addObserver(Observer observer) {
        scoreManager.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        scoreManager.removeObserver(observer);
    }
}
