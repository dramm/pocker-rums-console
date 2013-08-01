/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import Enums.GameStages.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class Game extends Thread{
    private boolean  run;
    Table[] mainTables = new Table[3];
    public Game(){
        mainTables[0] = new Table(4, "First table");
        mainTables[1] = new Table(6, "Second table");
        mainTables[2] = new Table(9, "Third table");
        run = false;
    }
    public void gameStart(){
        while(run){
            try {
                for (int i = 0; i < mainTables.length; i++) {
                    mainTables[i].nextStage();
                    mainTables[i].getInfo();
                }
                if(mainTables[0].getStage() == Stage.PREFLOP ||
                        mainTables[0].getStage() == Stage.FLOP ||
                        mainTables[0].getStage() == Stage.TURN){
                    for (int i = 5; i > 0; i--) {
                        Thread.sleep(1000);
                        System.out.println("Next stage after "+ i + " seconds...");
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
   
        }
    }
    @Override
    public void run (){
        this.run = true;
        gameStart();
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
