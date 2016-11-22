package codeofcards;

import codeofcards.commands.BoardAddFunctionCommand;
import codeofcards.commands.Command;
import codeofcards.commands.DrawCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Game {
    // Singleton :/
    public static Game instance;

    public int currentPlayer;
    public int numPlayers;
    public ArrayList<String> playerOrder;
    public HashMap<String, Player> playerList = new HashMap<>();
    public Board board;
    public CardFactory cardfactory;
    public boolean isHost = false;

    public ArrayList<Command> commandQueue = new ArrayList<>();

    public Scanner scanner;
    
    public Game() {
        this.board = new Board();
        this.playerOrder = new ArrayList<>();
        this.cardfactory = board.cardFactory;
        this.scanner = new Scanner(System.in);
    }

    public void hostGame() {
        isHost = true;
        this.setupNetworkGame();
    }
    
    public void setupGame() {
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
        this.runGame();
    }
    
    public void setupNetworkGame() {
        
    }
    
    public void addPlayer(String playerName) {
        Player player = new Player(1, playerName, this);
        this.playerList.put(player.name, player);
        this.playerOrder.add(player.name);
    }
    
    public void shuffelPlayerOrder(ArrayList playerOrder) {
        // has to shuffel the starting order of the players
    }
    
    public void runGame() {
        instance = this;
        
        System.out.println("Welcome to the game.");
        
        for (String players : playerOrder) {
            System.out.println(players + "Draws five cards");
            for (int i = 0; i < 5; i++) {
                this.execute(new DrawCommand(playerList.get(players).id));
            }
        }

        currentPlayer = 0;
        while(true) {
            playerList.get(currentPlayer).turn(playerList.get(currentPlayer == 0 ? 1 : 0));
            currentPlayer = (currentPlayer + 1) % 2;
            System.out.println("//////////////////\n//Player " + playerList.get(currentPlayer).name + "\n//////////////////");
        }
    }

    public Player getPlayer(int playerId) {
        return playerList.get(playerId);
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
