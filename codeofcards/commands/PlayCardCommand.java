package codeofcards.commands;

import codeofcards.Game;
import codeofcards.cards.Card;
import codeofcards.cards.FunctionCard;
import codeofcards.cards.LineCard;

/**
 * Created by mads on 13/11/2016.
 */
public class PlayCardCommand extends Command {
    int playerId;
    int otherId;
    Card card;

    int functionIndex;

    public PlayCardCommand(int playerId, int otherId, Card card, int functionIndex) {
        this.playerId = playerId;
        this.otherId = otherId;
        this.card = card;

        this.functionIndex = functionIndex;
    }

    // Java, please http://en.cppreference.com/w/cpp/language/default_arguments
    public PlayCardCommand(int playerId, int otherId, Card card) {
        this(playerId, otherId, card, 0);
    }

    @Override
    public void execute(Game game) {
        if (card instanceof FunctionCard) {
            Command command = new BoardAddFunctionCommand(playerId, (FunctionCard) card);

            game.execute(command);
        }
        else if (card instanceof LineCard &&
                 (((LineCard) card).lineType == LineCard.LineType.SelfExecuteFunction ||
                  ((LineCard) card).lineType == LineCard.LineType.OtherExecuteFunction)) {

            int player = ((LineCard) card).lineType == LineCard.LineType.SelfExecuteFunction ? playerId : otherId;
            int other = ((LineCard) card).lineType == LineCard.LineType.SelfExecuteFunction ? otherId : playerId;

            Command command = new ExecuteFunctionCommand(player, other, functionIndex);
            game.execute(command);
        }
        else {
            card.execute(1, game.getPlayer(playerId), game.getPlayer(otherId));
        }

        game.getPlayer(playerId).removeCard(card);
    }
}
