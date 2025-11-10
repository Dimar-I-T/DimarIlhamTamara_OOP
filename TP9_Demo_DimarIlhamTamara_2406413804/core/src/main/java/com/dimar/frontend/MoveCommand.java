package com.dimar.frontend;

public class MoveCommand implements Command{
    private Player player;
    private float dx;
    private float dy;

    public MoveCommand(Player player, float dx, float dy) {
        this.player = player;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        player.move(dx, dy);
    }
}
