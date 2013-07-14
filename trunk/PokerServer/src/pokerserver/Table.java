/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;

/**
 *
 * @author Андрей
 */
public class Table {
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
        Cards[] tmp = new Cards[2];
        for(int i=0; i<players.length; i++){
            for(int j=0; j<2;j++){
                tmp[j] = deck.IssueCard();
            }
            players[i].setPocketCards(tmp);
        }
    }
    public void Flop(){
        for(int i=0; i<3; i++){
            bord[i] = deck.IssueCard();
        }
    }
    public void Turn(){
        bord[3] = deck.IssueCard();
    }
    public void River(){
        bord[4] = deck.IssueCard();
    }
    
}
