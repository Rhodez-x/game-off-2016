package codeofcards;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to CodeOfCards, \n 1 = start local game \n 2 = Start game as host \n 3 = Connect a game \n anything else = exit game");
        while(true) {
            int choice = sc.nextInt();
            if (choice == 1) {
                Game game = new Game();
                game.hostGame();
            } else if (choice == 2) {
                System.out.println("Starting game with you as host");
                //NetworkHost gameServer = new NetworkHost();
            } else if (choice == 3) {
                System.out.println("Connect a game");
            } else if (choice == 0) {
                break;
            }
        }
    }
    
}
