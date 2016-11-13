package codeofcards.commands;

import codeofcards.Game;

/**
 * Created by mads on 13/11/2016.
 */
public class CyclesAddCommand extends Command {
    int functionId;
    int amount;

    public CyclesAddCommand(int functionId, int amount) {
        this.functionId = functionId;
        this.amount = amount;
    }

    @Override
    public void execute(Game game) {
        game.board.addCyclesToFunction(functionId, amount);
    }
}
