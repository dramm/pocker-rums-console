/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import java.io.IOException;
import java.net.ServerSocket;
import threads.Listener;
 

/**
 *
 * @author Андрей
 */
public class PokerServer {

    static private ServerSocket sc = null;
    public static void main(String[] args) throws IOException, InterruptedException {
        CardColection.getCards();
        sc = new ServerSocket(7777);
        System.out.println("Server start");
        while (true) {
        Listener l = new Listener();
        System.out.println("Wait connectig client");
        l.setClientSocket(sc.accept());
        System.out.println("Client connected");
        l.start();
        l.join();
        Thread.sleep(100);
        }    
    }
}
