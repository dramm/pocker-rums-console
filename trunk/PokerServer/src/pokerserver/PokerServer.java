/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;


/**
 *
 * @author Андрей
 */
public class PokerServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //comment
        Table tmp = new Table(9);
        tmp.PreFlop();
        tmp.Flop();
        tmp.Turn();
        tmp.River();
        tmp.WhoWin();
        System.out.println("helo");
    }
}
