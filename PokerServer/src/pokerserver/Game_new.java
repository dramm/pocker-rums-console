/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;
import Enums.GameStages.Stage;
import static Enums.GameStages.Stage.PREFLOP;
import static Enums.GameStages.Stage.STARTING;
import PokerEngyne.Counters;
import PokerEngyne.MonteCarlo;
import PokerEngyne.Sequence;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import java.util.Random;
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
    private Random rnd = new Random();
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
                    tables[i].generateGame();
                }
                Bridge.newData.js = generateStartingPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.PREFLOP;
                break;
            }
            case PREFLOP:{
                Bridge.newData.js = generatePreflopPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.FLOP;
                break;
            }
            case FLOP:{
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.TURN;
                break;
            }
            case TURN:{
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.RIVER;
                break;
            }
            case RIVER:{
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
                gameStage = Stage.SHOWDOWN;
                break;
            }
            case SHOWDOWN:{
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
            Counters factor = MonteCarlo.getFactor(tables[i].getPlayers(), tables[i].getDeck());
            JSONObject player = new JSONObject();
            for (int j = 0; j < tables[i].getPlayers().length; j++) {
                player.append("Player"+j, tables[i].getPlayers()[j].getFirstPocketCardId());
                player.append("Player"+j, tables[i].getPlayers()[j].getSecondPocketCardId());
                //player.append("Player"+j, new JSONObject().put("Factor", String.format("%.2f",factor[j])));
            }
            js.put("Table"+i, player);
        }
        js.put("Stage", gameStage.toString());
        return js;
    }
    private JSONObject generateBoardPackege() throws JSONException{
        JSONObject js = new JSONObject();
        for (int i = 0; i < tables.length; i++) {
            float[] factorFl = MonteCarlo.getFactor(tables[i].getPlayers(), tables[i].getDeck(), tables[i].getBord(), gameStage);
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
                if(gameStage == Stage.FLOP || gameStage == Stage.TURN){
                    factor.put("Player"+j, new JSONObject().put("Factor", String.format("%.2f",factorFl[j])));
                }else{
                    //factor.put("Player"+j, new JSONObject().put("Factor", "N/A"));
                    factor.put("Player"+j, new JSONObject().put("Factor", String.format("%.2f",factorFl[j])));
                }
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
