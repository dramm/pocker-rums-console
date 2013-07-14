/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Андрей
 */
public class Deck {
    private ArrayList<Cards> cardsArray;//основная колода
    private ArrayList<Cards> retreat;//выданные карты и отбой
    public Deck(){
        cardsArray = DBTools.getCards();
        retreat = new ArrayList<>();
        Collections.shuffle(cardsArray);
    }
    public Deck(ArrayList<Cards> CardsArray){
        cardsArray = CardsArray;
        retreat = new ArrayList<>();
        Collections.shuffle(cardsArray);
    }
    public Cards IssueCard(){
        Cards temp = getCardsArray().remove(0);
        getRetreat().add(temp);
        return temp;
        
    }
    
    public ArrayList<Cards> getCardsArray() {
        return cardsArray;
    }

    public void setCardsArray(ArrayList<Cards> cardsArray) {
        this.cardsArray = cardsArray;
    }

    public ArrayList<Cards> getRetreat() {
        return retreat;
    }

    public void setRetreat(ArrayList<Cards> retreat) {
        this.retreat = retreat;
    }
}
