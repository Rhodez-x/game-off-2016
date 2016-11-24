package codeofcards;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static public Game game;
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        boolean runningMain = true;
        while(runningMain) {
            System.out.println("Welcome to CodeOfCards, \n 1 = start local game \n 2 = Start game as host \n 3 = Connect a game \n anything else = exit game");
            int choice = sc.nextInt();
            if (choice == 1) {
                game = new Game();
                game.setupGame();
            } else if (choice == 2) {
                runningMain = false;
                System.out.println("Starting game with you as host");
                game = new Game();
                game.hostGame();
            } else if (choice == 3) {
                runningMain = false;
                System.out.println("Connect a game");
                //Player myPlayer = new Player();
                //myPlayer.connectToServer();
                NetworkClient client = new NetworkClient();
                client.clientStartGame();
            } else if (choice == 0) {
                break;
            }
        }
        System.out.println("Program closed");
    }
    
}
