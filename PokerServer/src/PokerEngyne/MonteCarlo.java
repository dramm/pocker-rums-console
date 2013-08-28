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
public class MonteCarlo {
    private static int iteration = 5400;
    public static Counters getFactor(Player[] players, Deck deck){
        Counters counter = new Counters(players);
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
                            //players[k].tie++;
                            counter.setTie(k);
                        }
                        else{
                            //players[k].wins++;
                            counter.setWins(k);
                        }
                    }
                }
            }
        }
        counter.iteration = iteration;
        return counter;
        
    }
    public static Counters getFactor(Player[] players, Deck deck, Cards[] board){
        Counters counter = new Counters(players);
        int cardsInBoard = 0;
        for (int i = 0; i < board.length; i++) {
            if(board[i] == null){
                cardsInBoard = i;
                break;
            }
        }
        for (int i = 0; i < iteration; i++) {
            Player[] tmpPlayers = players;
            Deck tmp = new Deck(deck);
            tmp.shuffleDeck(); 
            Cards[] boardTmp = new Cards[5];
            for (int j = cardsInBoard; j < board.length; j++){
                board[j] = tmp.IssueCard();
            }
            for (int j = 0; j < players.length; j++) {
                tmpPlayers[j].setCombinationPover(Sequence.CheckSequence(tmpPlayers[j].getPocketCards(), board));
            }
            Player[] winners = Sequence.getWinner(players);
            for (int j = 0; j < winners.length; j++) {
                for (int k = 0; k < players.length; k++) {
                    if(winners[j] == players[k]){
                        if(winners.length > 1){
                            //players[k].tie++;
                            counter.setTie(k);
                        }
                        else{
                            //players[k].wins++;
                            counter.setWins(k);
                        }
                    }
                }
            }
        }
        counter.iteration = iteration;
        return counter;
    }
}
