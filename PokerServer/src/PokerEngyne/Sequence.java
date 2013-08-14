/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import DataBaseClasses.Cards;
import DataBaseClasses.Dignity;
import DataBaseClasses.Suits;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import pokerserver.DBTools;

/**
 *
 * @author Андрей
 */
public class Sequence {
    public static int CheckSequence(Cards[] pocketCard, Cards[] board){
        Cards [] allCard;
        if((board == null) || (board.length == 0)){
            allCard = new Cards[pocketCard.length];
            System.arraycopy(pocketCard, 0, allCard, 0, pocketCard.length);
        }
        else{
            allCard = new Cards[pocketCard.length+board.length];
            System.arraycopy(pocketCard, 0, allCard, 0, pocketCard.length);
            System.arraycopy(board, 0, allCard, pocketCard.length, board.length);
        }
        Arrays.sort(allCard);
        int result = -1;
        
        if(isStraightFlush(allCard)){
            
        }
        
        if(isQuards(allCard)){
            
        }
        
        if(isFullHouse(allCard)){
            
        }
        
        if(isFlush(allCard)){
            
        }
        
        if(isStraight(allCard)){
            
        }
        
        if(isSet(allCard)){
            
        }
        if(isTwoPair(allCard)){
            
        }
        if(isOnePair(allCard)){
            
        }
        if(isHighCard(allCard)){
            
        }
        
        return result;
    } 
    private static boolean isHighCard(Cards[] allCard){
        return false;
    }
    private static boolean isOnePair(Cards[] allCard){
        for(int i=0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                return true; 
            }
        }
        return false;
    }
    public static boolean isTwoPair(Cards[] allCard){
        for(int i = 0; i < allCard.length - 1; i++ ){
           if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                for(int j = i+2; j < allCard.length - 1; j++){
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId()){
                        return true; 
                    }
                }
            } 
        }
        return false;
    }
    public static boolean isSet(Cards[] allCard){
        for(int i = 0; i < allCard.length - 2; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() && 
                   allCard[i].getDignitysId() == allCard[i+2].getDignitysId() ){
                return true; 
            }
        }
        return false;
    }
    public static boolean isStraight(Cards[] allCard){
        Cards[] clearCards = removeDuplicates(allCard);
        int stCount = 0;
        if(clearCards.length >= 5){
            for (int i = 0; i < clearCards.length - 1; i++) {
                if((clearCards[i+1].getDignitysId() - clearCards[i].getDignitysId()) == 1){
                    stCount++;
                    if(stCount >= 4 || (clearCards[0].getDignitysId() == 2 && 
                            clearCards[clearCards.length - 1].getDignitysId() == 14 && stCount == 3)){
                        return true;
                    }
                    continue;
                }
                stCount = 0;
            }
            
        }
        return false;
    }                                             
    public static boolean isFlush(Cards[] allCard){
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                return true;
            }
        }
        return false;
    }
    public static boolean isFullHouse(Cards[] allCard){
        int id = 0;
        for(int i = 0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                id = allCard[i+1].getDignitysId();
            }
        }
        for(int i = 0; i < allCard.length - 2; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() && 
                   allCard[i].getDignitysId() == allCard[i+2].getDignitysId() ){
                if(allCard[i+2].getDignitysId() != id){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isQuards(Cards[] allCard){
        int count = 0;
        for(int i = 0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                count++;
                continue;
            }
            count = 0;
        }
        if(count == 3){
            return true;
            
        }
        return false;
    }
    public static boolean isStraightFlush(Cards[] allCard){
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                if(isStraight(tmp.toArray(new Cards [0]))){
                    return true;
                }
            }
        }
        
        return false;
    }
    public static boolean isRoyalFlush(Cards[] allCard){
        
        return false;
    }
    
    public static void PrintCard(Cards card){
        Dignity dig = DBTools.getDignity(card.getDignitysId());
        Suits su = DBTools.getSuits(card.getSuitsId());
        String out = dig.getName();
        if(su.getId() == 1){
            out += "(♠)";
        }
        else if(su.getId() == 2){
            out += "(♦)";
        }
        else if(su.getId() == 3){
            out += "(♣)";
        }
        else if(su.getId() == 4){
            out += "(♥)";
        }
        System.out.println(out);
    }
    private static Cards[] removeDuplicates(Cards [] allCards){
        ArrayList<Cards> cards = new ArrayList<>(Arrays.asList(allCards));
        for (int i = 0; i < cards.size() - 1; i++) {
            if(cards.get(i).getDignitysId() == cards.get(i+1).getDignitysId()){
                cards.remove(i+1);
            }
        }
        return cards.toArray(new Cards[0]);
    }
}
