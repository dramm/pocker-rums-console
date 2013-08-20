/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.Sequence;


/**
 *
 * @author Андрей
 */
public class Table_new {
    private Deck deck;
    private Player[] players;
    private Cards[] bord;
    private int playersCount;
    public Table_new(int playerCount){
        this.playersCount = playerCount;
        deck = new Deck();
        players = new Player[playerCount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        bord = new Cards[5];
    }
    public void startingStage(){
        deck = new Deck();
        players = new Player[playersCount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        bord = new Cards[5];
    }
    public void preflopStage(){
        for (int i = 0; i < getPlayers().length; i++) {
            getPlayers()[i].setPocketCards(deck.IssueCard(), deck.IssueCard());
        }
    }
    public void flopStage(){
        for (int i = 0; i < 3; i++) {
            getBord()[i] = deck.IssueCard();
        }
    }
    public void turnStage(){
        getBord()[3] = deck.IssueCard();
    }
    public void riverStage(){
        getBord()[4] = deck.IssueCard();
    }
    public void showdownStage(){
        for (int i = 0; i < players.length; i++) {
            players[i].setCombinationPover(Sequence.CheckSequence(players[i].getPocketCards(), bord));
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public Cards[] getBord() {
        return bord;
    }
}
