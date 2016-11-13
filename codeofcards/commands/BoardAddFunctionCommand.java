package codeofcards.commands;

import codeofcards.Game;
import codeofcards.cards.FunctionCard;

/**
 * Created by mads on 12/11/2016.
 */
public class BoardAddFunctionCommand extends Command {
    public int playerId;
    public int functionIndex;
    public FunctionCard functionCard;

    BoardAddFunctionCommand(int playerId, int functionIndex, FunctionCard functionCard) {
        this.playerId = playerId;
        this.functionIndex = functionIndex;
        this.functionCard = functionCard;
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(playerId).removeCard(functionCard);
        game.board.addFunctionToBoard(this.functionCard);
    }
}
