package codeofcards;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhileConnect extends Thread{
    public ServerSocket server;
    public NetworkHost serverClass;
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
                this.serverClass.addClient(socket, "networkPlayer");
                System.out.println("A Player is connected");
                
            } catch (InterruptedException e) {
                System.out.println("Jello");
            } catch (IOException ex) {
                Logger.getLogger(WhileConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   /* @Override
    public boolean isInterrupted() {
        System.out.println("Is interrupted");
        return true;
        
    }*/
    
}
