/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;

/**
 *
 * @author Андрей
 */
public class Player {
    private Cards[] pocketCards;
    private int combinationPover;
    private Cards kicker;
    public Player(){
        pocketCards = new Cards[2];
        combinationPover = 0;
        kicker = new Cards();
    }
    public Player(Cards[] pocketCards){
        this.pocketCards = pocketCards;
        combinationPover = 0;
        kicker = new Cards();
    }

    public Cards[] getPocketCards() {
        return pocketCards;
    }

    public void setPocketCards(Cards[] pocketCards) {
        this.pocketCards = pocketCards;
    }
    
    public Cards getFirstCard(){
        return pocketCards[0];
    }
    public Cards getSecondCard(){
        return pocketCards[1];
    }

    public int getCombinationPover() {
        return combinationPover;
    }

    public void setCombinationPover(int combinationPover) {
        this.combinationPover = combinationPover;
    }

    public Cards getKicker() {
        return kicker;
    }

    public void setKicker(Cards kicker) {
        this.kicker = kicker;
    }
    
    public int[] getHandsCardsId(){
        int[] result = new int[2];
        result[0] = getFirstCard().getId();
        result[1] = getSecondCard().getId();
        return result;
    }
    
}
