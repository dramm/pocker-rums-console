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
        if((board==null)||(board.length==0)){
            allCard = new Cards[pocketCard.length];
            System.arraycopy(pocketCard, 0, allCard, 0, pocketCard.length);
        }
        else{
            allCard = new Cards[pocketCard.length+board.length];
            System.arraycopy(pocketCard, 0, allCard, 0, pocketCard.length);
            System.arraycopy(board, 0, allCard, pocketCard.length, board.length);
        }
        Arrays.sort(allCard);
        if(isOnePair(allCard)){
            return 10;
        }
        if(isSet(allCard)){
            return 11;
        }
        return -1;
    } 
    private static boolean isHighCard(Cards[] allCard){
        return false;
    }
    private static boolean isOnePair(Cards[] allCard){
        int pair = 0;
        for(int i=0; i<allCard.length-1; i++){
            if(allCard[i].getDignitysId()==allCard[i+1].getDignitysId()){
                pair++;
            }
        }
        if(pair==1){
            return true;
        }
        return false;
    }
    private static boolean isTwoPair(Cards[] allCard){
        return false;
    }
    private static boolean isSet(Cards[] allCard){
        int pair = 0;
        for(int i=0; i<allCard.length-1; i++){
            if(allCard[i].getDignitysId()==allCard[i+1].getDignitysId()){
                pair++;
            }
        }
        if(pair==2){
            return true;
        }
        return false;
    }
    private static boolean isStraight(Cards[] allCard){
        return false;
    }
    private static boolean isFlush(Cards[] allCard){
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
