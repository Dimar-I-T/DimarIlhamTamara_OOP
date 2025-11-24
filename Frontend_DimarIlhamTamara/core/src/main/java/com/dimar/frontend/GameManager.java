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
                    currentPlayerId = parse.getString("playerId");
                    Gdx.app.log("PLAYER", "ID tersimpan: " + currentPlayerId);
                } catch (Exception exception){
                    Gdx.app.error("JSON_ERROR", "Gagal parsing playerId", exception);
                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("ERROR", error);
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
        int jarak = scoreManager.getScore();
        backendService.submitScore(currentPlayerId, score, coinsCollected, jarak, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                Gdx.app.log("SUCCESS", "Berhasil mensubmit score");
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("ERROR", error);
            }
        });
    }

    public void addCoin() {
        coinsCollected++;
        Gdx.app.log("COIN COLLECTED!", "Total: "  + coinsCollected);
    }

    public void setScore(int distance) {
        if (gameActive) {
            scoreManager.setScore(distance);
        }
    }

    public int getCoins() {
        return coinsCollected;
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
