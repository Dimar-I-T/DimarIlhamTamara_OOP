package com.dimar.frontend;

public class BerlariState implements PlayerState {
    private Player player;
    public BerlariState(Player player) {
        this.player = player;
    }

    @Override
    public void berdiri() {
    }

    @Override
    public void berjalan() {
    }

    @Override
    public void berlari() {
        System.out.println("Kamu sedang berlari.");
        if (!player.isSHIFT() && !(player.getState() instanceof BerjalanState)) {
            player.setState(player.getBerjalanState());
        }else {
            if (!player.isA() && !player.isD() && !(player.getState() instanceof BerdiriState)) {
                player.setState(player.getBerdiriState());
            }
        }
    }

    @Override
    public float getSpeed() {
        return 300f;
    }

    @Override
    public String getStateName() {
        return "Berlari";
    }
}
