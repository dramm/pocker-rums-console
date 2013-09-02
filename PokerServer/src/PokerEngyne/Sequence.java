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
    public static WinnerData CheckSequence(Cards[] pocketCard, Cards[] board){
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
        if(isRoyalFlush(allCard) != null){
            WinnerData data = isRoyalFlush(allCard);
            data.combinationPower = 126;
            data.boardCards = getBoardCardsIdArray(board);
            return data;
        }
        WinnerData result = isStraightFlush(allCard);
        if(result != null){
            result.combinationPower += 112;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isQuads(allCard);
        if (result != null) {
            result.combinationPower += 98;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isFullHouse(allCard);
        if (result != null) {
            result.combinationPower += 84;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isFlush(allCard);
        if (result != null) {
            result.combinationPower += 70;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isStraight(allCard);
        if (result != null) {
            result.combinationPower += 56;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isSet(allCard);
        if (result != null) {
            result.combinationPower += 42;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isTwoPair(allCard);
        if (result != null) {
            result.combinationPower += 28;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        result = isOnePair(allCard);
        if (result != null) {
            result.combinationPower += 14;
            result.boardCards = getBoardCardsIdArray(board);
            return result;
        }
        return isHighCard(allCard);
    } 
    private static WinnerData isHighCard(Cards[] allCard){
        WinnerData data = new WinnerData();
        data.combinationPower = allCard[allCard.length-1].getDignitysId();
        data.winnCardsId = new int[1];
        data.winnCardsId[0] = allCard[allCard.length-1].getId();
        return data;
    }
    public static WinnerData isOnePair(Cards[] allCard){
        WinnerData data = new WinnerData();
        for(int i=0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                data.combinationPower = allCard[i].getDignitysId();
                data.winnCardsId = new int[2];
                data.winnCardsId[0] = allCard[i].getId();
                data.winnCardsId[1] = allCard[i+1].getId();
                return data; 
             }
        }
        return null;
    }
    public static WinnerData isTwoPair(Cards[] allCard){
        WinnerData data = new WinnerData();
        for(int i = 0; i < allCard.length - 1; i++ ){
           if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                for(int j = i+2; j < allCard.length - 1; j++){
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId()){
                        data.combinationPower = allCard[j].getDignitysId(); 
                        data.winnCardsId = new int[4];
                        data.winnCardsId[0] = allCard[i].getId();
                        data.winnCardsId[1] = allCard[i+1].getId();
                        data.winnCardsId[2] = allCard[j].getId();
                        data.winnCardsId[3] = allCard[j+1].getId();
                        return data;
                    }
                }
            } 
        }
        return null;
    }
    public static WinnerData isSet(Cards[] allCard){
        WinnerData data = new WinnerData();
        for(int i = 0; i < allCard.length - 2; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() && 
                   allCard[i].getDignitysId() == allCard[i+2].getDignitysId() ){
                data.combinationPower = allCard[i].getDignitysId();
                data.winnCardsId = new int[3];
                data.winnCardsId[0] = allCard[i].getId();
                data.winnCardsId[1] = allCard[i+1].getId();
                data.winnCardsId[2] = allCard[i+2].getId();
                return data; 
            }
        }
        return null;
    }
    public static WinnerData isStraight(Cards[] allCard){
        Cards[] clearCards = removeDuplicates(allCard);
        WinnerData data = new WinnerData();
        int stCount = 0;
        if(clearCards.length >= 5){
            for (int i = 0; i < clearCards.length - 1; i++) {
                if((clearCards[i+1].getDignitysId() - clearCards[i].getDignitysId()) == 1){
                    stCount++;
                    if((clearCards[0].getDignitysId() == 2 && clearCards[1].getDignitysId() == 3 && 
                            clearCards[2].getDignitysId() == 4 && clearCards[3].getDignitysId() == 5 &&
                            clearCards[clearCards.length - 1].getDignitysId() == 14)){
                        ArrayList<Integer> id = new ArrayList<>();
                        id.add(clearCards[0].getId());
                        id.add(clearCards[1].getId());
                        id.add(clearCards[2].getId());
                        id.add(clearCards[3].getId());
                        id.add(clearCards[clearCards.length - 1].getId());
                        data.combinationPower = clearCards[i+1].getDignitysId();
                        data.winnCardsId = new int[id.size()];
                        for (int j = 0; j < id.size(); j++) {
                            data.winnCardsId[j] = id.get(j).intValue();
                        }
                        return data;
                    }
                    if(stCount >= 4 ){
                        ArrayList<Integer> id = new ArrayList<>();
                        int lastCard = i + 1;
                        for (int j = lastCard; j >  lastCard - 5; j--) {
                            id.add(clearCards[j].getId());
                        }
                        data.combinationPower = clearCards[i+1].getDignitysId();
                        data.winnCardsId = new int[id.size()];
                        for (int j = 0; j < id.size(); j++) {
                            data.winnCardsId[j] = id.get(j).intValue();
                        }
                        return data;
                    }
                    continue;
                }
                stCount = 0;
            }
            
        }
        return null;
    }                                             
    public static WinnerData isFlush(Cards[] allCard){
        WinnerData data = new WinnerData();
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                data.combinationPower = tmp.get(tmp.size()-1).getDignitysId();
                data.winnCardsId = new int[tmp.size()];
                for (int j = 0; j < tmp.size(); j++) {
                    data.winnCardsId[j] = tmp.get(j).getId();
                }
                return data;
            }
        }
        return null;
    }
    public static WinnerData isFullHouse(Cards[] allCard){
        WinnerData data = new WinnerData();
        for (int i = 0; i < allCard.length-2; i++) {
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId() &&
                    allCard[i].getDignitysId() == allCard[i+2].getDignitysId()){
                for (int j = 0; j < allCard.length-1; j++) {
                    if(allCard[j].getDignitysId() == allCard[j+1].getDignitysId() &&
                            allCard[i].getDignitysId() != allCard[j].getDignitysId()){
                        data.combinationPower = allCard[i].getDignitysId(); 
                        data.winnCardsId = new int[5];
                        data.winnCardsId[0] = allCard[i].getId();
                        data.winnCardsId[1] = allCard[i + 1].getId();
                        data.winnCardsId[2] = allCard[i + 2].getId();
                        data.winnCardsId[3] = allCard[j].getId();
                        data.winnCardsId[4] = allCard[j + 1].getId();
                        return data;
                    }
                }
            }
        }
        return null;
    }
    public static WinnerData isQuads(Cards[] allCard){
        int count = 0;
        WinnerData data = new WinnerData();
        for(int i = 0; i < allCard.length - 1; i++){
            if(allCard[i].getDignitysId() == allCard[i+1].getDignitysId()){
                count++;
                if(count == 3){
                    data.combinationPower = allCard[i].getDignitysId();
                    data.winnCardsId = new int[4];
                    for (int j = 0; j < 4; j++) {
                        data.winnCardsId[j] = allCard[(i + 1) - j].getId();
                    }
                    return data;

                }
                continue;
            }
            count = 0;
        }
        return null;
    }
    public static WinnerData isStraightFlush(Cards[] allCard){
        WinnerData data = new WinnerData();
        for(int i = 1; i <= 4; i++){
            ArrayList<Cards> tmp = new ArrayList<>();
            for(int j = 0; j < allCard.length; j++){
                if(allCard[j].getSuitsId() == i){
                    tmp.add(allCard[j]);
                }
            }
            if(tmp.size() >= 5){
                if(isStraight(tmp.toArray(new Cards [0])) != null){
                    data.combinationPower = tmp.get(tmp.size()-1).getDignitysId();
                    data.winnCardsId = new int[tmp.size()];
                    for (int j = 0; j < tmp.size(); j++) {
                        data.winnCardsId[j] = tmp.get(j).getId();
                    }
                    return data;
                }
            }
        }
        
        return null;
    }
    public static WinnerData isRoyalFlush(Cards[] allCard){
        for (int i = 1; i <= 4; i++) {
            if(isFlush(allCard) != null && isStraight(allCard) != null){
                if(isFlush(allCard).combinationPower == 14 && isStraight(allCard).combinationPower == 14){
                    return isFlush(allCard);
                }
            }
        }
        return null;
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
    
    public static int getCicker(WinnerData data){
        return 0;
    }
    
    public static Player[] getWinner(Player[] players){
        ArrayList<Player> winners = new ArrayList<>();
        Player winner = players[0];
        for (int i = 1; i < players.length; i++) {
            if(players[i].getCombinationPover().combinationPower >= winner.getCombinationPover().combinationPower){
                winner = players[i];
            }
        }
        winners.add(winner);
        for (int i = 0; i < players.length; i++) {
            if(players[i].getCombinationPover().combinationPower == winner.getCombinationPover().combinationPower &&
                    players[i].getPlayerId() !=  winner.getPlayerId()){
                winners.add(players[i]);
            }
        }
        return winners.toArray(new Player[0]);
    }
}
