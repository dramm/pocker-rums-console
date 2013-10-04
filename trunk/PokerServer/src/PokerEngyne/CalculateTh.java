/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import DataBaseClasses.Cards;
import Enums.GameStages;
import static Enums.GameStages.Stage.FLOP;
import static Enums.GameStages.Stage.RIVER;
import static Enums.GameStages.Stage.TURN;
import pokerserver.Deck;
import pokerserver.Player;

/**
 *
 * @author Андрей
 */
public class CalculateTh implements Runnable{
    private static int iteration = 15101;
    private Counters counter;
    private boolean flag ;
    private Deck deck;
    private Player[] players;
    private Cards[] board;
    private GameStages.Stage stage;
    private boolean type;
    public CalculateTh(Player[] players, Deck deck){
        counter = new Counters(players);
        this.deck = deck;
        this.players = players;
        flag = false;
        type = true;
    }
    public CalculateTh(Player[] players, Deck deck, Cards[] board, GameStages.Stage stage){
        counter = new Counters(players);
        this.deck = deck;
        this.players = players;
        this.board = board;
        this.stage = stage;
        flag = false;
        type = false;
    }
    @Override
    public void run() {
        if(type){
            for (int i = 0; i < iteration; i++) {
                //long time = System.currentTimeMillis();
                Player[] tmpPlayers = players;
                Deck tmp = new Deck(deck);
                tmp.shuffleDeck(); 
                Cards[] boardLocal = new Cards[5];
                for (int j = 0; j < boardLocal.length; j++){
                    boardLocal[j] = tmp.IssueCard();
                }
                
                for (int j = 0; j < players.length; j++) {
                    //long time = System.currentTimeMillis();
                    tmpPlayers[j].setCombinationPover(Sequence.CheckSequence(tmpPlayers[j].getPocketCards(), boardLocal));
                    //System.out.println("Time" + (System.currentTimeMillis() - time));
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
                //System.out.println("Time" + (System.currentTimeMillis() - time));
            }
            
            counter.iteration = iteration;
            flag = true;
        }else{
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
            flag = true;
        }
        
    }

    /**
     * @return the counter
     */
    public synchronized Counters getCounter() {
        return counter;
    }

    /**
     * @return the flag
     */
    public synchronized boolean isFlag() {
        return flag;
    }
    
}
