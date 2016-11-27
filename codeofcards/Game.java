package codeofcards;

import codeofcards.cards.EventCard;
import codeofcards.commands.BoardAddFunctionCommand;
import codeofcards.commands.Command;
import codeofcards.commands.DrawCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Game {
    // Singleton :/
    public static Game instance;
    
    public int playerCount;
    public int currentPlayer;
    public int numPlayers;
    public ArrayList<Player> playerList;
    public Board board;
    public CardFactory cardfactory;
    public boolean isHost = false;

    public ArrayList<Command> commandQueue = new ArrayList<>();

    public Scanner scanner;
    
    public Game() {
        this.board = new Board();
        this.playerList = new ArrayList<>();
        this.cardfactory = board.cardFactory;
        this.scanner = new Scanner(System.in);
        this.playerCount = 0;
    }

    public void hostGame() throws IOException, InterruptedException {
        isHost = true;
        NetworkHost gameServer = new NetworkHost("gameTitel", this);
        gameServer.startAddClient();
    }
    
    public void setupGame() throws IOException {
        boolean askForPlayers = true;
        String playerName;
        String again;
        do {
            System.out.println("Add new player \n Enter playername:");
            playerName = this.scanner.next();
            this.addPlayer(playerName);
            System.out.println("Add another player? y/n");
            again = this.scanner.next();
            if (again.equals("y")) {
                playerName = null;
            } else {
                askForPlayers = false;
            }
        } while(askForPlayers);
        this.shuffelPlayerOrder(playerList);
        this.currentPlayer = 0;
        this.runGame();
    }
    
    public void setupNetworkGame(ArrayList<Player> players, int playerCount) throws IOException {
        System.out.println("Setting up game:");
        this.playerList = players;
        this.currentPlayer = 0;
        this.runNetworkGame();
    }
    
    public void addPlayer(String playerName) throws IOException {
        Player player = new Player(playerCount, playerName, this, null);
        this.playerList.add(player);
        this.playerCount++;
    }
    
    public void shuffelPlayerOrder(ArrayList playerOrder) {
        // has to shuffel the starting order of the players
    }
    
    public void runNetworkGame() {
        instance = this;
        for (Player players : playerList) {
            players.sendMsgToPlayer.println("Welcome to this network game. :D");
            players.sendMsgToPlayer.println(playerList);
        }
        
        // makes the standart functionscards to the bord. 
        // Event cards
        this.board.addFunctionToBoard(new EventCard("OnTurnStart()", 2, 15, Board.EventType.OnTurnStart));
        this.board.addFunctionToBoard(new EventCard("OnTurnEnd()", 2, 15, Board.EventType.OnTurnEnd));
        this.board.addFunctionToBoard(new EventCard("OnCardPlayed()", 2, 15, Board.EventType.OnCardPlayed));
        this.board.addFunctionToBoard(new EventCard("OnCardDraw()", 2, 15, Board.EventType.OnCardDraw));
        
        for (int i = 0; i < this.playerList.size(); i++) {
            communicateWithNetworkPlayers(this.playerList, this.playerList.get(i) + "Draws five cards");
            for (int j = 0; j < 5; j++) {
                this.execute(new DrawCommand(this.playerList.get(i).id));
            }
        }
        while(true) {
            this.playerList.get(this.currentPlayer).turn(playerList);
            this.changeTurn();
        }
    }
    
    public void communicateWithNetworkPlayers(ArrayList<Player> playerList, String msg) {
        for (Player players : playerList) {
            players.sendMsgToPlayer.println(msg);
        }
    }
    
    public void runGame() {
        instance = this;
        
        communicateWithNetworkPlayers(this.playerList, "Welcome to the game.");
        //communicateWithNetworkPlayers(this.playerList, this.playerList);
        for (int i = 0; i < this.playerList.size(); i++) {
            communicateWithNetworkPlayers(this.playerList, this.playerList.get(i) + "Draws five cards");
            for (int j = 0; j < 5; j++) {
                this.execute(new DrawCommand(this.playerList.get(i).id));
            }
            communicateWithNetworkPlayers(this.playerList, "Player hand" + this.playerList.get(i).cards);
        }
        while(true) {
            this.playerList.get(this.currentPlayer).turn(this.playerList);
            this.changeTurn();
        }
    }
    
    public void changeTurn() {
        communicateWithNetworkPlayers(playerList, "//////////////////\n//Player " + playerList.get(currentPlayer).name + "\n//////////////////");
        this.currentPlayer = (this.currentPlayer + 1)%playerList.size();
    }

    public Player getPlayer(int id) {
        return playerList.get(id);
    }

    public void execute(Command command) {
        commandQueue.add(command);
        command.execute(this);
    }

    public void serverExecute(Command command) {
        if (isHost) {
            command.execute(this);
        }
    }

    public int getInput(String text, int maxChoice, Player player) {
        int choice = -1;

        while(choice < 0 || choice > maxChoice + 1) {
            player.sendMsgToPlayer.println("itsyourturn4322");
            player.sendMsgToPlayer.format("%s [1-%d]", text, maxChoice);
            choice = (player.playersInput.nextInt());
        }
        
        return choice - 1;
    }
}
