package com.dimar.frontend;

public class ScoreDisplay implements ScoreObserver{
    @Override
    public void onScoreUpdate(int newScore) {
        System.out.println("Skor baru = " + newScore);
    }
}
