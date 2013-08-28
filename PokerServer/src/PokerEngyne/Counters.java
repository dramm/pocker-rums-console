/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import pokerserver.Player;

/**
 *
 * @author Андрей
 */
public class Counters {
    private int[] wins;
    private int[] tie;
    public int iteration;
    public Counters(Player[] players){
        int size = players.length;
        wins = new int[size];
        tie = new int[size];
    }

    public int[] getWins() {
        return wins;
    }

    public void setWins(int index) {
        this.wins[index]++;
    }

    public int[] getTie() {
        return tie;
    }

    public void setTie(int index) {
        this.tie[index]++;
    }
}
