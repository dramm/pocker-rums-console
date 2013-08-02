/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import Enums.Xor;
import java.io.IOException;
import java.net.ServerSocket;
import threads.Listener;


/**
 *
 * @author Андрей
 */
public class PokerServer {

    /**
     * @param args the command line arguments
     */
    static private ServerSocket sc = null;
    public static void main(String[] args) throws IOException, InterruptedException {
        
        sc = new ServerSocket(7777);
        while (true) {
            Listener l = new Listener();
            l.setClientSocket(sc.accept());
            l.start();
            l.join();
        }
    }
}
