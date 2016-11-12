import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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