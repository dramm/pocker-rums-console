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
    private static int iteration = 10000;
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
        }
        return counter;
        
    }
    public static float[] getFactor(Player[] players, Deck deck, Cards[] board, Stage stage){
        int cardInBoard = 0;
        switch (stage) {
            case FLOP:{
                cardInBoard = 2;
                break;
            }
            case TURN:{
                cardInBoard = 3;
                break;
            }
            case RIVER:{
                cardInBoard = 4;
                break;
            }
        }
        float[] result = new float[players.length];
        int[] winnerCount = new int[players.length];
        for (int i = 0; i < winnerCount.length; i++) {
            winnerCount[i] = 0;
            result[i] = 0;
        }
        for (int i = 0; i < iteration; i++) {
            Deck tmp = new Deck(deck);
            tmp.shuffleDeck();
            Cards[] cloneBoard = board;
            for (int j = cardInBoard; j < board.length; j++){
                cloneBoard[j] = tmp.IssueCard();
            }
            for (int j = 0; j < players.length; j++) {
                players[j].setCombinationPover(Sequence.CheckSequence(players[j].getPocketCards(), cloneBoard));
            }
            Player winner = Sequence.getWinner(players);
            for (int j = 0; j < players.length; j++) {
                if(players[j] == winner){
                    winnerCount[j]++;
                }
            }
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = 1f/(((float)winnerCount[i]+1)/iteration);
            if(result[i]>50){
                result[i] = 50;
            }
        }
        return result;
    }
}
