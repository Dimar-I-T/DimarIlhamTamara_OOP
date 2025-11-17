package com.dimar.frontend.commands;

import com.dimar.frontend.Player;

public class JetpackCommand implements Command {
    private Player player;
    public JetpackCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        if (!player.getIsDead()) {
            player.fly();
        }
    }
}
