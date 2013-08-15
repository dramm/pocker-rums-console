/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class Deck {
    private ArrayList<Cards> cardsArray;//основная колода
    public Deck(){
        cardsArray = new ArrayList<>(DBTools.getCards());
        //cardsArray = DBTools.getCards();
        Collections.shuffle(cardsArray);      
    }
    public Deck(ArrayList<Cards> CardsArray){
        cardsArray = CardsArray;
        Collections.shuffle(cardsArray);
    }
    public Cards IssueCard(){
        return cardsArray.remove(0);        
    }
    
    public ArrayList<Cards> getCardsArray() {
        return cardsArray;
    }

    public void setCardsArray(ArrayList<Cards> cardsArray) {
        this.cardsArray = cardsArray;
    }
}
