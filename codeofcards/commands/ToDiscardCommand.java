package codeofcards.commands;

import codeofcards.Game;

/**
 * Created by Mads on 13-11-2016.
 */
public class ToDiscardCommand extends Command {
    int playerId;
    int numCards;

    public ToDiscardCommand(int playerId, int numCards) {
        this.playerId = playerId;
        this.numCards = numCards;
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(playerId).discardCount += numCards;
    }
}
