import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import static java.sql.DriverManager.println;
import java.util.Scanner;
public class NetworkHost {
    
    public void run() throws IOException  { 
    ServerSocket server = new ServerSocket(2343); 
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