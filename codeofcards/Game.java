package codeofcards;

import java.util.ArrayList;

public class Game {
    public int currentPlayer;
    public ArrayList<Player> playerList = new ArrayList<>();
    public Board board;
    public CardFactory cardfactory;
    
    public Game() {
        this.board = new Board();
    }

    public void hostGame() {

    }
    
    public void runGame() {
        System.out.println("Welcome to the game.");
        Player player1 = new Player(0, "CodeOfCards.Player 1", 50, this.board);
        Player player2 = new Player(1, "CodeOfCards.Player 2", 50, this.board);
        playerList.add(player1);
        playerList.add(player2);
        currentPlayer = 0;
        while(true) {
            playerList.get(currentPlayer).turn(playerList.get(currentPlayer == 0 ? 1 : 0));
            currentPlayer = (currentPlayer + 1) % 2;
            System.out.println(currentPlayer);
        }
    }

    public Player getPlayer(int playerId) {
        return playerList.get(playerId);
    }
}
