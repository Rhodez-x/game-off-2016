package codeofcards;

import codeofcards.commands.BoardAddFunctionCommand;
import codeofcards.commands.Command;
import codeofcards.commands.DrawCommand;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    // Singleton :/
    public static Game instance;

    public int currentPlayer;
    public ArrayList<Player> playerList = new ArrayList<>();
    public Board board;
    public CardFactory cardfactory;

    public boolean isHost = false;

    public ArrayList<Command> commandQueue = new ArrayList<>();
    
    public Game() {
        this.board = new Board();
        this.cardfactory = board.cardFactory;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to CodeOfCards, \n 1 = start local game \n 2 = Start game as host \n 3 = Connect a game \n anything else = exit game");
        int choice = sc.nextInt();
        sc.close();
        if (choice == 1) {
            Game game = new Game();
            game.hostGame();
        } else if (choice == 2) {
            System.out.println("Starting game with you as host");
        } else if (choice == 3) {
            System.out.println("Connect a game");
        } else {
            
        }
    }

    public void hostGame() {
        isHost = true;
        runGame();
    }
    
    public void runGame() {
        instance = this;

        System.out.println("Welcome to the game.");
        Player player1 = new Player(0, "Monty Python", 50, this);
        Player player2 = new Player(1, "Marilyn Monroe", 50, this);

        playerList.add(player1);
        playerList.add(player2);

        for (int i = 0; i < 5; i++) {
            execute(new DrawCommand(player1.id));
            execute(new DrawCommand(player2.id));
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
        Scanner sc = new Scanner(System.in);

        while(choice < 0 || choice > maxChoice + 1) {
            System.out.format("%s [1-%d]", text, maxChoice);
            choice = sc.nextInt();
        }
        sc.close();

        return choice - 1;
    }
}
