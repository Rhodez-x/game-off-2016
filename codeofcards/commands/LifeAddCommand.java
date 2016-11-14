package codeofcards.commands;

import codeofcards.Game;

public class LifeAddCommand extends Command {
    public int targetId;
    public int amount;

    public LifeAddCommand(int targetId, int amount) {
        this.targetId = targetId;
        this.amount = amount;
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(this.targetId).addLife(amount);
    }
}
