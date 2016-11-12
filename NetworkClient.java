import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClient { 
    
    public void run() throws IOException {
        Socket sock = new Socket("127.0.0.1", 2343); 
        Scanner in = new Scanner(sock.getInputStream());
        PrintStream out = new PrintStream(sock.getOutputStream());
        while(true) { 
            out.println(new Scanner(System.in).nextLine()); 
            System.out.println(in.nextLine());  
        } 
    }   
} 
