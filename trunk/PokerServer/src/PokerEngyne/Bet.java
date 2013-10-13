/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Андрей
 */
public class Bet {
    private int betId;
    private int userId;
    private double betSize;
    private boolean express;
    private List<Integer> handsId;
    private Map<String,Map<Integer,Double>> tableData;
    public Bet(JSONObject data) throws JSONException{
        betId = data.getInt("IdBet");
        userId = data.getInt("Id");
        betSize = data.getDouble("Sum");
        tableData = new HashMap<>();
        if(data.has("Express")){
            express = data.getBoolean("Express");
        }else{
            express = false;
        }
        handsId = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if(data.has("Table"+i)){
                JSONObject bets = data.getJSONObject("Table"+i);
                if(bets != null){
                    Iterator it = bets.keys();
                    Map<Integer,Double> tmp = new HashMap<>();
                    while(it.hasNext()){
                        String next = it.next().toString();
                        tmp.put(Integer.parseInt(next), bets.getDouble(next));
                        handsId.add((i * 10) + Integer.parseInt(next));
                    }
                    tableData.put("Table" + i, tmp);
                }
            }
        }
    }

    public int getUserId() {
        return userId;
    }

    public double getBetSize() {
        return betSize;
    }

    public Map<String,Map<Integer,Double>> getTableData() {
        return tableData;
    }

    public int getBetId() {
        return betId;
    }

    /**
     * @return the express
     */
    public boolean isExpress() {
        return express;
    }

    /**
     * @return the handsId
     */
    public List<Integer> getHandsId() {
        return handsId;
    }

    /**
     * @param handsId the handsId to set
     */
    public void setHandsId(List<Integer> handsId) {
        this.handsId = handsId;
    }
}