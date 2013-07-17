/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import DataBaseClasses.Cards;
import java.util.Arrays;

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
        
        if(isFlush(allCard)){
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
            }
        }
        if(count >= 5){
            return true;
        }
        return false;
    }
    private static boolean isFlush(Cards[] allCard){
        for(int i = 1; i <= 4; i++){
            int count = 0;
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    count++;
                }
            }
            if(count >= 5){
                return true;
            }
        }
        return false;
    }
    private static boolean isFullHouse(Cards[] allCard){
        return false;
    }
    private static boolean isQuards(Cards[] allCard){
        return false;
    }
    private static boolean isRtraightFlush(Cards[] allCard){
        return false;
    }
    private static boolean isRoyalFlush(Cards[] allCard){
        
        return false;
    }
}
