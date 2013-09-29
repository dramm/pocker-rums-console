/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import java.util.HashMap;
import java.util.Iterator;
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
    private Map<String,Map<Integer,Double>> tableData;
    public Bet(JSONObject data) throws JSONException{
        betId = data.getInt("IdBet");
        //betId = 101;
        userId = data.getInt("Id");
        betSize = data.getDouble("Sum");
        tableData = new HashMap<>();
        if(data.has("Express")){
            express = data.getBoolean("Express");
        }else{
            express = false;
        }
        for (int i = 0; i < 3; i++) {
            if(data.has("Table"+i)){
                JSONObject bets = data.getJSONObject("Table"+i);
                if(bets != null){
                    Iterator it = bets.keys();
                    Map<Integer,Double> tmp = new HashMap<>();
                    while(it.hasNext()){
                        String next = it.next().toString();
                        tmp.put(Integer.parseInt(next), bets.getDouble(next));
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
}