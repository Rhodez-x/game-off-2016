package codeofcards.commands;

import codeofcards.CardFactory;
import codeofcards.Game;
import codeofcards.cards.Card;

public class DrawCommand extends Command {
    public String targetPlayerName;
    public Card card;

    public DrawCommand(String targetPlayerName, CardFactory cardFactory) {
        this.targetPlayerName = targetPlayerName;
        this.card = cardFactory.newCard();
    }

    public DrawCommand(String targetPlayerName) {
        this(targetPlayerName, Game.instance.cardfactory);
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(targetPlayerName).giveCard(this.card);
        game.board.addCard(this.card);
    }
}
