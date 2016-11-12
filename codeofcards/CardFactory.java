package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.EventCard;
import codeofcards.cards.FunctionCard;
import codeofcards.cards.RepeatCard;

import java.util.ArrayList;
import java.util.Random;

public class CardFactory {
    Board board;
    ArrayList<Card> cardPrototypes = new ArrayList<>();
    
    CardFactory() {
        // Draw Cards
        this.cardPrototypes.add(new LineCard("Self.DrawCard()", 4, LineCard.LineType.SelfDrawCard));
        this.cardPrototypes.add(new LineCard("Other.DrawCard()", 4, LineCard.LineType.OtherDrawCard));

        // Increment Life
        this.cardPrototypes.add(new LineCard("Self.Life++", 4, LineCard.LineType.SelfIncrementLife));
        this.cardPrototypes.add(new LineCard("Other.Life++", 4, LineCard.LineType.OtherIncrementLife));

        // Decrement Life
        this.cardPrototypes.add(new LineCard("Self.Life--", 4, LineCard.LineType.SelfDecrementLife));
        this.cardPrototypes.add(new LineCard("Other.Life--", 4, LineCard.LineType.OtherDecrementLife));

        // Discard
        this.cardPrototypes.add(new LineCard("Self.DiscardCard()", 4, LineCard.LineType.SelfDiscardCard));
        this.cardPrototypes.add(new LineCard("Other.DiscardCard()", 4, LineCard.LineType.OtherDiscardCard));

        // Function Executing
        this.cardPrototypes.add(new LineCard("Execute Function", 5, LineCard.LineType.SelfExecuteFunction));
        this.cardPrototypes.add(new LineCard("Execute Function", 3, LineCard.LineType.OtherExecuteFunction));

        // Function Cycles
        this.cardPrototypes.add(new LineCard("Function.Cycles++", 4, LineCard.LineType.CyclesIncrement));
        this.cardPrototypes.add(new LineCard("Function.Cycles--", 3, LineCard.LineType.CyclesDecrement));

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
                return card;
            }

            randomNumber -= card.frequency;
        }

        // We should not be able to come here
        return null;
    }
}
