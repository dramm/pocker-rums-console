/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.Sequence;
import java.io.IOException;
import java.net.ServerSocket;
 

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
        
        /*sc = new ServerSocket(7777);
        while (true) {
            Listener l = new Listener();
            l.setClientSocket(sc.accept());
            l.start();
            l.join();
        }*/
        Cards[] cards = new Cards[7];
        cards[0] = DBTools.getCards(1);
        cards[1] = DBTools.getCards(2);
        cards[2] = DBTools.getCards(3);
        for (int i = 3; i < 7; i++) {
            cards[i] = DBTools.getCards(52 / i);
        }
        for (int i = 0; i < 7; i++) {
            Sequence.PrintCard(cards[i]);
        }
        if(Sequence.isTwoPair(cards)){
            System.out.println("TRUE");
        }
    }
}
