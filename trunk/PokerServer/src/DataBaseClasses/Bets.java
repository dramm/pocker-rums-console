/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class Bets {
    private int handInStageId;
    private int playerId;
    private double value;
    public Bets(){
        handInStageId = 0;
        playerId = 0;
        value = 0;
    }
    public Bets(int handStage, int playerId, double value){
        handInStageId = handStage;
        this.playerId = playerId;
        this.value = value;
    }
    public void setHandStageId(int handStage){
        handInStageId=handStage;
    }
    public int getHandStageId(){
        return this.handInStageId;
    }
    public void setPlayerId(int playerId){
        this.playerId = playerId;
    }
    public int getPlayerId(){
        return this.playerId;
    }
    public void setValue(double value){
        this.value = value;
    }
    public double getValue(){
        return this.value;
    }
}
