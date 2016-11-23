package codeofcards;

import java.io.PrintStream;
import java.util.Scanner;
import javafx.event.ActionEvent;

public class InputThread extends Thread{
    public PrintStream input;
    public Scanner in;
    
    InputThread(PrintStream input, Scanner in) {
        this.input = input;
        this.in = in;
    }
    
    @Override
    public void run() {
        ActionEvent a = new ActionEvent();
        while(true) {
            System.out.println(this.in.nextLine());
        }
    }
}
