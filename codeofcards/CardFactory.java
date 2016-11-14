package codeofcards;

import codeofcards.cards.*;

import java.util.ArrayList;
import java.util.Random;

public class CardFactory {
    Board board;
    ArrayList<Card> cardPrototypes = new ArrayList<>();

    CardFactory(Board board) {
        this.board = board;

        // Draw Cards
        this.cardPrototypes.add(new StatementCard("Self.DrawCard()", 4, StatementCard.StatementType.SelfDrawCard));
        this.cardPrototypes.add(new StatementCard("Other.DrawCard()", 4, StatementCard.StatementType.OtherDrawCard));

        // Increment Life
        this.cardPrototypes.add(new StatementCard("Self.Life++", 4, StatementCard.StatementType.SelfIncrementLife));
        this.cardPrototypes.add(new StatementCard("Other.Life++", 4, StatementCard.StatementType.OtherIncrementLife));

        // Decrement Life
        this.cardPrototypes.add(new StatementCard("Self.Life--", 4, StatementCard.StatementType.SelfDecrementLife));
        this.cardPrototypes.add(new StatementCard("Other.Life--", 4, StatementCard.StatementType.OtherDecrementLife));

        // Discard
        this.cardPrototypes.add(new StatementCard("Self.DiscardCard()", 4, StatementCard.StatementType.SelfDiscardCard));
        this.cardPrototypes.add(new StatementCard("Other.DiscardCard()", 4, StatementCard.StatementType.OtherDiscardCard));

        // Function Executing
        this.cardPrototypes.add(new StatementCard("Execute Function", 5, StatementCard.StatementType.SelfExecuteFunction));
        this.cardPrototypes.add(new StatementCard("Execute Function with Other", 3, StatementCard.StatementType.OtherExecuteFunction));

        // Function Cycles
        this.cardPrototypes.add(new StatementCard("Function.Cycles++", 4, StatementCard.StatementType.CyclesIncrement));
        this.cardPrototypes.add(new StatementCard("Function.Cycles--", 3, StatementCard.StatementType.CyclesDecrement));

        // Function cards
        this.cardPrototypes.add(new FunctionCard("Function", 5, 5));
        this.cardPrototypes.add(new FunctionCard("Function", 3, 10));
        this.cardPrototypes.add(new FunctionCard("Function", 1, 20));

        // Repeat cards
        this.cardPrototypes.add(new RepeatCard("Repeat 3", 3, 3));
        this.cardPrototypes.add(new RepeatCard("Repeat 5", 1, 5));

        // Event cards
        this.cardPrototypes.add(new EventCard("OnTurnStart()", 2, 15, Board.EventType.OnTurnStart));
        this.cardPrototypes.add(new EventCard("OnTurnEnd()", 2, 15, Board.EventType.OnTurnEnd));
        this.cardPrototypes.add(new EventCard("OnCardPlayed()", 2, 15, Board.EventType.OnCardPlayed));
        this.cardPrototypes.add(new EventCard("OnCardDraw()", 2, 15, Board.EventType.OnCardDraw));
    }

    public Card newCard() {
        ArrayList<Card> cards = new ArrayList<>();

        for (Card card : this.cardPrototypes) {
            if (card instanceof EventCard) {
                if (board.cardsInPlay.contains(card)) {
                    continue;
                }
            }

            cards.add(card);
        }

        int frequencySum = 0;

        for (Card card : cards) {
            frequencySum += card.frequency;
        }

        Random rng = new Random();

        int randomNumber = rng.nextInt(frequencySum);

        for (Card card : cards) {
            if (randomNumber < card.frequency) {
                return card.clone();
            }

            randomNumber -= card.frequency;
        }

        // We should not be able to come here
        return null;
    }
}
