package codeofcards;

import codeofcards.commands.Command;

import java.util.ArrayList;

public class Game {
    // Singleton :/
    public static Game instance;

    public int currentPlayer;
    public ArrayList<Player> playerList = new ArrayList<>();
    public Board board;
    public CardFactory cardfactory;

    public boolean isHost = true;

    public ArrayList<Command> commandQueue = new ArrayList<>();
    
    public Game() {
        this.board = new Board();
        this.cardfactory = board.cardFactory;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.hostGame();
    }

    public void hostGame() {
        isHost = true;
        runGame();
    }
    
    public void runGame() {
        instance = this;

        System.out.println("Welcome to the game.");
        Player player1 = new Player(0, "CodeOfCards.Player 1", 50, this);
        Player player2 = new Player(1, "CodeOfCards.Player 2", 50, this);
        playerList.add(player1);
        playerList.add(player2);
        currentPlayer = 0;
        while(true) {
            playerList.get(currentPlayer).turn(playerList.get(currentPlayer == 0 ? 1 : 0));
            currentPlayer = (currentPlayer + 1) % 2;
            System.out.println("//////////////////\n//Player " + currentPlayer + "\n//////////////////");
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
}
