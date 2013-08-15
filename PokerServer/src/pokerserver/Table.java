/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import Enums.GameStages.Stage;
import PokerEngyne.Sequence;
import threads.Bridge;

/**
 *
 * @author Андрей
 */
public class Table {
    private String name;
    private Deck deck;
    private Player[] players;
    private Cards[] bord;
    private Stage stage;
    private int gameId;
    
    
    public Table(int players, String tableName){
        stage = Stage.STARTING;
        name = tableName;
        this.players = new Player[players];
        this.bord = new Cards[5];
        this.deck = new Deck();
        for(int i=0; i<players; i++){
            this.players[i] = new Player();
        }
        DBTools.setGame();
        gameId = DBTools.getLastGameId();
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
    }
    
    private void Starting(){
        stage = Stage.STARTING;
        this.bord = new Cards[5];
        this.deck = new Deck();
        for(int i=0; i<players.length; i++){
            this.players[i] = new Player();
        }
        DBTools.setGame();
        gameId = DBTools.getLastGameId();
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
    }
    
    private void PreFlop(){
        stage = Stage.PREFLOP;
        for(int i = 0;  i < players.length; i++){
            Cards[] tmp = new Cards[2];
            tmp[0] = deck.IssueCard();
            tmp[1] = deck.IssueCard();
            players[i].setPocketCards(tmp);
            DBTools.setHand(tmp);
        }
        DBTools.setGameStage(stage.getStage(), gameId);
        int [][] handsCardsId = new int[players.length][];
        for (int i = 0; i < handsCardsId.length; i++) {
            handsCardsId[i] = players[i].getHandsCardsId();
        }
        Bridge.data.setHandCards(handsCardsId);
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
    }
    
    private void Flop(){  
        stage = Stage.FLOP;
        DBTools.setGameStage(stage.getStage(), gameId);
        for(int i=0; i<3; i++){
            bord[i] = deck.IssueCard();
            DBTools.setDistribution(bord[i], gameId, stage.getStage());
        }
        int[] flopCards = new int[3];
        for (int i = 0; i < 3; i++) {
            flopCards[i] = bord[i].getId();
        }
        Bridge.data.setBoard(flopCards);
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
    }
    private void Turn(){
        stage = Stage.TURN;
        DBTools.setGameStage(stage.getStage(), gameId);
        bord[3] = deck.IssueCard();
        int[] flopCards = new int[4];
        for (int i = 0; i < 4; i++) {
            flopCards[i] = bord[i].getId();
        }
        DBTools.setDistribution(bord[3], gameId, stage.getStage());
        Bridge.data.setBoard(flopCards);
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
        
    }
    private void River(){
        stage = Stage.RIVER;
        DBTools.setGameStage(stage.getStage(), gameId);
        bord[4] = deck.IssueCard();
        int[] flopCards = new int[5];
        for (int i = 0; i < 5; i++) {
            flopCards[i] = bord[i].getId();
        }
        DBTools.setDistribution(bord[4], gameId, stage.getStage());
        Bridge.data.setBoard(flopCards);
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
       
    }
    
    private void Showdown() {
        stage = Stage.SHOWDOWN;
        Bridge.data.setStage(stage);
        Bridge.data.setTableName(name);
        Bridge.data.setFlag(true);
    }
    
    public void nextStage(){
        switch(stage){
            case STARTING:{
                PreFlop();
                break;
            }
            case PREFLOP:{
                Flop();
                break;
            }
            case FLOP:{
                Turn();
                break;
            }
            case TURN:{
                River();
                break;
            }
            case RIVER:
                Showdown();
                break;
            case SHOWDOWN:{
                Starting();
                break;
            }
        }
    }
    
    public void getInfo(){
        System.out.println("Table : " + name);
        System.out.println("Game stage : " + stage.toString());
        if(stage.getStage()>0){
            for (int i = 0; i < players.length; i++) {
                System.out.println("Player " + (i + 1) + " hand cards: ");
                Sequence.PrintCard(players[i].getFirstCard());
                Sequence.PrintCard(players[i].getSecondCard());

            }
            for (int i = 0; i < bord.length; i++) {
                System.out.println("Bord card " + (i + 1));
                if(bord[i] != null){
                    Sequence.PrintCard(bord[i]);
                }
                else{
                    System.out.println("In the deck");
                }
            }
        }
    }
    
    public void CheckCombination(){
        for(int i = 0; i < players.length; i++){
            players[i].setCombinationPover(Sequence.CheckSequence(players[i].getPocketCards(), bord));
        }  
    }  
    public Stage getStage() {
        return stage;
    }
}
