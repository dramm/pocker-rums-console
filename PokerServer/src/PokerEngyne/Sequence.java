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
    public static double CheckSequence(Cards[] pocketCard, Cards[] board){
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
        if(isRoyalFlush(allCard, pocketCard) != -1){
            return isRoyalFlush(allCard, pocketCard);
        }
        double result = isStraightFlush(allCard, pocketCard);
        if(result != -1){
            return result;
        }
        result = isQuads(allCard);
        if (result != -1) {
            return result;
        }
        result = isFullHouse(allCard);
        if (result != -1) {
            return result;
        }
        result = isFlush(allCard);
        if (result != -1) {
            return result;
        }
        result = isStraight(allCard, pocketCard);
        if (result != -1) {
            return result;
        }
        result = isSet(allCard, pocketCard);
        if (result != -1) {
            return result;
        }
        result = isTwoPair(allCard, pocketCard);
        if (result != -1) {
            return result;
        }
        result = isOnePair(allCard, pocketCard);
        if (result != -1) {
            return result;
        }
        
        return isHighCard(allCard);
    } 
    public static double isHighCard(Cards[] allCard){
        double result;
        result = (allCard[allCard.length-1].getDignitysId());
        return result;
    }
    
    public static double isOnePair(Cards[] allCard, Cards[] poketCards){
        double result;
        for(int i=0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                result = allCard[i].getDignitysId();
                result += 1400;
                Cards[] combination = {allCard[i], allCard[i+1]};
                result += (getCiker(combination, poketCards) / 100.0);
                return result;
             }
        }
       
        return -1;
    }
    
    public static double isTwoPair(Cards[] allCard, Cards[] poketCards){
        double result;
        int count = 0;
        for (int i = 0; i < allCard.length - 1; i++) {
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                count++;
            }
        }
        boolean flag = true;
        for(int i = 0; i < allCard.length - 1; i++ ){
           if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
               if(count > 2 && flag){
                   flag = false;
                   continue;
               }
                for(int j = i+2; j < allCard.length - 1; j++){
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId()){
                        Cards[] combination = {allCard[i], allCard[i+1], allCard[j], allCard[j+1]};
                        result = allCard[j].getDignitysId();
                        result += 2800;
                        result += allCard[i].getDignitysId() / 100.0;
                        result += getCiker(combination, poketCards) / 10000.0;
                        return result;
                    }
                }
            } 
        }
        
        return -1;
    }
    
    public static double isSet(Cards[] allCard, Cards[] poketCards){
        double result;
        for(int i = 0; i < allCard.length - 2; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() && 
                   allCard[i].getDignitysId() == allCard[i+2].getDignitysId() ){
                Cards[] combination = {allCard[i], allCard[i+1], allCard[i+2]};
                result = allCard[i].getDignitysId();
                result += 4200;
                result += getCiker(combination, poketCards) / 100.0;
                return result;
            }
        }
        
        return -1;
    }
    
    public static double isStraight(Cards[] allCard, Cards[] poketCards){
        
        Cards[] clearCards = removeDuplicates(allCard);
        double result;
        int stCount = 0;
        if(clearCards.length >= 5){
            for (int i = 0; i < clearCards.length - 1; i++) {
                if((clearCards[i+1].getDignitysId() - clearCards[i].getDignitysId()) == 1){
                    stCount++;
                    if((clearCards[0].getDignitysId() == 2 && clearCards[1].getDignitysId() == 3 && 
                            clearCards[2].getDignitysId() == 4 && clearCards[3].getDignitysId() == 5 &&
                            clearCards[clearCards.length - 1].getDignitysId() == 14)){
                        ArrayList<Cards> id = new ArrayList<>();
                        id.add(clearCards[0]);
                        id.add(clearCards[1]);
                        id.add(clearCards[2]);
                        id.add(clearCards[3]);
                        id.add(clearCards[clearCards.length - 1]);
                        result = clearCards[i+1].getDignitysId();
                        result += 5600;
                        result += getCiker(id.toArray(new Cards[0]), poketCards) / 100.0;
                        return result;
                    }
                    if(stCount >= 4 ){
                        ArrayList<Cards> id = new ArrayList<>();
                        int lastCard = i + 1;
                        for (int j = lastCard; j >  lastCard - 5; j--) {
                            id.add(clearCards[j]);
                        }
                        result = clearCards[i+1].getDignitysId();
                        result += 5600;
                        result += getCiker(id.toArray(new Cards[0]), poketCards) / 100.0;
                        return result;
                    }
                    continue;
                }
                stCount = 0;
            }
            
        }
        
        return -1;
    }    
    
    public static double isFlush(Cards[] allCard){
        double result = 0;
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                result = tmp.get(tmp.size()-1).getDignitysId();
                result += 7000;
                double divisor = 100;
                for (int j = tmp.size() - 2; j >= 0; j--) {
                    double res = tmp.get(j).getDignitysId() / divisor;
                    result += res;
                    divisor *= 100;
                }
                //System.out.println(result1);
                //result = (float)result1;
                return result;
            }
        }
        
        return -1;
    }
    
    public static double isFullHouse(Cards[] allCard){
        double result;
        int tripsDig = 0;
        int pairDig = 0;
        for (int i = 0; i < allCard.length - 2; i++) {
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() &&
                    allCard[i].getDignitysId() == allCard[i+2].getDignitysId()){
                if(allCard[i].getDignitysId() > tripsDig){
                    tripsDig = allCard[i].getDignitysId();
                }
            }
        }
        for (int i = 0; i < allCard.length - 1; i++) {
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() &&
                    allCard[i].getDignitysId() != tripsDig){
                if(allCard[i].getDignitysId() > pairDig){
                    pairDig = allCard[i].getDignitysId();
                }
            }
        }
        if(tripsDig != 0 && pairDig != 0){ 
            result = tripsDig;
            result += 8400;
            result += pairDig / 100.0;
            return result;
        }
        
        return -1;
    }
    
    public static double isQuads(Cards[] allCard){
        double result;
        int count = 0;
        for(int i = 0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                count++;
                if(count == 3){
                    result = allCard[i].getDignitysId();
                    result += 9800;
                    return result;

                }
                continue;
            }
            count = 0;
        }
       
        return -1;
    }
    
    public static double isStraightFlush(Cards[] allCard, Cards[] poketCards){
        double result;
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                if(isStraight(tmp.toArray(new Cards [0]), poketCards) != -1){
                    result = tmp.get(tmp.size()-1).getDignitysId();
                    result += 11200;
                    return result;
                }
            }
        }
        
        return -1;
    }
    
    public static double isRoyalFlush(Cards[] allCard, Cards[] poketCards){
        
        for (int i = 1; i <= 4; i++) {
            if(isFlush(allCard) != -1 && isStraight(allCard, poketCards) != -1){
                if(isFlush(allCard) == 14f && isStraight(allCard, poketCards) == 14f){
                    
                    return isStraight(allCard, poketCards) + 10000;
                }
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
    
    public static int[] getBoardCardsIdArray(Cards[] board){
        int[] result = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            result[i] = board[i].getId();
        }
        return result;
    }
    
    public static Player[] getWinner(Player[] players){
        ArrayList<Player> winners = new ArrayList<>();
        double power = getMaxCombinationPower(players);
        for (int i = 0; i < players.length; i++) {
            if(players[i].getCombinationPover() == power){
                winners.add(players[i]);
            }
        }
        return winners.toArray(new Player[0]);
    }
 
    private static double getMaxCombinationPower(Player[] players){
        double result = 0;
        for (int i = 0; i < players.length; i++) {
            if(players[i].getCombinationPover() >= result){
                result = players[i].getCombinationPover();
            }
        }
        return result;
    }
    
    private static int getCiker(Cards[] combination, Cards[] poketCards){
        int result = 0;
        ArrayList<Cards> tmp = new ArrayList<>();
        for (int i = 0; i < poketCards.length; i++) {
            boolean flag = true;
            for (int j = 0; j < combination.length; j++) {
                if(poketCards[i].getId() == combination[j].getId()){
                    flag = false;
                }
            }
            if(flag){
                tmp.add(poketCards[i]);
            }
        }
        if(tmp.size() == 1){
            return tmp.get(0).getDignitysId();
        }else if(tmp.size() == 2){
            result = tmp.get(0).getDignitysId() > tmp.get(1).getDignitysId() ? 
                    tmp.get(0).getDignitysId() : tmp.get(1).getDignitysId();
        }
        return result;
    }
}
