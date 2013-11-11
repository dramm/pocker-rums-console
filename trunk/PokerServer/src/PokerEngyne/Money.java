/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerEngyne;

import pokerserver.DBTools;

/**
 *
 * @author Андрей
 */
public class Money implements Runnable{
    private float persent;
    private double totalMoney;
    private double balance;
    private double profit;
    private double spareMoney;
    public Money() {
        this.persent = (float)(DBTools.getPersent() / 100f);
        this.totalMoney = DBTools.getTotalMoney();
        this.balance = DBTools.getBalance();
        this.profit = DBTools.getProfit();
        this.spareMoney = DBTools.getSpareMoney();
    }
    
    @Override
    public void run() {
        if(profit < (totalMoney * persent)){
            
        }else{
            
        }
    }
    
}
