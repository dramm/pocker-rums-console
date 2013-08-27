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
    private WinnerData combinationPover;
    private Cards kicker;
    public int wins;
    public int loses;
    public int tie;
    public Player(){
        pocketCards = new Cards[2];
        combinationPover = null;
        kicker = new Cards();
    }
    public Player(Cards[] pocketCards){
        this.pocketCards = pocketCards;
        combinationPover = null;
        kicker = new Cards();
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

    public WinnerData getCombinationPover() {
        return combinationPover;
    }

    public void setCombinationPover(WinnerData combinationPover) {
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
