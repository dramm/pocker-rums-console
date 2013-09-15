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
            Thread.sleep(100);
        }
        /*Table_new table = new Table_new(8);
        Player[] players = new Player[8];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i);
        }
        Cards[] border = new Cards[5];
        players[0].setPocketCards(new Cards(4, 4, 2), new Cards(25, 1, 8));
        players[1].setPocketCards(new Cards(39, 3, 11), new Cards(15, 3, 5));
        players[2].setPocketCards(new Cards(37, 1, 11), new Cards(46, 2, 13));
        players[3].setPocketCards(new Cards(43, 3, 12), new Cards(45, 1, 13));
        players[4].setPocketCards(new Cards(38, 2, 10), new Cards(7, 3, 3));
        players[5].setPocketCards(new Cards(35, 3, 10), new Cards(17, 1, 6));
        players[6].setPocketCards(new Cards(13, 1, 5), new Cards(51, 3, 14));
        players[7].setPocketCards(new Cards(3, 3, 2), new Cards(29, 1, 9));
        
        border[0] = new Cards(32, 4, 9);
        border[1] = new Cards(44, 4, 12);
        border[2] = new Cards(40, 4, 11);
        border[3] = new Cards(24, 4, 7);
        border[4] = new Cards(38, 2, 11);
        table.bord = border;
        table.players = players;
        table.showdownStage();
        Player[] winners = table.getWinners();
        System.out.println(winners.length);
        /*int r = DBTools.getOlder(4, 50);
        System.out.println(r);
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
