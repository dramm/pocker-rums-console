/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author Андрей
 */
public class CardsCombination {
    public enum Combinations{ 
        ROYALFLUSH(10), STRAIGHTFLUSH(9), FOUROFAKIND(8), FULLHOUSE(7),
        FLUSH(6), STARAIGHT(5), THREEOFAKIND(4), TWOPAIRS(3), PAIR(2), NIGHCARD(1);
        private int pover;
        private Combinations(int pover){
            this.pover = pover;
        }
        public int getPover(){
            return pover;
        }
    }
}
