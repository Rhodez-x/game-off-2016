import java.util.ArrayList;
import java.util.Random;

public class CardFactory {
    Board board;
    ArrayList<Card> cardPrototypes = new ArrayList<>();
    
    CardFactory() {
        // Draw Cards
        this.cardPrototypes.add(new LineCard("Self.DrawCard()", 4, LineCard.LineType.SelfDrawCard));
        this.cardPrototypes.add(new LineCard("Other.DrawCard()", 4, LineCard.LineType.OtherDrawCard));

        // Function cards
        this.cardPrototypes.add(new FunctionCard("Function", 5, 5));
        this.cardPrototypes.add(new FunctionCard("Function", 3, 10));
        this.cardPrototypes.add(new FunctionCard("Function", 1, 25));

        // Repeat cards
        this.cardPrototypes.add(new RepeatCard("For 0..3 do", 4, 3));
        this.cardPrototypes.add(new RepeatCard("For 0..5 do", 2, 5));
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
