package codeofcards;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhileConnect extends Thread{
    public ServerSocket server;
    public NetworkHost serverClass;
    public Scanner scannerIn;
    public boolean searching = false;
    
    WhileConnect(ServerSocket server, NetworkHost serverClass) {
        this.server = server;
        this.serverClass = serverClass;
        this.searching = true;
    }
    
    @Override
    public void run () {
        while(this.searching) {
            try {
                Socket socket = this.server.accept();
                //scannerIn = new Scanner(socket.getInputStream());
                //String networkPlayerUsername = scannerIn.nextLine();
                this.connectPlayer(socket, serverClass);
                //this.serverClass.addClient(socket, "networkPlayer");
                System.out.println("A Player is connected");
            } catch (IOException ex) {
                //Logger.getLogger(WhileConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void connectPlayer(Socket clientSocket, NetworkHost theHost) throws IOException {
        CreateNetworkPlayer networkPlayer = new CreateNetworkPlayer(clientSocket, theHost);
        networkPlayer.start();
    }
    
   /* @Override
    public boolean isInterrupted() {
        System.out.println("Is interrupted");
        return true;
        
    }*/
    
}
