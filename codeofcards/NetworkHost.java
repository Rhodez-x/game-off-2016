package codeofcards;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class NetworkHost extends Thread {
    public String username;
    public ServerSocket server;
    public Socket socket;
    public boolean serverRunning;
    public Scanner in;
    public PrintStream serverPrint;
    public String connectinCode;
    public String password;
    public WhileConnect connectThis;
    //static public HashMap<String, PrintStream> connectedPlayers = new HashMap<>();
    static public HashMap<String, Player> ConnectedPlayers = new HashMap<>();
    
    NetworkHost(String username) throws IOException {
        this.server = new ServerSocket(2343); 
        this.serverRunning = true;
        this.connectThis = new WhileConnect(this.server, this);
    }
    public void startAddClient() throws InterruptedException, IOException {
        String stopSearching;
        Scanner sc = new Scanner(System.in);
        this.connectThis.start();
        System.out.println("Wating for clients to connect");
        System.out.println("Write 'stop' for stop searching for players");
        stopSearching = sc.next();
        if (stopSearching.equals("stop")) {
            this.stopAddClient(server);
        } 
    }

    public void addClient(Socket sock) throws IOException, InterruptedException {
        System.out.println("Client added");
        /*this.socket = sock;
        this.in = new Scanner(sock.getInputStream());
        this.serverPrint = new PrintStream(sock.getOutputStream());
        this.connectinCode = this.in.nextLine();*/
    }
    
    public void stopAddClient(ServerSocket server) throws InterruptedException, IOException{
        connectThis.searching = false;
        server.close();
    }

    /*while (true) { 
        Socket sock = server.accept(); 
        Scanner in = new Scanner(sock.getInputStream()); 
        PrintStream out = new PrintStream(sock.getOutputStream());             
        while (true) {
            System.out.println(in.nextLine()); 
            out.println(new Scanner(System.in).nextLine()); 
        }   
    }*/ 

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