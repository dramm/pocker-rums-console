/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import threads.Bridge;

/**
 *
 * @author Андрей
 */
public class Game extends Thread{
    private boolean  run;
    Table[] mainTables = new Table[3];
    public Game(){
        mainTables[0] = new Table(4, "First");
        mainTables[1] = new Table(6, "Second");
        mainTables[2] = new Table(8, "Third");
        run = false;
    }
    public void gameStart(){
        while(run){
            if(Bridge.data.isGoNext()){
                for (int i = 0; i < mainTables.length; i++) {
                    mainTables[i].nextStage();
                   // mainTables[i].getInfo();
                }
                Bridge.data.setGoNext(false);
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
