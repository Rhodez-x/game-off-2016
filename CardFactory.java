import java.util.ArrayList;
import java.util.Random;

public class CardFactory {
    Board board;
    ArrayList<Card> cardPrototypes = new ArrayList<>();
    
    CardFactory() {
        
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
