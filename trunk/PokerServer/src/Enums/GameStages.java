/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author Андрей
 */

public class GameStages {
    public enum Stage{
        STARTING(0),PREFLOP(1), FLOP(2), TURN(3), RIVER(4), SHOWDOWN(5);
        private int stage;
        private Stage(int val){
            stage = val;
        }
        public int getStage() {
            return stage;
        }
        @Override public String toString(){
            String result = super.toString();
            return result.substring(0, 1) + result.substring(1).toLowerCase();
        }
    }
    
}
