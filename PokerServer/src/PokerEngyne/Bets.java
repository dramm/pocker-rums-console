/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import java.util.ArrayList;
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
    public void resetBets(){
        bets = new ArrayList<>();
    }
    public JSONArray findWinner(int[][] indexes) throws JSONException{
        JSONArray winnData = new JSONArray();
        for (Bet bet : bets) {
            if(bet.isExpress()){
                float factor = 1;
                int count = 0;
                JSONObject playerData = new JSONObject();
                Map<String, Map<Integer, Double>> tableData = bet.getTableData();
                for(Map.Entry<String, Map<Integer, Double>> tableInfo : tableData.entrySet()){
                    for (int i = 0; i < indexes.length; i++) {
                        if(tableInfo.getKey().equals("Table" + i)){
                            for (int j = 0; j < indexes[i].length; j++) {
                                if(tableInfo.getValue().get(indexes[i][j]) != null){
                                    factor *= tableInfo.getValue().get(indexes[i][0]);
                                    count ++;
                                }
                            }                           
                        }
                    }
                }
                float winnSize = 0;
                if(tableData.size() == count){
                    winnSize = (float) (bet.getBetSize() * factor);
                }
                playerData.put("IdBet", bet.getBetId());
                playerData.put("winnSize", winnSize);
                playerData.put("playerId", bet.getUserId());
                winnData.put(playerData);
            }else{
                JSONObject playerData = new JSONObject();
                int betCoutn = 0;
                double summSize = 0;
                Map<String, Map<Integer, Double>> tableData = bet.getTableData();
                for(Map.Entry<String, Map<Integer, Double>> tableInfo : tableData.entrySet()){
                    for (int i = 0; i < indexes.length; i++) {
                        if(tableInfo.getKey().equals("Table" + i)){
                            Map<Integer, Double> handInfo = tableInfo.getValue();
                            for(Map.Entry<Integer, Double> factorinfo : handInfo.entrySet()){
                                betCoutn++;
                                for (int j = 0; j < indexes[i].length; j++) {
                                    if(factorinfo.getKey() == indexes[i][j]){
                                        summSize += bet.getBetSize() * factorinfo.getValue();
                                    }
                                }
                            }  
                        }
                    }
                }
                playerData.put("betCount", betCoutn);
                            
                playerData.put("IdBet", bet.getBetId());
                playerData.put("winnSize", summSize / betCoutn);
                playerData.put("playerId", bet.getUserId());
                winnData.put(playerData);
            }            
        }
        return winnData;
    }
}
