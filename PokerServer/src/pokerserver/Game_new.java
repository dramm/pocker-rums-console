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
        gameStage = STARTING;
        tables = new Table_new[3];
        tables[0] = new Table_new(4);
        tables[1] = new Table_new(6);
        tables[2] = new Table_new(8);
        run = false;
    }

    @Override
    public void run() {
        run = true;
        try{
            while (run) {
                if(Bridge.newData.isGoNext()){
                    nextStage();
                    Bridge.newData.setGoNext(false);
                }
                Thread.sleep(10);
            }
        }
        catch (JSONException ex) {
            Logger.getLogger(Game_new.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game_new.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void nextStage() throws JSONException{
        switch (gameStage) {
            case STARTING:{
                DBTools.setGame();
                gameId = DBTools.getLastGameId();
                for (int i = 0; i < tables.length; i++) {
                    tables[i].startingStage();
                }
                Bridge.newData.js = generateStartingPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.PREFLOP;
                break;
            }
            case PREFLOP:{
                for (int i = 0; i < tables.length; i++) {
                    tables[i].preflopStage();
                }
                Bridge.newData.js = generatePreflopPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.FLOP;
                break;
            }
            case FLOP:{
                for (int i = 0; i < tables.length; i++) {
                    tables[i].flopStage();
                }
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.TURN;
                break;
            }
            case TURN:{
                for (int i = 0; i < tables.length; i++) {
                    tables[i].turnStage();
                }
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.RIVER;
                break;
            }
            case RIVER:{
                for (int i = 0; i < tables.length; i++) {
                    tables[i].riverStage();
                }
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.SHOWDOWN;
                break;
            }
            case SHOWDOWN:{
                for (int i = 0; i < tables.length; i++) {
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
        js.put("Round", gameId);
        js.put("Stage", gameStage.toString());
        return js;
    }
    private JSONObject generatePreflopPackege() throws JSONException{
        JSONObject js = new JSONObject();
        for (int i = 0; i < tables.length; i++) {
            JSONObject player = new JSONObject();
            for (int j = 0; j < tables[i].getPlayers().length; j++) {
                player.append("Player"+j, tables[i].getPlayers()[j].getFirstPocketCardId());
                player.append("Player"+j, tables[i].getPlayers()[j].getSecondPocketCardId());
                player.append("Player"+j, new JSONObject().put("Factor", tables[i].getPlayers()[j].getFactor()));
            }
            js.put("Table"+i, player);
        }
        js.put("Stage", gameStage.toString());
        return js;
    }
    private JSONObject generateBoardPackege() throws JSONException{
        JSONObject js = new JSONObject();
        for (int i = 0; i < tables.length; i++) {
            JSONObject bord = new JSONObject();
            JSONObject factor = new JSONObject();
            switch (gameStage) {
                case FLOP:{
                    for (int j = 0; j < 3; j++) {
                        bord.append("Bord", tables[i].getBord()[j].getId());
                    }
                    break;
                }
                case TURN:{
                    for (int j = 0; j < 4; j++) {
                        bord.append("Bord", tables[i].getBord()[j].getId());
                    }
                    break;
                }
                case RIVER:{
                    for (int j = 0; j < 5; j++) {
                        bord.append("Bord", tables[i].getBord()[j].getId());
                    }
                    break;
                }
            }
            for (int j = 0; j < tables[i].getPlayers().length; j++) {
                factor.put("Player"+j, new JSONObject().put("Factor", tables[i].getPlayers()[j].getFactor()));
            }
            js.append("Table"+i, bord);
            js.append("Table"+i, factor);
        }
        js.put("Stage", gameStage.toString());
        return js;
    }
    private void debug(){
        System.out.println(gameStage.toString());
        System.out.println(Bridge.newData.js.toString());
    }
}
