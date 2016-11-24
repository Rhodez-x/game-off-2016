package codeofcards.commands;

import codeofcards.Board;
import codeofcards.Game;
import codeofcards.cards.Card;

/**
 * Created by mads on 13/11/2016.
 */
public class AddCardToFunctionCommand extends Command {
    int playerId;
    int functionId;
    int place;
    Board board;
    Card card;

    public AddCardToFunctionCommand(int playerId, Card card, int functionId, int place, Board board) {
        this.playerId = playerId;
        this.card = card;
        this.functionId = functionId;
        this.place = place;
        this.board = board;
    }

    @Override
    public void execute(Game game) {
        this.board.addCardToFunction(card, functionId, place);
        game.getPlayer(playerId).removeCard(card);
    }
}
