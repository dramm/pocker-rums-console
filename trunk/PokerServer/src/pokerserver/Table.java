/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.Sequence;
import java.util.UUID;

/**
 *
 * @author Андрей
 */
public class Table {
    private String name;
    private Deck deck;
    private Player[] players;
    private Cards[] bord;
    private int stage;
    private UUID uuid;
    
    public Table(int players, String tableName){
        uuid = UUID.randomUUID();
        stage = 0;
        name = tableName;
        this.players = new Player[players];
        this.bord = new Cards[5];
        this.deck = new Deck();
        for(int i=0; i<players; i++){
            this.players[i] = new Player();
        }
        DBTools.setGame(uuid.toString());
    }
    
    private void Reset(){
        uuid = UUID.randomUUID();
        stage = 0;
        this.bord = new Cards[5];
        this.deck = new Deck();
        for(int i=0; i<players.length; i++){
            this.players[i] = new Player();
        }
        DBTools.setGame(uuid.toString());
    }
    
    public void PreFlop(){
        stage = 1;
        for(int i = 0;  i < players.length; i++){
            Cards[] tmp = new Cards[2];
            tmp[0] = deck.IssueCard();
            tmp[1] = deck.IssueCard();
            players[i].setPocketCards(tmp);
            DBTools.setHand(tmp);
        }
        DBTools.setGameStage(stage, uuid.toString());
    }
    private void Flop(){
        stage = 2;
        DBTools.setGameStage(stage, uuid.toString());
        for(int i=0; i<3; i++){
            bord[i] = deck.IssueCard();
            DBTools.setDistribution(bord[i], uuid.toString(), stage);
        }
        
    }
    private void Turn(){
        stage = 3;
        DBTools.setGameStage(stage, uuid.toString());
        bord[3] = deck.IssueCard();
        DBTools.setDistribution(bord[3], uuid.toString(), stage);
        
    }
    private void River(){
        stage = 4;
        DBTools.setGameStage(stage, uuid.toString());
        bord[4] = deck.IssueCard();
        DBTools.setDistribution(bord[4], uuid.toString(), stage);
       
    }
    
    private void Showdown() {
        stage = 5;
    }
    
    public void nextStage(){
        switch(stage){
            case 0:{
                PreFlop();
                break;
            }
            case 1:{
                Flop();
                break;
            }
            case 2:{
                Turn();
                break;
            }
            case 3:{
                River();
                break;
            }
            case 4:
                Showdown();
                break;
            case 5:{
                Reset();
                break;
            }
        }
    }
    
    public void getInfo(){
        System.out.println("Table : " + name);
        System.out.println("Game stage : " + getStage());
        if(stage > 0){
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
    
    private String getStage(){
        switch(stage){
            case 1:{
                return "Preflop";
            }
            case 2:{
                return "Flop";
            }
            case 3:{
                return "Turn";
            }
            case 4:{
                return "River";
            }
            case 5:{
                return "Showdown";
            }                
        }
        return "Start game";
    }
    
    public void CheckCombination(){
        for(int i = 0; i < players.length; i++){
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 10){
                System.out.println("Player "+i+" have one pair");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 11){
                System.out.println("Player "+i+" have two pair");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 12){
                System.out.println("Player "+i+" have set");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 13){
                System.out.println("Player "+i+" have streight");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 14){
                System.out.println("Player "+i+" have flush");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 15){
                System.out.println("Player "+i+" have full house");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 16){
                System.out.println("Player "+i+" have kare");
            }
            if(Sequence.CheckSequence(players[i].getPocketCards(), bord)== 17){
                System.out.println("Player "+i+" have streight flush");
            }
        }
        
    }   
}
