/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import DataBaseClasses.Cards;
import pokerserver.Deck;
import pokerserver.Player;

/**
 *
 * @author Андрей
 */
public class CalculateTh implements Runnable{
    private static int iteration = 50;
    private Counters counter;
    private boolean flag ;
    private Deck deck;
    private Player[] players;
    public CalculateTh(Player[] players, Deck deck){
        counter = new Counters(players);
        this.deck = deck;
        this.players = players;
        flag = false;
    }
    @Override
    public void run() {
        for (int i = 0; i < iteration; i++) {
            Player[] tmpPlayers = players;
            Deck tmp = new Deck(deck);
            tmp.shuffleDeck(); 
            Cards[] board = new Cards[5];
            for (int j = 0; j < board.length; j++){
                board[j] = tmp.IssueCard();
            }
            for (int j = 0; j < players.length; j++) {
                tmpPlayers[j].setCombinationPover(Sequence.CheckSequence(tmpPlayers[j].getPocketCards(), board));
            }
            Player[] winners = Sequence.getWinner(players);
            
            for (int j = 0; j < winners.length; j++) {
                for (int k = 0; k < players.length; k++) {
                    if(winners[j].getPlayerId() == players[k].getPlayerId()){
                        if(winners.length > 1){
                            getCounter().setTie(k);
                            getCounter().setWins(k);
                        }
                        else{
                            getCounter().setWins(k);
                        }
                    }
                }
            }
        }
        counter.iteration = iteration;
        flag = true;
    }

    /**
     * @return the counter
     */
    public Counters getCounter() {
        return counter;
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }
    
}
