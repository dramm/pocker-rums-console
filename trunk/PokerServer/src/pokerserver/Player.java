/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import PokerEngyne.WinnerData;

/**
 *
 * @author Андрей
 */
public class Player {
    private Cards[] pocketCards;
    private int handId;
    private int playerId;
    private int cicker;
    private float pover;
    private float factor;
    public Player(int id){
        pocketCards = new Cards[2];
        playerId = id;
    }
    public Player(Cards[] pocketCards, int id){
        this.pocketCards = pocketCards;
        playerId = id;
    }

    public Cards[] getPocketCards() {
        return pocketCards;
    }

    public void setPocketCards(Cards[] pocketCards) {
        this.pocketCards = pocketCards;
    }
    
    public void setPocketCards(Cards first, Cards second) {
        this.pocketCards[0] = first;
        this.pocketCards[1] = second;
    }
    
    public int getFirstPocketCardId(){
        return pocketCards[0].getId();
    }
    
    public int getSecondPocketCardId(){
        return pocketCards[1].getId();
    }
    
    public Cards getFirstCard(){
        return pocketCards[0];
    }
    public Cards getSecondCard(){
        return pocketCards[1];
    }

    public synchronized float getCombinationPover() {
        return pover;
    }

    public synchronized void setCombinationPover(float combinationPover) {
        this.pover = combinationPover;
    }
    
    public int[] getHandsCardsId(){
        int[] result = new int[2];
        result[0] = getFirstCard().getId();
        result[1] = getSecondCard().getId();
        return result;
    }

    public int getPlayerId() {
        return playerId;
    }

    /**
     * @return the cicker
     */
    public int getCicker() {
        return cicker;
    }

    /**
     * @param cicker the cicker to set
     */
    public void setCicker(int cicker) {
        this.cicker = cicker;
    }

    /**
     * @return the factor
     */
    public float getFactor() {
        return factor;
    }

    /**
     * @param factor the factor to set
     */
    public void setFactor(float factor) {
        this.factor = factor;
    }

    /**
     * @return the handId
     */
    public int getHandId() {
        return handId;
    }

    /**
     * @param handId the handId to set
     */
    public void setHandId(int handId) {
        this.handId = handId;
    }
}
