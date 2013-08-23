/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.Sequence;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
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
        System.out.println("Server start");
        while (true) {
            Listener l = new Listener();
            System.out.println("Wait connectig client");
            l.setClientSocket(sc.accept());
            System.out.println("Client connected");
            l.start();
            l.join();
            Thread.sleep(10);
        }
        /*Cards[] cards = new Cards[7];
        cards[0] = DBTools.getCards(48);
        cards[5] = DBTools.getCards(44);
        cards[1] = DBTools.getCards(40);
        cards[2] = DBTools.getCards(36);
        cards[3] = DBTools.getCards(52);
        cards[4] = DBTools.getCards(5);
        cards[6] = DBTools.getCards(5);
        Arrays.sort(cards);
        System.out.println(Sequence.isRoyalFlush(cards));*/
    }
}
