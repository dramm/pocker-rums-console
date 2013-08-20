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
import pokerserver.Game_new;

/**
 *
 * @author Андрей
 */
public class Listener extends Thread{
    private Socket clientSocket = null;
    private InputStream input = null;
    private Game_new game;

    public Listener() {
        this.game = new Game_new();
    }
    
    
    @Override
    public void run(){
        SpeakerThread sp = new SpeakerThread();
        try {
            Thread th = new Thread(game);
            sp.setOutput(clientSocket.getOutputStream());
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
                            th.start();
                            sp.start();
                        }
                        break;
                    }
                    case 1010:{
                        if(!Bridge.newData.isGoNext()){
                            Bridge.newData.setGoNext(true);
                        }
                        break;
                    }
                }
            }
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                game.setRun(false);
                sp.setFlag(false);
               
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
