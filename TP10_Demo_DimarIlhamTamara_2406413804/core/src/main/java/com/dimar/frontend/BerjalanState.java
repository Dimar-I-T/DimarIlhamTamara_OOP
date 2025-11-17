package com.dimar.frontend;

public class BerjalanState implements PlayerState{
    private Player player;
    public BerjalanState(Player player) {
        this.player = player;
    }

    @Override
    public void berdiri() {
    }

    @Override
    public void berjalan() {
        System.out.println("Kamu sedang berjalan.");

        if (player.isSHIFT() && !(player.getState() instanceof BerlariState)) {
            player.setState(player.getBerlariState());
        }else {
            if (!player.isA() && !player.isD() && !(player.getState() instanceof BerdiriState)) {
                player.setState(player.getBerdiriState());
            }
        }
    }

    @Override
    public void berlari() {
    }

    @Override
    public float getSpeed() {
        return 100f;
    }

    @Override
    public String getStateName() {
        return "Berjalan";
    }
}
