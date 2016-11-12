package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.FunctionCard;

import java.util.*;

public class Player {
    public int id;
    public String name;
    public Board board;
    public int life;
    public int actionLeft;
    public int discardCount; // The number of card the player has to discard in the satrt of the players turn.
    public ArrayList<Card> cards = new ArrayList<>();
        
    Player(int id, String name, int life, Board board) {
        this.id = id;
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
            System.out.println("Choose what to do. \n 1 = Draw card \n 2 = Play card directly \n 3 = place a card in a function ");
            Scanner sc = new Scanner(System.in);
            int chooise = sc.nextInt();
            switch (chooise) {
                case 1: // Draw card
                    this.drawCard();
                    break;            
                case 2:// Play a card, diretcly 
                    System.out.println("Witch card do you want to play?" + this.cards);
                    chooise = sc.nextInt();
                    System.out.println("You played this card" + this.cards.get(chooise));
                    this.playCard(chooise, other);
                    break;
                case 3: // Place a card in function
                    
                    break;
                case 0:
                    break;
                default: // CodeOfCards.Player chooise a worng number.
                    break;
            }
            System.out.println("CodeOfCards.Board contains " + board.functionCards);
        }
        
    }
    void playCard(int cardIndex, Player other) { // Directplay (not lay card in a function on the table)
        Card card = cards.get(cardIndex);
        if (card instanceof FunctionCard) {
            board.addFunctionToBoard((FunctionCard)card);
        }
        card.execute(1, this, other);

        removeCard(card);
    }
    
    void playCardToFunction() { // CodeOfCards.Player lay a card to a function
        /*
        Spørg spiller vælge et kort. 
        sender et kort til board
        */
    }
    
    public void discardCard(int cardIndex) {
        removeCard(cardIndex);
    }

    public void drawCard() {
        System.out.println("You got a card");
        this.cards.add(board.addCard());
        System.out.println("You cards are now " + this.cards);
        /*
        debug line, print 19 cards
        for(int i=1; i<20; i++){
            System.out.println(cardFactory.newCard());
        }*/ 
    }

    public void giveCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        board.removeCard(card);
    }

    public void removeCard(int cardIndex) {
        Card card = cards.get(cardIndex);
        removeCard(card);
    }

    public void addLife(int amount) {
        this.life += amount;
    }

    public void executeFunction(Player other) {
        // Get function to execute
        int functionToExecute = 0;

        board.executeFunction(functionToExecute, this, other);
    }
}
