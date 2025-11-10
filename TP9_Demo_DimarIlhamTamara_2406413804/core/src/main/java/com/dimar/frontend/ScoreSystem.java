package com.dimar.frontend;

import java.util.ArrayList;

public class ScoreSystem {
    ArrayList<ScoreObserver> scoreObservers = new ArrayList<>();
    int score;
    public void registerObserver(ScoreObserver observer) {
        scoreObservers.add(observer);
    }

    public void removeObserver(ScoreObserver observer) {
        scoreObservers.remove(observer);
    }

    public void notifyObservers() {
        for (ScoreObserver observer : scoreObservers) {
            observer.onScoreUpdate(score);
        }
    }

    public void addScore(int amount) {
        score += amount;
        notifyObservers();
    }
}
