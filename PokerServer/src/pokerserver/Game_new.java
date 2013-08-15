/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;
import Enums.GameStages.Stage;
import static Enums.GameStages.Stage.PREFLOP;
import static Enums.GameStages.Stage.STARTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import threads.Bridge;

/**
 *
 * @author Андрей
 */
public class Game_new implements Runnable{
    private boolean run;
    private Table_new[] tables;
    private Stage gameStage;
    private int gameId;
    public Game_new(){
        tables = new Table_new[3];
        tables[0] = new Table_new(4);
        tables[1] = new Table_new(6);
        tables[2] = new Table_new(8);
        run = false;
    }

    @Override
    public void run() {
        try{
            while (run) {
                if(Bridge.newData.isGoNext()){
                        nextStage();
                }
            }
        }
        catch (JSONException ex) {
            Logger.getLogger(Game_new.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void nextStage() throws JSONException{
        switch (gameStage) {
            case STARTING:{
                DBTools.setGame();
                gameId = DBTools.getLastGameId();
                for (int i = 0; i < 10; i++) {
                    tables[i].startingStage();
                }
                Bridge.newData.js = generateStartingPackege();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.PREFLOP;
                break;
            }
            case PREFLOP:{
                for (int i = 0; i < 10; i++) {
                    tables[i].preflopStage();
                }
                Bridge.newData.js = generatePreflopPackege();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.FLOP;
                break;
            }
            case FLOP:{
                for (int i = 0; i < 10; i++) {
                    tables[i].flopStage();
                }
                gameStage = Stage.TURN;
                break;
            }
            case TURN:{
                for (int i = 0; i < 10; i++) {
                    tables[i].turnStage();
                }
                gameStage = Stage.RIVER;
                break;
            }
            case RIVER:{
                for (int i = 0; i < 10; i++) {
                    tables[i].riverStage();
                }
                gameStage = Stage.SHOWDOWN;
                break;
            }
            case SHOWDOWN:{
                for (int i = 0; i < 10; i++) {
                    tables[i].showdownStage();
                }
                gameStage = Stage.STARTING;
                break;
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
    
    private JSONObject generateStartingPackege() throws JSONException{
        JSONObject js = new JSONObject();
        js.put("Stage", gameStage.toString()); 
        js.put("Round", gameId);
        return js;
    }
    private JSONObject generatePreflopPackege() throws JSONException{
        JSONObject js = new JSONObject();
        for (int i = 0; i < tables.length; i++) {
            JSONObject tmp = new JSONObject();
            for (int j = 0; j < tables[i].getPlayers().length; j++) {
                tmp.append("Player"+i, tables[i].getPlayers()[j].getFirstPocketCardId());
                tmp.append("Player"+i, tables[i].getPlayers()[j].getSecondPocketCardId());
            }
            js.append("Bord"+i, tmp);
        }
        return js;
    }
}
