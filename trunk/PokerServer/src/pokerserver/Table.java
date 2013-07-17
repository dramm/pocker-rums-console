/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.Sequence;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class Table {
    private static Logger log = Logger.getLogger(Table.class.getName());
    private Deck deck;
    private Player[] players;
    private Cards[] bord;
    
    public Table(int players){
        this.players = new Player[players];
        this.bord = new Cards[5];
        this.deck = new Deck();
        for(int i=0; i<players; i++){
            this.players[i] = new Player();
        }
    }
    public void PreFlop(){
        
        for(int i = 0;  i < players.length; i++){
            Cards[] tmp = new Cards[2];
            for(int j = 0; j < 2; j++){
                tmp[j] = deck.IssueCard();
            }
            players[i].setPocketCards(tmp);
        }
        /*Cards[] t = new Cards[2];
        t[0] = new Cards(0, 3, 7);
        t[1] = new Cards(0, 3, 3);
        players[0].setPocketCards(t);
        t = new Cards[2];
        t[0] = new Cards(0, 4, 2);
        t[1] = new Cards(0, 4, 5);
        players[1].setPocketCards(t);
        t = new Cards[2];
        t[0] = new Cards(0, 3, 2);
        t[1] = new Cards(0, 3, 9);
        players[2].setPocketCards(t);*/
    }
    public void Flop(){
        for(int i=0; i<3; i++){
            bord[i] = deck.IssueCard();
            //bord[i] = new Cards(0, 4, i);
        }
       /* bord[0] = new Cards(0, 3, 11);
        bord[1] = new Cards(0, 3, 13);
        bord[2] = new Cards(0, 3, 8);*/
    }
    public void Turn(){
        bord[3] = deck.IssueCard();
        //bord[3] = new Cards(0, 1, 2);
    }
    public void River(){
        bord[4] = deck.IssueCard();
        //bord[4] = new Cards(0, 3, 9);
    }
    public void WhoWin(){
        for(int i=0; i<players.length; i++){
            System.out.println("Player "+i+" have: "+players[i].getFirstCard().getDignitysId()+" ("+
                    players[i].getFirstCard().getSuitsId()+") "+
                    players[i].getSecondCard().getDignitysId()+
                    " ("+players[i].getFirstCard().getSuitsId()+")");
        }
        for(int i=0; i<bord.length;i++){
            System.out.println("Bord "+bord[i].getDignitysId()+" ("+bord[i].getSuitsId()+")");
        }
        for(int i=0; i<players.length;i++){
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 10){
                System.out.println("Player "+i+" have one pair");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 11){
                System.out.println("Player "+i+" have two pair");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 12){
                System.out.println("Player "+i+" have set");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 13){
                System.out.println("Player "+i+" have streight");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 14){
                System.out.println("Player "+i+" have flush");
            }
        }
        
    }
    
}