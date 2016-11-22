package codeofcards.commands;

import codeofcards.CardFactory;
import codeofcards.Game;
import codeofcards.cards.Card;

public class DrawCommand extends Command {
    public int targetId;
    public Card card;

    public DrawCommand(int targetId, CardFactory cardFactory) {
        this.targetId = targetId;
        this.card = cardFactory.newCard();
    }

    public DrawCommand(int targetId) {
        this(targetId, Game.instance.cardfactory);
    }

    @Override
    public void execute(Game game) {
        game.getPlayer(targetId).giveCard(this.card);
        game.board.addCard(this.card);
    }
}
