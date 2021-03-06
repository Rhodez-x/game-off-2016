package codeofcards;

import codeofcards.cards.Card;
import codeofcards.cards.FunctionCard;
import codeofcards.cards.StatementCard;
import codeofcards.commands.*;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.String.format;
import java.net.Socket;

import java.util.*;

public class Player {
    public int id;
    public String name;
    public Socket playerSocket;
    public PrintStream sendMsgToPlayer;
    public Scanner playersInput;
    private Game game;
    private Board board;
    private Board playerBoard;
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
        this.playerBoard = new Board();
        for (int i = 0; i < 2; i++) {
            this.playerBoard.addFunctionToBoard(new FunctionCard("Function", 1, 20));
        }
        this.playerSocket = playerSocket;
        this.sendMsgToPlayer = new PrintStream(this.playerSocket.getOutputStream());
        this.playersInput = new Scanner(this.playerSocket.getInputStream());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void turn(ArrayList<Player> playerList) {
        //board.onTurnStart(this);

        while (discardCount > 0) {
            this.sendMsgToPlayer.format("You must discard %d cards. Choose card to discard %s\n", discardCount, cards);
            int choice = game.getInput("Card", cards.size(), this);

            if (choice < 0) continue;

            game.serverExecute(new DiscardCommand(id, cards.get(choice)));
            discardCount--;
        }

        int actionsDone = 0;

        while(actionsDone < 3){
            String temp = "";
            for (Player players : playerList) {
                temp = temp + players.name + " " + players.life + " " + players.playerBoard.functionCards + "\n";
            }
            this.game.communicateWithNetworkPlayers(playerList, temp);
    

            this.sendMsgToPlayer.println("Board:");
            for (int j = 0; j < board.functionCards.size(); j++) {
                this.sendMsgToPlayer.format("%s: %s\n", j, board.functionCards.get(j), board.functionCards.get(j).cards);
            }

            this.sendMsgToPlayer.println("\nChoose what to do, " + name);
            this.sendMsgToPlayer.println("1: Draw | 2: Play Card | 3: Place card in function ");
            this.sendMsgToPlayer.println("Your hand: " + this.cards);
            
            int choice;

            do  {
                choice = game.getInput("Action " + (actionsDone + 1), 3, this);
            } while (choice < 0);

            switch (choice) {
                case 0: // Draw card
                    game.serverExecute(new DrawCommand(this.id));
                    actionsDone++;
                    break;
                case 1:// Play a card, directly
                    this.sendMsgToPlayer.println("Which card do you want to play?" + this.cards);
                    choice = game.getInput("Card", this.cards.size(), this);
                    if (choice < 0) break;

                    this.sendMsgToPlayer.println("You played this card" + this.cards.get(choice));
                    Player other = this.choosePlayer(playerList);
                    this.playCard(choice, other);
                    actionsDone++;
                    break;
                case 2: // Place a card in function
                    // First ask player on witch  board, own og tabel board
                    this.sendMsgToPlayer.println("Where to place the card: \n 1: Place card in own hand? \n 2: Place card on tabel");
                    int witchBoard = game.getInput("Card", this.cards.size(), this);
                    Board choosenBoard = board;
                    if (witchBoard == 0) {
                        choosenBoard = this.playerBoard;
                    } else if (witchBoard == 1) {
                        choosenBoard = this.board;
                    }
                    // witch card to be played
                    this.sendMsgToPlayer.println("Which card do you want to place?" + this.cards);
                    int cardIndex = game.getInput("Card", this.cards.size(), this);
                    if (cardIndex < 0) break;
                    
                    //In witch funciton 
                    this.sendMsgToPlayer.println("In which function should it be placed?" + choosenBoard.functionCards);
                    int functionIndex = game.getInput("Function", choosenBoard.functionCards.size(), this);
                    if (functionIndex < 0) break;

                    if (choosenBoard.functionCards.get(functionIndex).cards.size() > 0) {
                        this.sendMsgToPlayer.println("Where in the function should it be placed?" + choosenBoard.functionCards.get(functionIndex).cards);
                        choice = game.getInput("Index", choosenBoard.functionCards.get(functionIndex).cards.size() + 1, this);
                        if (functionIndex < 0) break;
                    }
                    else {
                        choice = 0;
                    }

                    game.serverExecute(new AddCardToFunctionCommand(id, cards.get(cardIndex), functionIndex, choice, choosenBoard));
                    actionsDone++;
                    break;
                default: // CodeOfCards.Player chooise a worng number.
                    break;
            }
        }
    }

    public Player choosePlayer(ArrayList<Player> playerList) {
        this.sendMsgToPlayer.println(playerList);
        int theChoose = game.getInput("Card", cards.size(), this);
        return playerList.get(theChoose);
    }
    
    void playCard(int cardIndex, Player other) { // Directplay (not lay card in a function on the table)
        Card card = cards.get(cardIndex);
        int functionIndex = 0;

        if (card instanceof StatementCard &&
                (((StatementCard) card).statementType == StatementCard.StatementType.SelfExecuteFunction ||
                 ((StatementCard) card).statementType == StatementCard.StatementType.OtherExecuteFunction ||
                 ((StatementCard) card).statementType == StatementCard.StatementType.CyclesIncrement ||
                 ((StatementCard) card).statementType == StatementCard.StatementType.CyclesDecrement)) {
            this.sendMsgToPlayer.println("Which function? " + board.functionCards);
            functionIndex = game.getInput("Function", board.functionCards.size(), this);
        }

        game.serverExecute(new PlayCardCommand(this.id, other.id, card, functionIndex));
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
