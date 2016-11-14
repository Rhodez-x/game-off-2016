package codeofcards.commands;

import codeofcards.Game;
import codeofcards.cards.Card;

/**
 * Created by Mads on 14-11-2016.
 */
public class DiscardCommand extends Command {
    int playerId;
    Card card;

    public DiscardCommand(int playerId, Card card) {
        this.playerId = playerId;
        this.card = card;
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(playerId).removeCard(card);
        game.board.removeCard(card);
    }
}
