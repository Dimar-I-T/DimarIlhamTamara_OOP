package com.dimar.frontend.observers;

public interface Subject {
    public abstract void addObserver(Observer observers);
    public abstract void removeObserver(Observer observers);
    public abstract void notifyObservers(int score);
}
