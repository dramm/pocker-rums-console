/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Андрей
 */
public class Bets {
    private List<Bet> bets;
    public Bets(){
        bets = new ArrayList<>();
    }
    public void addBet(Bet bet){
        bets.add(bet);
    }
    public JSONArray findWinner(int tableId, int handId) throws JSONException{
        JSONObject winners = new JSONObject();
        JSONArray winnData = new JSONArray();
        int playerId;
        double factor;
        double sum;
        for (Bet bet : bets) {
            JSONObject playerData = new JSONObject();
            Map<Integer, Map<Integer, Double>> tableData = bet.getTableData();
            for(Map.Entry<Integer, Map<Integer, Double>> item : tableData.entrySet()){
                if(tableId == item.getKey()){
                    Map<Integer, Double> value = item.getValue();
                    for (Map.Entry<Integer, Double> factorInBet : value.entrySet()) {
                        if(handId == factorInBet.getKey()){
                            factor = factorInBet.getValue();
                            playerId = bet.getUserId();
                            sum = bet.getBetSize();
                            playerData.put("id", playerId);
                            playerData.put("sum", sum * factor);
                        }
                    }
                    
                }
            }
            winnData.put(playerData);
        }
        return winnData;
    }
}
