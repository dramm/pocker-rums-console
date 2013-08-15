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

    public synchronized boolean isFlag() {
        return flag;
    }

    public synchronized void setFlag(boolean flag) {
        this.flag = flag;
    }

    public synchronized boolean isGoNext() {
        return goNext;
    }

    public synchronized void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public synchronized Stage getGameStage() {
        return gameStage;
    }

    public synchronized void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }
}
