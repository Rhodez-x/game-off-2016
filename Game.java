
import java.util.ArrayList;

public class Game {
    String currentPlayer;
    public ArrayList<Player> playerList;
    
    Game() {
        
    }
    
    public void runGame() {
        System.out.println("Welcome to the game.");
        Player player1 = new Player("Player 1", 50);
        Player player2 = new Player("Player 2", 50);
        playerList.add(player1);
        playerList.add(player2);
        playerList.get(0).turn();
    }
}
