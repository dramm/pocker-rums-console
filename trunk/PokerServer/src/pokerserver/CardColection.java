/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import java.util.ArrayList;

/**
 *
 * @author Андрей
 */
public class CardColection {
    public static ArrayList<Cards> mainDeck = new ArrayList<>();
    
    public static void getCards(){
        mainDeck = DBTools.getCards();
        mainDeck.add(0, null);
    }
}
