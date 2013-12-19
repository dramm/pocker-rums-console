/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PokerEngyne;

import DataBaseClasses.Cards;
import Enums.GameStages;
import java.util.Map;
import pokerserver.Deck;
import pokerserver.Player;

/**
 *
 * @author andrey
 */
public class CheapDistribution implements Runnable{

    private final Player[] players;
    private final Cards[] bord;
    private final Deck deck;
    private final Bets bets;
    private int cardsInBord;
    private int tableId;
    private Cards[] fakeBord;
    private GameStages.Stage stage;

    public CheapDistribution(Player[] players, Cards[] bord, Deck deck, Bets bets, GameStages.Stage stage, int tableId) {
        this.players = players;
        this.bord = bord;
        this.deck = deck;
        this.bets = bets;
        this.tableId = tableId;
        this.fakeBord = bord;
        this.stage = stage;
        switch(stage){
            case FLOP:{
                cardsInBord = 0;
                break;
            }
            case TURN:{
                cardsInBord = 3;
                break;
            }
            case RIVER:{
                cardsInBord = 4;
                break;
            }
        }
    }

    
    @Override
    public void run() {
        
        double winnSize;
        for (Player player : players) {
            player.setCombinationPover(Sequence.CheckSequence(player.getPocketCards(), bord));
        }
        Player[] winner = Sequence.getWinner(players);
        winnSize = findWinnSize(winner);
        if(winnSize == 0){
            System.out.println("Returned, winnsize 0");
            return;
        }
        System.out.println("Fake running, winn size = " + winnSize);
        int it = 0;
        for (int j = cardsInBord; j < bord.length; j++){
                it++;
        }
        System.out.println("Iterations " + it);
        for (int i = 0; i < 10000; i++) {
            Player[] tmpPlayers = players;
            Deck tmp = new Deck(deck);
            tmp.shuffleDeck();
            Cards[] boardTmp = new Cards[5];
            System.arraycopy(bord, 0, boardTmp, 0, 5);
            for (int j = cardsInBord; j < bord.length; j++){
                boardTmp[j] = tmp.IssueCard();
            }
            for (int j = 0; j < players.length; j++) {
                tmpPlayers[j].setCombinationPover(Sequence.CheckSequence(tmpPlayers[j].getPocketCards(), boardTmp));
            }
            Player[] winnerTmp = Sequence.getWinner(tmpPlayers);
            if(findWinnSize(winnerTmp) < winnSize){
                System.out.println("Find new winnSize " + findWinnSize(winnerTmp));
                //fakeBord = boardTmp;
                fakeBord = new Cards[5];
                System.arraycopy(boardTmp, 0, fakeBord, 0, 5);
                winnSize = findWinnSize(winnerTmp);
                if(winnSize == 0){
                    break;
                }
            }
        }
        System.out.println("End " + tableId);
    }
    
    private double findWinnSize(Player[] winner){
        double winnSize = 0;
        for (Bet bet : bets.getBets()){
            Map<String, Map<Integer, Double>> tableData = bet.getTableData();
            for (Map.Entry<String, Map<Integer, Double>> entry : tableData.entrySet()) {
                String string = entry.getKey();
                Map<Integer, Double> map = entry.getValue();
                if(string.equals("Table" + tableId)){
                    for (Player player : winner) {
                        if(map.get(player.getPlayerId()) != null){
                            winnSize += (bet.getBetSize() * map.get(player.getPlayerId()));
                        }
                    }
                }
                
            }
        }
        return winnSize;
    }

    /**
     * @return the fakeBord
     */
    public synchronized Cards[] getFakeBord() {
        return fakeBord;
    }
    
}
