package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.FunctionCard;
import codeofcards.cards.LineCard;
import codeofcards.commands.BoardAddFunctionCommand;
import codeofcards.commands.Command;
import codeofcards.commands.DrawCommand;
import codeofcards.commands.PlayCardCommand;

import javax.sound.sampled.Line;
import java.util.*;

public class Player {
    public int id;
    public String name;
    private Game game;
    private Board board;
    public int life;
    public int actionLeft;
    public int discardCount; // The number of card the player has to discard in the satrt of the players turn.
    public ArrayList<Card> cards = new ArrayList<>();
        
    Player(int id, String name, int life, Game game) {
        this.id = id;
        this.name = name;
        this.life = life;
        this.game = game;
        this.board = game.board;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public void turn(Player other) {
        //board.onTurnStart(this);
        for(int i=1; i<4; i++){
            System.out.println();

            System.out.format("Player 0: %2d | Player 1: %2d\n", life, other.life);

            System.out.println("Board:");
            for (int j = 0; j < board.functionCards.size(); j++) {
                System.out.format("%s: %-20s -> %s\n", j, board.functionCards.get(j), board.functionCards.get(j).cards);
            }

            System.out.println("\nChoose what to do\n1: Draw | 2: Play Card | 3: Place card in function ");
            System.out.println("Your hand: " + this.cards);
            Scanner sc = new Scanner(System.in);
            int chooise = sc.nextInt();
            switch (chooise) {
                case 1: // Draw card
                    game.serverExecute(new DrawCommand(id));
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

        }
        
    }
    void playCard(int cardIndex, Player other) { // Directplay (not lay card in a function on the table)
        Card card = cards.get(cardIndex);
        int functionIndex = 0;

        if (card instanceof LineCard &&
                (((LineCard) card).lineType == LineCard.LineType.SelfExecuteFunction ||
                 ((LineCard) card).lineType == LineCard.LineType.OtherExecuteFunction ||
                 ((LineCard) card).lineType == LineCard.LineType.CyclesIncrement ||
                 ((LineCard) card).lineType == LineCard.LineType.CyclesDecrement)) {
            System.out.println("Which function? " + board.functionCards);
            Scanner sc = new Scanner(System.in);
            functionIndex = sc.nextInt();
        }

        game.serverExecute(new PlayCardCommand(id, other.id, card, functionIndex));
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

    /*public void drawCard() {
        System.out.println("You got a card");
        this.cards.add(board.addCard());
        System.out.println("You cards are now " + this.cards);
        /*
        debug line, print 19 cards
        for(int i=1; i<20; i++){
            System.out.println(cardFactory.newCard());
        }*/ /*
    }*/

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
