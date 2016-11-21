package codeofcards;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class NetworkHost extends Thread {
    public String username;
    public Socket socket;
    public boolean serverRunning;
    public Scanner in;
    public PrintStream serverPrint;
    public String connectinCode;
    public String password;
    static public HashMap<String, PrintStream> connectedPlayers = new HashMap<>();
    static public HashMap<String, Player> ;
    
    NetworkHost(String username) throws IOException {
        ServerSocket server = new ServerSocket(2343); 
        this.serverRunning = true;
        
    }
    
    public void addClient(Socket sock) throws IOException {
        this.socket = sock;
        this.in = new Scanner(sock.getInputStream());
        this.serverPrint = new PrintStream(sock.getOutputStream());
        this.connectinCode = this.in.nextLine();
    }
        
        while (true) { 
            Socket sock = server.accept(); 
            Scanner in = new Scanner(sock.getInputStream()); 
            PrintStream out = new PrintStream(sock.getOutputStream());             
            while (true) {
                System.out.println(in.nextLine()); 
                out.println(new Scanner(System.in).nextLine()); 
            }   
        } 
    }
    @Override
    public void run() { 
      
    }   
} 
/*
public class MyServer { 
    public static void main(String[]args) throws IOException { 
        ServerSocket server = new ServerSocket(2343); 
        while (true) { 
            Socket sock = server.accept(); 
            new MultiServerHandler(sock).start();     
        }   
    }   
    public static class MultiServerHandler extends Thread {
        private Socket sock;     
        public MultiServerHandler(Socket sock) { 
        this.sock = sock;
        } 
        
        public void identify() {
            println("This is me");
        }
        
        @Override
    public void run() {
        try {             
            Scanner in = new Scanner(sock.getInputStream()); 
            PrintStream out = new PrintStream(sock.getOutputStream());
            while (true) {  
                out.println(in.nextLine().toUpperCase());
                identify();  
            }         
        } 
        catch (IOException e) {}     
    }
    }
}*/