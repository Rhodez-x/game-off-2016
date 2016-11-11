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

        // Function cards
        this.cardPrototypes.add(new FunctionCard("Function", 5, 5));
        this.cardPrototypes.add(new FunctionCard("Function", 3, 10));
        this.cardPrototypes.add(new FunctionCard("Function", 1, 20));

        // Repeat cards
        this.cardPrototypes.add(new RepeatCard("Repeat 3", 3, 3));
        this.cardPrototypes.add(new RepeatCard("Repeat 5", 1, 5));
    }
    
    public Card newCard() {
        int frequencySum = 0;

        for (Card card : this.cardPrototypes) {
            frequencySum += card.frequency;
        }

        Random rng = new Random();

        int randomNumber = rng.nextInt(frequencySum);

        for (Card card : this.cardPrototypes) {
            if (randomNumber < card.frequency) {
                return card;
            }

            randomNumber -= card.frequency;
        }

        // We should not be able to come here
        return null;
    }
}
