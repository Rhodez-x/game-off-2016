package codeofcards.commands;

import codeofcards.Game;
import codeofcards.cards.FunctionCard;

/**
 * Created by mads on 12/11/2016.
 */
public class BoardAddFunctionCommand extends Command {
    public int playerId;
    public FunctionCard functionCard;

    public BoardAddFunctionCommand(int playerId, FunctionCard functionCard) {
        this.playerId = playerId;
        this.functionCard = functionCard;
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(playerId).removeCard(functionCard);
        game.board.addFunctionToBoard(this.functionCard);
    }
}
