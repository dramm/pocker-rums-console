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
    private boolean fold;
    public Player(){
        pocketCards = new Cards[2];
        fold = false;
    }
    public Player(Cards[] pocketCards){
        this.pocketCards = pocketCards;
        fold = false;
    }

    public Cards[] getPocketCards() {
        return pocketCards;
    }

    public void setPocketCards(Cards[] pocketCards) {
        this.pocketCards = pocketCards;
    }

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }
    public Cards getFirstCard(){
        return pocketCards[0];
    }
    public Cards getSecondCard(){
        return pocketCards[1];
    }
    
}
