package codeofcards;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateNetworkPlayer extends Thread {
    private NetworkHost theHost;
    private Socket clientSocket;
    private PrintStream output;
    private Scanner inputScanner;
    
    CreateNetworkPlayer(Socket clientSocket, NetworkHost theHost) throws IOException {
        this.theHost = theHost;
        this.clientSocket = clientSocket;
        this.output = new PrintStream(clientSocket.getOutputStream());
        this.inputScanner = new Scanner(clientSocket.getInputStream());
    }
    
    @Override
    public void run() {
        while(true) {
            this.output.println("Enter username: ");
            String username = this.inputScanner.nextLine();
            boolean check = false;
            try {
                check = this.theHost.addClient(this.clientSocket, username);
            } catch (IOException ex) {
                Logger.getLogger(CreateNetworkPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(CreateNetworkPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (check) {
                this.output.println("true");
                break;
            } else {
                this.output.println("false");
            }
        }
        
    }
    
}
