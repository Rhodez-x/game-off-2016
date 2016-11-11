
import java.util.ArrayList;

public class Game {
    String currentPlayer;
    public ArrayList<Player> playerList = new ArrayList<>();
    
    Game() {
        
    }
    
    public void runGame() {
        System.out.println("Welcome to the game.");
        Board board = new Board();
        Player player1 = new Player("Player 1", 50, board);
        Player player2 = new Player("Player 2", 50, board);
        playerList.add(player1);
        playerList.add(player2);
        playerList.get(0).turn(playerList.get(1));
    }
}
