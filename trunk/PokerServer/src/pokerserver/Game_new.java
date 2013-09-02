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
                Bridge.newData.js = generateShowDownPakege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                Bridge.newData.setFlag(true);
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
                float winRate = (float)(factor.getWins()[j] + 1) / factor.iteration;
                float winFactor = 1 / winRate;
                int indicator = (int)(((float)(factor.getTie()[j] + 1) / factor.iteration) * 100);
                JSONObject factors = new JSONObject();
                player.append("Player"+j, tables[i].getPlayers()[j].getFirstPocketCardId());
                player.append("Player"+j, tables[i].getPlayers()[j].getSecondPocketCardId());
                factors.put("Factor", String.format("%.2f", winFactor < 50 ? winFactor : 50));
                factors.put("Indicator", indicator);
                player.append("Player"+j, factors);
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
            JSONObject player = new JSONObject();
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
            
            Counters counters = MonteCarlo.getFactor(tables[i].getPlayers(), tables[i].getDeck(), tables[i].getBord(), gameStage);
            for (int j = 0; j < tables[i].getPlayers().length; j++) {
                float winRate = (float)(counters.getWins()[j] + 1) / counters.iteration;
                float winFactor = 1 / winRate;
                int indicator = (int)(((float)(counters.getTie()[j] +1) / counters.iteration) * 100);
                JSONObject factor = new JSONObject();
                factor.put("Factor", String.format("%.2f", winFactor < 50 ? winFactor : 50));
                factor.put("Indicator", indicator);
                player.put("Player"+j, factor);
            }
            js.append("Table"+i, bord);
            js.append("Table"+i, player);
        }
        js.put("Stage", gameStage.toString());
        return js;
    }
    
    private JSONObject generateShowDownPakege() throws JSONException{
        JSONObject pack = new JSONObject();
        for (int i = 0; i < tables.length; i++) {
            JSONObject jsCardId = new JSONObject();
            Player[] winners = Sequence.getWinner(tables[i].getPlayers());
            for (int j = 0; j < winners.length; j++) {
                int[] cardsId = winners[j].getCombinationPover().winnCardsId;
                for (int k = 0; k < cardsId.length ; k++) {
                    jsCardId.append("Combination"+j, cardsId[k]);
                }
            }
            pack.put("Table"+i, jsCardId);
        }
        pack.put("Stage", gameStage.toString());
        return pack;
    }
    
    private void debug(){
        System.out.println(gameStage.toString());
        System.out.println(Bridge.newData.js.toString());
    }
}
