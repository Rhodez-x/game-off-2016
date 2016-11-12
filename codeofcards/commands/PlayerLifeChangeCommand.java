package codeofcards.commands;

import codeofcards.Game;

public class PlayerLifeChangeCommand extends Command {
    public int targetId;
    public int amount;

    public PlayerLifeChangeCommand(int targetId, int amount) {
        this.targetId = targetId;
        this.amount = amount;
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(this.targetId).addLife(amount);
    }
}
