/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.Sequence;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
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
        
       /* sc = new ServerSocket(7777);
        while (true) {
            Listener l = new Listener();
            l.setClientSocket(sc.accept());
            l.start();
            l.join();
        }*/
        Cards[] cards = new Cards[7];
        cards[0] = DBTools.getCards(1);
        cards[5] = DBTools.getCards(11);
        cards[1] = DBTools.getCards(25);
        cards[2] = DBTools.getCards(29);
        cards[3] = DBTools.getCards(33);
        cards[4] = DBTools.getCards(37);
        
        cards[6] = DBTools.getCards(51);
        Arrays.sort(cards);
        if(Sequence.isStraight(cards)){
            System.out.println("TRUE");
        }
    }
}
