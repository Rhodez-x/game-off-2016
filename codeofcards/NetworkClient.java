package codeofcards;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClient { 
    
        public boolean running;
        public String msg;
        public String name;
        public String ConnectTo;
        public Socket sock;
        public Scanner in, userInput;
        public PrintStream outserver;
        public InputThread input;
        
    public void run() throws IOException {
        this.sock = new Socket("127.0.0.1", 2343); 
        this.in = new Scanner(sock.getInputStream());
        this.userInput = new Scanner(System.in);
        this.outserver = new PrintStream(sock.getOutputStream());
        this.input = new InputThread(this.outserver, this.in);
        this.input.start();
        String confirmed = "false";
        //out.println(new Scanner(System.in).nextLine()); 
        System.out.println("I'am connected :D");
        System.out.println("Create your player: ");
        do {
            outserver.println(this.userInput.nextLine());
             
        } while (confirmed.equals("false"));
        System.out.println("Waiting for game to start");
    }   
} 
