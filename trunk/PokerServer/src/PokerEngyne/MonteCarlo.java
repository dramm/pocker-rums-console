/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import DataBaseClasses.Cards;
import Enums.GameStages.Stage;
import pokerserver.Deck;
import pokerserver.Player;

/**
 *
 * @author Андрей
 */
public class MonteCarlo {
    private static int iteration = 1000;

    public static Counters getFactor(Player[] players, Deck deck){
        Counters counter = new Counters(players);
        for (int i = 0; i < iteration; i++) {
            long time = System.currentTimeMillis();
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
                            counter.setTie(k);
                            counter.setWins(k);
                        }
                        else{
                            counter.setWins(k);
                        }
                    }
                }
            }
            time = (System.currentTimeMillis() - time);
            System.out.println("Iteration time" + time);
        }
        counter.iteration = iteration;
        return counter;
        
    }
    public static Counters getFactor(Player[] players, Deck deck, Cards[] board, Stage stage){
        Counters counter = new Counters(players);
        int cardsInBoard = 0;
        switch (stage) {
            case FLOP:{
                cardsInBoard = 3;
                break;
            }
            case TURN:{
                cardsInBoard = 4;
                break;
            }
            case RIVER:{
                cardsInBoard = 5;
                break;
            }
        }
        
        for (int i = 0; i < iteration; i++) {
            Player[] tmpPlayers = players;
            Deck tmp = new Deck(deck);
            tmp.shuffleDeck(); 
            Cards[] boardTmp = new Cards[5];
            System.arraycopy(board, 0, boardTmp, 0, cardsInBoard);
            for (int j = cardsInBoard; j < board.length; j++){
                boardTmp[j] = tmp.IssueCard();
            }
            for (int j = 0; j < players.length; j++) {
                tmpPlayers[j].setCombinationPover(Sequence.CheckSequence(tmpPlayers[j].getPocketCards(), boardTmp));
            }
            Player[] winners = Sequence.getWinner(players);
            for (int j = 0; j < winners.length; j++) {
                for (int k = 0; k < players.length; k++) {
                    if(winners[j] == players[k]){
                        if(winners.length > 1){
                            counter.setTie(k);
                            counter.setWins(k);
                        }
                        else{
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
