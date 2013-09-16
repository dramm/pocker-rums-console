/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;
import Enums.GameStages.Stage;
import static Enums.GameStages.Stage.PREFLOP;
import static Enums.GameStages.Stage.STARTING;
import PokerEngyne.Bets;
import PokerEngyne.Counters;
import PokerEngyne.MonteCarlo;
import PokerEngyne.Sequence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
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
    public Bets bets;
    private Stage gameStage;
    private int gameId;
    public Game_new(){
        gameStage = STARTING;
        tables = new Table_new[3];
        tables[0] = new Table_new(4);
        tables[1] = new Table_new(6);
        tables[2] = new Table_new(8);
        bets = new Bets();
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
                Thread.sleep(200);
            }
        }
        catch (JSONException | InterruptedException ex) {
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
                
                Bridge.newData.setComand(1500);
                Bridge.newData.setFlag(true);
                gameStage = Stage.PREFLOP;
                break;
            }
            case PREFLOP:{
                DBTools.setGameStage(gameStage.getStage(), gameId);
                Bridge.newData.js = generatePreflopPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1510);
                Bridge.newData.setFlag(true);
                gameStage = Stage.FLOP;
                break;
            }
            case FLOP:{
                DBTools.setGameStage(gameStage.getStage(), gameId);
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1520);
                Bridge.newData.setFlag(true);
                gameStage = Stage.TURN;
                break;
            }
            case TURN:{
                DBTools.setGameStage(gameStage.getStage(), gameId);
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1530);
                Bridge.newData.setFlag(true);
                gameStage = Stage.RIVER;
                break;
            }
            case RIVER:{
                DBTools.setGameStage(gameStage.getStage(), gameId);
                Bridge.newData.js = generateBoardPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1540);
                Bridge.newData.setFlag(true);
                gameStage = Stage.SHOWDOWN;
                break;
            }
            case SHOWDOWN:{
                Bridge.newData.js = generateShowDownPakege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1550);
                Bridge.newData.setFlag(true);
                gameStage = Stage.STARTING;
                bets.resetBets();
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
        for (int i = 0; i < tables.length; i++) {
            tables[i].runMontecarlo();
        }
        do {
            
            tables[0].setFactor();
            tables[1].setFactor();
            tables[2].setFactor();
            
        } while (!tables[0].isFlag() && !tables[1].isFlag() && !tables[2].isFlag());
        
        JSONObject js = new JSONObject();
        for (int i = 0; i < tables.length; i++) {
            //Counters factor = MonteCarlo.getFactor(tables[i].getPlayers(), tables[i].getDeck());
            Counters factor = tables[i].getFactor();
            JSONObject player = new JSONObject();
            for (int j = 0; j < tables[i].getPlayers().length; j++) {
                float winRate = (float)(factor.getWins()[j] + 1) / factor.iteration;
                float winFactor = 1 / winRate;
                if(winFactor < 1.35f){
                    winFactor = 1.01f;
                }
                int indicator = (int)(((float)(factor.getTie()[j] + 1) / factor.iteration) * 100);
                JSONObject factors = new JSONObject();
                player.append("Player"+j, tables[i].getPlayers()[j].getFirstPocketCardId());
                player.append("Player"+j, tables[i].getPlayers()[j].getSecondPocketCardId());
                factors.put("Factor", String.format("%.2f", winFactor < 50 ? winFactor : 49.99f));
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
                factor.put("Factor", String.format("%.2f", winFactor < 50 ? (winFactor < 1.35f ? 1.01f : winFactor ) : 49.99f));
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
        JSONArray win = new JSONArray();
        int[][] indexes = new int[tables.length][];
        for (int i = 0; i < tables.length; i++) {
            JSONObject jsCardId = new JSONObject();
            Player[] winners = tables[i].getWinners();
            indexes[i] = new int[winners.length];
            for (int j = 0; j < winners.length; j++) {
                indexes[i][j] = winners[j].getPlayerId();
                int[] cardsId = winners[j].getCombinationPover().winnCardsId;
                for (int k = 0; k < cardsId.length ; k++) {
                    jsCardId.append("Combination"+j, cardsId[k]);
                }
            }
            pack.put("Table"+i, jsCardId);
        }
        pack.put("Stage", gameStage.toString());
        pack.put("Winners", bets.findWinner(indexes));
        return pack;
    }
    
    private void debug(){
        System.out.println(gameStage.toString());
        System.out.println(Bridge.newData.js.toString());
    }
}
