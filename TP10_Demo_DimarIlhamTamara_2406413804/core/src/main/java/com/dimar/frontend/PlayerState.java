package com.dimar.frontend;

public interface PlayerState {
    void berdiri();
    void berjalan();
    void berlari();
    float getSpeed();
    String getStateName();
}
