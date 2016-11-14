package codeofcards.commands;

import codeofcards.Game;
import codeofcards.cards.Card;

/**
 * Created by mads on 13/11/2016.
 */
public class AddCardToFunctionCommand extends Command {
    int playerId;
    Card card;
    int functionId;
    int place;

    public AddCardToFunctionCommand(int playerId, Card card, int functionId, int place) {
        this.playerId = playerId;
        this.card = card;
        this.functionId = functionId;
        this.place = place;
    }

    @Override
    public void execute(Game game) {
        game.board.addCardToFunction(card, functionId, place);
        game.getPlayer(playerId).removeCard(card);
    }
}
