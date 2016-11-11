import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    public String name;
    public Board board;
    public int life;
    public int actionLeft;
    public int discardCount; // The number of card the player has to discard in the satrt of the players turn. 
    public ArrayList<Card> cards;
        
    Player(String name, int life, Board board) {
        this.name = name;
        this.life = life;
        this.board = board;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public void turn(Player other) {
        //board.onTurnStart(this);
        for(int i=1; i<4; i++){
            Scanner sc = new Scanner(System.in);
            int chooise = sc.nextInt();
            System.out.println("Choose what to do. \n 1 = Draw card \n 2 = Play card directly \n 3 = place a card in a function ");
            switch (chooise) {
                case 1: // Draw card
                    this.drawCard();
                    break;            
                case 2:// Play a card, diretcly 
                    
                    break;
                case 3: // Place a card in function
                    
                    break;
                default: // Player chooise a worng number. 
                    break;
            }
            System.out.println("Count is: " + i);
            System.out.println(other);
        }
        
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
        System.out.println("You got a card");
    }

    public void addLife(int amount) {
        this.life = this.life + amount;
    }
}
