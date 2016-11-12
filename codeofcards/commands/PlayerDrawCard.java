package codeofcards.commands;

import codeofcards.CardFactory;
import codeofcards.Game;
import codeofcards.cards.Card;
import codeofcards.commands.Command;

public class PlayerDrawCard extends Command {
    public int targetId;
    public Card card;

    public PlayerDrawCard(int targetId, CardFactory cardFactory) {
        this.targetId = targetId;
        this.card = cardFactory.newCard();
    }

    @Override
    public void execute(Game game) {
        game.playerList.get(this.targetId).giveCard(this.card);
        game.board.addCard(this.card);
    }
}
