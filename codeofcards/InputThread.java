package codeofcards;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.event.ActionEvent;

public class InputThread extends Thread{
    private PrintStream input;
    private Scanner in;
    private NetworkClient client;
    
    InputThread(PrintStream input, Scanner in, NetworkClient client) {
        this.input = input;
        this.in = in;
        this.client = client;
    }
    
    @Override
    public void run() {
        String recivedMsg;
        while(true) {
            recivedMsg = this.in.nextLine();
            if (recivedMsg.equals("itsyourturn4322")) {
                while(true) {
                    try {
                        input.println(new Scanner(System.in).nextInt());
                        break;
                    } catch (NumberFormatException | InputMismatchException ex) {

                    }
                }
            } else {
                System.out.println(recivedMsg);
            }
        }
    }
}
