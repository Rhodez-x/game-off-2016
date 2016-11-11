import java.util.ArrayList;

public class Player {
    public String name;
    public int life;
    public int actionLeft;
    public int discardCount; // The number of card the player has to discard in the satrt of the players turn. 
    public ArrayList<Card> cards;
        
    Player(String name, int life) {
        
    }
    
    public void turn() {
        /*
       input choise
                
       1 == playcard
       2 == play car to function
       3 draw card
       */
    }
    void playCard(int cardIndex, Player other) { // Directplay (not lay card in a function on the table)
        Card card = cards.get(cardIndex);
        card.execute(1, this, other);

        cards.remove(cardIndex);
    }
    
    void playCardToFunction() { // Player lay a card to a function
        /*
        Spørg spiller vælge et kort. 
        sender et kort til board
        */
    }
    
    public void discardCard() {
        
    }

    public void drawCard() {
    }

    public void addLife(int amount) {
    }
}
