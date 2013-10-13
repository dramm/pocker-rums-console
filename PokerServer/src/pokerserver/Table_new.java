/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import Enums.GameStages;
import PokerEngyne.CalculateTh;
import PokerEngyne.Counters;
import PokerEngyne.Sequence;


/**
 *
 * @author Андрей
 */
public class Table_new {
    private Deck deck;
    public Player[] players;
    public Player[] winners;
    public Cards[] bord;
    public int stageId;
    private int tableId;
    private int stageIdDb;
    private int[] winnerCombination;
    private int playersCount;
    private Counters factor;
    private CalculateTh ct;
    public Table_new(int playerCount){
        this.playersCount = playerCount;
        deck = new Deck();
        players = new Player[playerCount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i);
        }
        bord = new Cards[5];
    }
    public void startingStage(){
        deck = new Deck();
        players = new Player[playersCount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i);
        }
        bord = new Cards[5];
    }
    public void preflopStage(){
        for (int i = 0; i < getPlayers().length; i++) {
            getPlayers()[i].setPocketCards(getDeck().IssueCard(), getDeck().IssueCard());
        }
    }
    public void flopStage(){
        for (int i = 0; i < 3; i++) {
            bord[i] = getDeck().IssueCard();
        }
    }
    public void turnStage(){
        bord[3] = getDeck().IssueCard();
    }
    public void riverStage(){
        bord[4] = getDeck().IssueCard();
    }
    public void showdownStage(){
        for (int i = 0; i < players.length; i++) {
            players[i].setCombinationPover(Sequence.CheckSequence(players[i].getPocketCards(), bord));
            
            
        }
        winners = Sequence.getWinner(players);
    }
    
    public void generateGame(){
        startingStage();
        preflopStage();
        flopStage();
        turnStage();
        riverStage();
        showdownStage();
    }

    public Player[] getPlayers() {
        return players;
    }

    public Cards[] getBord() {
        return bord;
    }

    public Deck getDeck() {
        return deck;
    }

    public int[] getWinnerCombination() {
        return winnerCombination;
    }

    public void setWinnerCombination(int[] winnerCombination) {
        this.winnerCombination = winnerCombination;
    }

    /**
     * @return the winners
     */
    public Player[] getWinners() {
        return winners;
    }

    /**
     * @return the factor
     */
    public synchronized Counters getFactor() {
        return factor;
    }
    
    public synchronized boolean isFlag(){
        return ct.isFlag();
    }
    public synchronized void setFactor(){
        factor = ct.getCounter();
    }
    public void runMontecarlo(){
        ct = new CalculateTh(players, deck);
        Thread thread = new Thread(ct);
        thread.start();
    }
    
    public void runMontecarlo(GameStages.Stage stage){
        ct = new CalculateTh(players, deck, bord, stage);
        Thread thread = new Thread(ct);
        thread.start();
    }

    /**
     * @return the tableId
     */
    public int getTableId() {
        return tableId;
    }

    /**
     * @param tableId the tableId to set
     */
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    /**
     * @return the stageIdDb
     */
    public int getStageIdDb() {
        return stageIdDb;
    }

    /**
     * @param stageIdDb the stageIdDb to set
     */
    public void setStageIdDb(int stageIdDb) {
        this.stageIdDb = stageIdDb;
    }
}
