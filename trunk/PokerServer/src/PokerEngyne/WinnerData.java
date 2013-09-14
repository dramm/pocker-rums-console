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
public class WinnerData {
    public int combinationPower;
    public int secondPairPower;
    public int actualPower;
    public int cicker;
    public int[] pocketCardsId;
    public int[] winnCardsId;
    public int[] boardCards;
    
    public void calculationCicker(){
        int result = DBTools.getOlder(pocketCardsId[0], pocketCardsId[1]);
        for (int i = 0; i < winnCardsId.length; i++) {
            if(winnCardsId[i] == result){
                result = DBTools.getJunior(pocketCardsId[0], pocketCardsId[1]);
            }
        }
        for (int i = 0; i < winnCardsId.length; i++) {
            if(winnCardsId[i] == result){
                cicker = 0;
            }
        }
        cicker = DBTools.getCardDignitys(result);
    }
}
