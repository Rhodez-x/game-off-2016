package codeofcards;

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
        //String playerName = this.scanner.next();
        //Player player = new Player(playerCount, playerName, this, null);
        //players.add(player);
        this.playerList = players;
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
        }
        
        System.out.println("Welcome to the game.");
        System.out.println(playerList);
        for (int i = 0; i < this.playerList.size(); i++) {
            System.out.println(this.playerList.get(i) + "Draws five cards");
            for (int j = 0; j < 5; j++) {
                System.out.println("Card drawed");
                this.execute(new DrawCommand(this.playerList.get(i).id));
            }
            System.out.println("Player hand" + this.playerList.get(i).cards);
        }
        while(true) {
            //playerList.get(currentPlayer).turn(playerList.get(currentPlayer == 0 ? 1 : 0));
            this.playerList.get(this.currentPlayer).turn(this.playerList.get(this.currentPlayer));
            this.changeTurn();
            //currentPlayer = (currentPlayer + 1) % 2;
            //System.out.println("//////////////////\n//Player " + playerList.get(currentPlayer).name + "\n//////////////////");
        }
    }
    
    public void runGame() {
        instance = this;
        
        System.out.println("Welcome to the game.");
        System.out.println(playerList);
        for (int i = 0; i < this.playerList.size(); i++) {
            System.out.println(this.playerList.get(i) + "Draws five cards");
            for (int j = 0; j < 5; j++) {
                System.out.println("Card drawed");
                this.execute(new DrawCommand(this.playerList.get(i).id));
            }
            System.out.println("Player hand" + this.playerList.get(i).cards);
        }
        while(true) {
            //playerList.get(currentPlayer).turn(playerList.get(currentPlayer == 0 ? 1 : 0));
            this.playerList.get(this.currentPlayer).turn(this.playerList.get(this.currentPlayer));
            this.changeTurn();
            //currentPlayer = (currentPlayer + 1) % 2;
            //System.out.println("//////////////////\n//Player " + playerList.get(currentPlayer).name + "\n//////////////////");
        }
    }
    
    public void changeTurn() {
        
        System.out.println("//////////////////\n//Player " + playerList.get(currentPlayer).name + "\n//////////////////");
    }

    public Player getPlayer(int id) {
        return playerList.get(id);
    }

    public void execute(Command command) {
        commandQueue.add(command);
        command.execute(this);
    }

    public void sendCommand(Command command) {

    }

    public void serverExecute(Command command) {
        if (isHost) {
            command.execute(this);
        }
    }

    public int getInput(String text, int maxChoice) {
        int choice = -1;

        while(choice < 0 || choice > maxChoice + 1) {
            System.out.format("%s [1-%d]", text, maxChoice);
            choice = scanner.nextInt();
        }

        return choice - 1;
    }
}
