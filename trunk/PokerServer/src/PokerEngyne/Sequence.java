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
import pokerserver.DBTools;
import pokerserver.Player;

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
            result = 17;
            return result;
        }
        
        if(isQuards(allCard)){
            result = 16;
            return result;
        }
        
        if(isFullHouse(allCard)){
            result = 15;
            return result;
        }
        
        if(isFlush(allCard)!= null){
            result = 14;
            return result;
        }
        
        if(isStraight(allCard)){
            result = 13;
            return result;
        }
        
        if(isSet(allCard)){
            result = 12;
            return result;
        }
        if(isTwoPair(allCard)){
            result = 11;
            return result;
        }
        if(isOnePair(allCard)){
            result = 10;
            return result;
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
    private static boolean isTwoPair(Cards[] allCard){
        for(int i = 0; i < allCard.length - 1; i++ ){
           if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                for(int j = i+1; j < allCard.length - 1; j++){
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId()){
                        return true; 
                    }
                }
            } 
        }
        return false;
    }
    private static boolean isSet(Cards[] allCard){
        for(int i = 0; i < allCard.length - 2; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() && 
                   allCard[i].getDignitysId() == allCard[i+2].getDignitysId() ){
                return true; 
            }
        }
        return false;
    }
    private static boolean isStraight(Cards[] allCard){
        int count = 0;
        for(int i = 0; i < allCard.length - 1; i++){
            if((allCard[i].getDignitysId() - allCard[i+1].getDignitysId()) == -1){
                count++;
                continue;
            }
            count = 0;
        }
        if(count >= 4){
            return true;
        }
        return false;
    }                                             
    private static Cards[] isFlush(Cards[] allCard){
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                return tmp.toArray(new Cards[0]);
            }
        }
        return null;
    }
    private static boolean isFullHouse(Cards[] allCard){
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
    private static boolean isQuards(Cards[] allCard){
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
    private static boolean isStraightFlush(Cards[] allCard){
        /*int index = 0;
        for(int i = 0; i < allCard.length - 1; i++){
            if((allCard[i].getDignitysId() - allCard[i+1].getDignitysId() == -1) &&
                    (allCard[i].getSuitsId() == allCard[i+1].getSuitsId())){
                index++;
            }
        }
        if(index == 4){
            return true;
        }*/
           
        Cards[] tmp = isFlush(allCard);
        if(tmp != null){
            if(isStraight(tmp)){
                return true;
            }
        }
        
        return false;
    }
    private static boolean isRoyalFlush(Cards[] allCard){
        
        return false;
    }
    
    public static Player WhoWin(Player[] players){
        return players[0];
    }
    
    public static String PrintCard(Cards card){
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
        return out;
    }
}
