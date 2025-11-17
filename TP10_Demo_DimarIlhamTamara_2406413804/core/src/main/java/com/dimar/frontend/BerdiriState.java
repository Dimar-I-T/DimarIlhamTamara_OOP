package com.dimar.frontend;

public class BerdiriState implements PlayerState{
    private Player player;
    public BerdiriState(Player player) {
        this.player = player;
    }

    @Override
    public void berdiri() {
        System.out.println("Kamu sedang berdiri.");

        if ((player.isA() || player.isD()) && !(player.getState() instanceof BerjalanState)) {
            player.setState(player.getBerjalanState());
        }
    }

    @Override
    public void berjalan() {
    }

    @Override
    public void berlari() {
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public String getStateName() {
        return "Berdiri";
    }
}
