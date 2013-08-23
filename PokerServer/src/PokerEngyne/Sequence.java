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
        if(isRoyalFlush(allCard) != -1){
            return 126;
        }
        int result = isStraightFlush(allCard);
        if(result != -1){
            return 112 + result;
        }
        result = isQuads(allCard);
        if (result != -1) {
            return 98 + result;
        }
        result = isFullHouse(allCard);
        if (result != -1) {
            return 84 + result;
        }
        result = isFlush(allCard);
        if (result != -1) {
            return 70 + result;
        }
        result = isStraight(allCard);
        if (result != -1) {
            return 56 + result;
        }
        result = isSet(allCard);
        if (result != -1) {
            return 42 + result;
        }
        result = isTwoPair(allCard);
        if (result != -1) {
            return 28 + result;
        }
        result = isOnePair(allCard);
        if (result != -1) {
            return 14 + result;
        }
        return isHighCard(allCard);
    } 
    private static int isHighCard(Cards[] allCard){
        return allCard[allCard.length-1].getDignitysId();
    }
    public static int isOnePair(Cards[] allCard){
        for(int i=0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                return allCard[i].getDignitysId(); 
            }
        }
        return -1;
    }
    public static int isTwoPair(Cards[] allCard){
        for(int i = 0; i < allCard.length - 1; i++ ){
           if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                for(int j = i+2; j < allCard.length - 1; j++){
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId()){
                        return allCard[j].getDignitysId(); 
                    }
                }
            } 
        }
        return -1;
    }
    public static int isSet(Cards[] allCard){
        for(int i = 0; i < allCard.length - 2; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() && 
                   allCard[i].getDignitysId() == allCard[i+2].getDignitysId() ){
                return allCard[i].getDignitysId(); 
            }
        }
        return -1;
    }
    public static int isStraight(Cards[] allCard){
        Cards[] clearCards = removeDuplicates(allCard);
        int stCount = 0;
        if(clearCards.length >= 5){
            for (int i = 0; i < clearCards.length - 1; i++) {
                if((clearCards[i+1].getDignitysId() - clearCards[i].getDignitysId()) == 1){
                    stCount++;
                    if(stCount >= 4 || (clearCards[0].getDignitysId() == 2 && 
                            clearCards[clearCards.length - 1].getDignitysId() == 14 && 
                            stCount == 3 && 
                            clearCards[0].getSuitsId() == clearCards[clearCards.length - 1].getSuitsId())){
                        return clearCards[i+1].getDignitysId();
                    }
                    continue;
                }
                stCount = 0;
            }
            
        }
        return -1;
    }                                             
    public static int isFlush(Cards[] allCard){
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                return tmp.get(tmp.size()-1).getDignitysId();
            }
        }
        return -1;
    }
    public static int isFullHouse(Cards[] allCard){
        for (int i = 0; i < allCard.length-2; i++) {
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() &&
                    allCard[i].getDignitysId() == allCard[i+2].getDignitysId()){
                for (int j = 0; j < allCard.length-1; j++) {
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId() &&
                            allCard[i].getDignitysId() != allCard[j].getDignitysId()){
                        return allCard[i].getDignitysId();
                    }
                }
            }
        }
        return -1;
    }
    public static int isQuads(Cards[] allCard){
        int count = 0;
        for(int i = 0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                count++;
                if(count == 3){
                    return allCard[i].getDignitysId();

                }
                continue;
            }
            count = 0;
        }
        return -1;
    }
    public static int isStraightFlush(Cards[] allCard){
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                if(isStraight(tmp.toArray(new Cards [0])) != -1){
                    return tmp.get(tmp.size()-1).getDignitysId();
                }
            }
        }
        
        return -1;
    }
    public static int isRoyalFlush(Cards[] allCard){
        for (int i = 1; i <= 4; i++) {
            if(isFlush(allCard) == 14 && isStraight(allCard) == 14){
                return 14;
            }
        }
        return -1;
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
    public static Player getWinner(Player[] players){
        Player winner = players[0];
        for (int i = 1; i < players.length; i++) {
            if(players[i].getCombinationPover() > winner.getCombinationPover()){
                winner = players[i];
            }
        }
        return winner;
    }
}
