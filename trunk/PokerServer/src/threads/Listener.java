/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokerserver.Game;

/**
 *
 * @author Андрей
 */
public class Listener extends Thread{
    private Socket clientSocket = null;
    private InputStream input = null;
    private Game game = new Game();
    
    
    @Override
    public void run(){
        SpeakerThread sp = new SpeakerThread();
        sp.setSc(clientSocket);
        sp.start();
        try {
            input = new BufferedInputStream(clientSocket.getInputStream());
            int flag = 1;
            while (flag > 0) {
                byte[] command = new byte[4];
                flag = input.read(command, 0, 4); 
                int result = Functions.byteArrayToInt(command);
                switch (result) {
                    case 1000:{
                        System.out.println("Starting game");
                        if(!game.isRun()){
                            game.start();
                        }
                        break;
                    }
                        default:{
                            System.out.println("Default");
                            sp.stop();
                            game.setRun(false);
                            input.close();
                            clientSocket.close();
                            
                        }
                        
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
