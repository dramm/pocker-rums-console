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
import pokerserver.DBTools;

/**
 *
 * @author Андрей
 */
public class Bets {
    private List<Bet> bets;
    private double totalWin;
    private double totalBet;
    public Bets(){
        bets = new ArrayList<>();
        totalBet = 0;
        totalWin = 0;
    }
    public void addBet(Bet bet){
        getBets().add(bet);
        totalBet += bet.getBetSize();
        
    }
    public void resetBets(){
        bets = new ArrayList<>();
    }
    public JSONArray findWinner(int[][] indexes) throws JSONException{
        totalWin = 0;
        JSONArray winnData = new JSONArray();
        for (Bet bet : getBets()) {
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
                double winnSize = 0;
                if(tableData.size() == count){
                    winnSize = (double) (bet.getBetSize() * factor);
                }
                playerData.put("IdBet", bet.getBetId());
                playerData.put("winnSize", winnSize);
                playerData.put("playerId", bet.getUserId());
                winnData.put(playerData);
                totalWin += winnSize;
                DBTools.setBetResult(bet.getBetId(), winnSize);
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
                double winnSize = (double)(summSize / betCoutn);
                playerData.put("betCount", betCoutn);            
                playerData.put("IdBet", bet.getBetId());
                playerData.put("winnSize", winnSize);
                playerData.put("playerId", bet.getUserId());
                winnData.put(playerData);
                totalWin += winnSize;
                DBTools.setBetResult(bet.getBetId(), summSize / betCoutn);
            }            
        }
        return winnData;
    }

    /**
     * @return the totalWin
     */
    public double getTotalWin() {
        return totalWin;
    }

    /**
     * @return the totalBet
     */
    public double getTotalBet() {
        return totalBet;
    }

    /**
     * @return the bets
     */
    public List<Bet> getBets() {
        return bets;
    }
}
