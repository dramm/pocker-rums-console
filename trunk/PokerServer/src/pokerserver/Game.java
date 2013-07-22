/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class Game {
    public boolean  run;
    Table[] mainTables = new Table[3];
    public Game(){
        mainTables[0] = new Table(4, "First table");
        mainTables[1] = new Table(6, "Second table");
        mainTables[2] = new Table(9, "Third table");
        run = true;
    }
    public void gameStart(){
        while(run){
            try {
                for (int i = 0; i < mainTables.length; i++) {
                    mainTables[i].nextStage();
                    mainTables[i].getInfo();
                }
                Thread.sleep(15*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
   
        }
    }
}
