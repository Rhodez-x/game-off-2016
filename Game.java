
import java.util.ArrayList;

public class Game {
    String currentPlayer;
    public ArrayList<Player> playerList = new ArrayList<>();
    public Board board;
    public CardFactory cardfactory;
    
    public Game() {
        this.board = new Board();
        this.cardfactory = new CardFactory();
    }
    
    public void runGame() {
        System.out.println("Welcome to the game.");
        Player player1 = new Player("Player 1", 50, this.board, this.cardfactory);
        Player player2 = new Player("Player 2", 50, this.board, this.cardfactory);
        playerList.add(player1);
        playerList.add(player2);
        playerList.get(0).turn(playerList.get(1));
    }
}
