/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import Enums.GameStages.Stage;
import org.json.JSONObject;

/**
 *
 * @author Андрей
 */
public class GameData_new {
    public JSONObject js;
    private boolean flag;
    private boolean goNext;
    private Stage gameStage;
    private int comand;

    public synchronized boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public synchronized boolean isGoNext(){
        return goNext;
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public  Stage getGameStage() {
        return gameStage;
    }

    public  void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public int getComand() {
        return comand;
    }

    public void setComand(int comand) {
        this.comand = comand;
    }

}
