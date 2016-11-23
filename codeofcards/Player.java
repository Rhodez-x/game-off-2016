package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.StatementCard;
import codeofcards.commands.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import java.util.*;

public class Player {
    public int id;
    public String name;
    public Socket playerSocket;
    public PrintStream sendMsgToPlayer;
    private Game game;
    private Board board;
    public int life;
    public int actionLeft;
    public int discardCount; // The number of card the player has to discard in the satrt of the players turn.
    public ArrayList<Card> cards = new ArrayList<>();

    Player() {
        
    }
    Player(int id, String name, Game game, Socket playerSocket) throws IOException {
        this.id = id;
        this.name = name;
        this.life = 30;
        this.game = game;
        this.board = game.board;
        this.playerSocket = playerSocket;
        this.sendMsgToPlayer = new PrintStream(this.playerSocket.getOutputStream());
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    public void connectToServer() throws IOException {
        Socket sock = new Socket("127.0.0.1", 2343); 
        Scanner in = new Scanner(sock.getInputStream());
        PrintStream out = new PrintStream(sock.getOutputStream());
        //out.println(new Scanner(System.in).nextLine()); 
        System.out.println("I'am connected :D");  
    }

    public void turn(Player other) {
        //board.onTurnStart(this);

        while (discardCount > 0) {
            System.out.format("You must discard %d cards. Choose card to discard %s\n", discardCount, cards);
            int choice = game.getInput("Card", cards.size());

            if (choice < 0) continue;

            game.serverExecute(new DiscardCommand(id, cards.get(choice)));
            discardCount--;
        }

        int actionsDone = 0;

        while(actionsDone < 3){
            System.out.format("%s: %2d | %s: %2d\n", name, life, other.name, other.life);

            System.out.println("Board:");
            for (int j = 0; j < board.functionCards.size(); j++) {
                System.out.format("%s: %s\n", j, board.functionCards.get(j), board.functionCards.get(j).cards);
            }

            System.out.println("\nChoose what to do, " + name);
            System.out.println("1: Draw | 2: Play Card | 3: Place card in function ");
            System.out.println("Your hand: " + this.cards);

            int choice;

            do  {
                choice = game.getInput("Action " + (actionsDone + 1), 3);
            } while (choice < 0);

            switch (choice) {
                case 0: // Draw card
                    game.serverExecute(new DrawCommand(id));
                    actionsDone++;
                    break;
                case 1:// Play a card, directly
                    System.out.println("Which card do you want to play?" + this.cards);
                    choice = game.getInput("Card", this.cards.size());
                    if (choice < 0) break;

                    System.out.println("You played this card" + this.cards.get(choice));
                    this.playCard(choice, other);
                    actionsDone++;
                    break;
                case 2: // Place a card in function
                    System.out.println("Which card do you want to place?" + this.cards);
                    int cardIndex = game.getInput("Card", this.cards.size());
                    if (cardIndex < 0) break;

                    System.out.println("In which function should it be placed?" + board.functionCards);
                    int functionIndex = game.getInput("Function", board.functionCards.size());
                    if (functionIndex < 0) break;

                    if (board.functionCards.get(functionIndex).cards.size() > 0) {
                        System.out.println("Where in the function should it be placed?" + board.functionCards.get(functionIndex).cards);
                        choice = game.getInput("Index", board.functionCards.get(functionIndex).cards.size() + 1);
                        if (functionIndex < 0) break;
                    }
                    else {
                        choice = 0;
                    }

                    game.serverExecute(new AddCardToFunctionCommand(id, cards.get(cardIndex), functionIndex, choice));
                    actionsDone++;
                    break;
                default: // CodeOfCards.Player chooise a worng number.
                    break;
            }
        }
    }

    void playCard(int cardIndex, Player other) { // Directplay (not lay card in a function on the table)
        Card card = cards.get(cardIndex);
        int functionIndex = 0;

        if (card instanceof StatementCard &&
                (((StatementCard) card).statementType == StatementCard.StatementType.SelfExecuteFunction ||
                 ((StatementCard) card).statementType == StatementCard.StatementType.OtherExecuteFunction ||
                 ((StatementCard) card).statementType == StatementCard.StatementType.CyclesIncrement ||
                 ((StatementCard) card).statementType == StatementCard.StatementType.CyclesDecrement)) {
            System.out.println("Which function? " + board.functionCards);
            functionIndex = game.getInput("Function", board.functionCards.size());
        }

        game.serverExecute(new PlayCardCommand(id, other.id, card, functionIndex));
    }

    void playCardToFunction() { // CodeOfCards.Player lay a card to a function
        /*
        Spørg spiller vælge et kort.
        sender et kort til board
        */
    }

    public void discardCard(Card cardIndex) {
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
        //board.removeCard(card);
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
