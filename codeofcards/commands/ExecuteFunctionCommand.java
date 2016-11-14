package codeofcards.commands;

import codeofcards.Game;

/**
 * Created by mads on 13/11/2016.
 */
public class ExecuteFunctionCommand extends Command {
    public int userId;
    public int otherId;
    public int functionId;

    public ExecuteFunctionCommand(int userId, int otherId, int functionId) {
        this.userId = userId;
        this.otherId = otherId;
        this.functionId = functionId;
    }

    @Override
    public void execute(Game game) {
        game.board.executeFunction(this.functionId, game.getPlayer(this.userId), game.getPlayer(this.otherId));
    }
}
