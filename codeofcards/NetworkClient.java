package codeofcards;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClient extends Thread{ 
    
        public boolean running;
        public String msg;
        public String name;
        public String ConnectTo;
        public Socket sock;
        public Scanner in, userInput;
        public PrintStream outserver;
        public InputThread input;
        
    public void clientStartGame() throws IOException {
        this.sock = new Socket("127.0.0.1", 2343); 
        this.in = new Scanner(sock.getInputStream());
        this.userInput = new Scanner(System.in);
        this.outserver = new PrintStream(sock.getOutputStream());
        this.input = new InputThread(this.outserver, this.in, this);
        String confirmed = "false";
        
        System.out.println("I'am connected :D");
        System.out.println("Create your player: ");
        do {
            outserver.println(new Scanner(System.in).nextLine()); 
            confirmed = this.in.nextLine();
        } while (confirmed.equals("false"));
        System.out.println("Waiting for game to start");
        this.input.start();
    }   
    
    @Override
    public void run() {
        System.out.println("It's tour turn");
        outserver.println(new Scanner(System.in).nextLine()); 
    }
} 
