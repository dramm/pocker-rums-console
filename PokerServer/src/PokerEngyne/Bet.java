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
    private int userId;
    private double betSize;
    private Map<Integer,Map<Integer,Double>> tableData;
    public Bet(JSONObject data) throws JSONException{
        userId = data.getInt("Id");
        betSize = data.getDouble("Sum");
        tableData = new HashMap<>();
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
                    tableData.put(i, tmp);
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

    public Map<Integer,Map<Integer,Double>> getTableData() {
        return tableData;
    }
}