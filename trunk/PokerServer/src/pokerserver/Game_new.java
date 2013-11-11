/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;
import DataBaseClasses.Cards;
import Enums.GameStages.Stage;
import static Enums.GameStages.Stage.PREFLOP;
import static Enums.GameStages.Stage.STARTING;
import PokerEngyne.Bets;
import PokerEngyne.Counters;
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
        tables[0].setTableId(0);
        tables[1] = new Table_new(6);
        tables[1].setTableId(1);
        tables[2] = new Table_new(8);
        tables[2].setTableId(2);
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
                gameId = DBTools.setGame();
                for (int i = 0; i < getTables().length; i++) {
                    getTables()[i].generateGame();
                }
                Bridge.newData.js = generateStartingPackege();
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1500);
                Bridge.newData.setFlag(true);
                gameStage = Stage.PREFLOP;
                for (int i = 0; i < getTables().length; i++) {
                    getTables()[i].runMontecarlo();
                }
                break;
            }
            case PREFLOP:{
                //DBTools.setGameStage(gameStage.getStage(), gameId);
                Bridge.newData.js = generatePreflopPackege();
                
                for (int i = 0; i < getTables().length; i++) {
                    int gameStageId = DBTools.setGameStage(gameStage.getStage(), gameId, getTables()[i].getTableId());
                    getTables()[i].setStageIdDb(gameStageId);
                    
                    for (int j = 0; j < getTables()[i].getPlayers().length; j++) {
                        int setHand = DBTools.setHand(getTables()[i].getPlayers()[j].getPocketCards());
                        getTables()[i].getPlayers()[j].setHandInStageId(setHand);
                        DBTools.setHandInStage(gameStageId, setHand, getTables()[i].getPlayers()[j].getFactor(), getTables()[i].getPlayers()[j].getIndicator());
                    }
                }
                
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1510);
                Bridge.newData.setFlag(true);
                gameStage = Stage.FLOP;
                for (int i = 0; i < getTables().length; i++) {
                    getTables()[i].runMontecarlo(gameStage);
                }
                break;
            }
            case FLOP:{
                //DBTools.setGameStage(gameStage.getStage(), gameId);
                
                Bridge.newData.js = generateBoardPackege();
                
                for (int i = 0; i < getTables().length; i++) {
                    int gameStageId = DBTools.setGameStage(gameStage.getStage(), gameId, getTables()[i].getTableId());
                    getTables()[i].setStageIdDb(gameStageId);
                    for (int j = 0; j < 3; j++) {
                        DBTools.setDistribution(getTables()[i].getBord()[j], getTables()[i].getStageIdDb());
                    }
                    for (int j = 0; j < getTables()[i].getPlayers().length; j++) {
                        int setHand = DBTools.setHand(getTables()[i].getPlayers()[j].getPocketCards());
                        getTables()[i].getPlayers()[j].setHandInStageId(setHand);
                        DBTools.setHandInStage(gameStageId, setHand, getTables()[i].getPlayers()[j].getFactor(), getTables()[i].getPlayers()[j].getIndicator());
                    }
                }
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1520);
                Bridge.newData.setFlag(true);
                gameStage = Stage.TURN;
                for (int i = 0; i < getTables().length; i++) {
                    getTables()[i].runMontecarlo(gameStage);
                }
                break;
            }
            case TURN:{
                //DBTools.setGameStage(gameStage.getStage(), gameId);
                
                Bridge.newData.js = generateBoardPackege();
                
                for (int i = 0; i < getTables().length; i++) {
                    int gameStageId = DBTools.setGameStage(gameStage.getStage(), gameId, getTables()[i].getTableId());
                    getTables()[i].setStageIdDb(gameStageId);
                    DBTools.setDistribution(getTables()[i].getBord()[3], getTables()[i].getStageIdDb());
                    for (int j = 0; j < getTables()[i].getPlayers().length; j++) {
                        int setHand = DBTools.setHand(getTables()[i].getPlayers()[j].getPocketCards());
                        getTables()[i].getPlayers()[j].setHandInStageId(setHand);
                        DBTools.setHandInStage(gameStageId, setHand, getTables()[i].getPlayers()[j].getFactor(), getTables()[i].getPlayers()[j].getIndicator());
                    }
                }
                debug();
                Bridge.newData.setGameStage(gameStage);
                
                Bridge.newData.setComand(1530);
                Bridge.newData.setFlag(true);
                gameStage = Stage.RIVER;
                for (int i = 0; i < getTables().length; i++) {
                    getTables()[i].runMontecarlo(gameStage);
                }
                break;
            }
            case RIVER:{
                //DBTools.setGameStage(gameStage.getStage(), gameId);
                
                Bridge.newData.js = generateBoardPackege();
                
                for (int i = 0; i < getTables().length; i++) {
                    int gameStageId = DBTools.setGameStage(gameStage.getStage(), gameId, getTables()[i].getTableId());
                    getTables()[i].setStageIdDb(gameStageId);
                    DBTools.setDistribution(getTables()[i].getBord()[4], getTables()[i].getStageIdDb());
                    for (int j = 0; j < getTables()[i].getPlayers().length; j++) {
                        int setHand = DBTools.setHand(getTables()[i].getPlayers()[j].getPocketCards());
                        getTables()[i].getPlayers()[j].setHandInStageId(setHand);
                        DBTools.setHandInStage(gameStageId, setHand, getTables()[i].getPlayers()[j].getFactor(), getTables()[i].getPlayers()[j].getIndicator());
                    }
                }
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
                
        JSONObject js = new JSONObject();
        for (int i = 0; i < getTables().length; i++) {
            do {

                getTables()[i].setFactor();
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_new.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!tables[i].isFlag());
            //Counters factor = MonteCarlo.getFactor(tables[i].getPlayers(), tables[i].getDeck());
            Counters factor = getTables()[i].getFactor();
            JSONObject player = new JSONObject();
            for (int j = 0; j < getTables()[i].getPlayers().length; j++) {
                float winRate = (float)(factor.getWins()[j] + 1) / factor.iteration;
                float winFactor = 1 / winRate;
                if(winFactor >50 && winFactor < 147){
                    winFactor = 50;
                }else if(winFactor >= 147){
                    winFactor = -1;
                }else if(winFactor < 1.1f){
                    winFactor = 1;
                }
                int indicator = (int)(((float)(factor.getTie()[j] + 1) / factor.iteration) * 100);
                JSONObject factors = new JSONObject();
                player.append("Player"+j, getTables()[i].getPlayers()[j].getFirstPocketCardId());
                player.append("Player"+j, getTables()[i].getPlayers()[j].getSecondPocketCardId());
                factors.put("Factor", String.format("%.2f", winFactor));
                getTables()[i].getPlayers()[j].setFactor(winFactor);
                getTables()[i].getPlayers()[j].setIndicator(indicator);
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
        for (int i = 0; i < getTables().length; i++) {
            do { 
                getTables()[i].setFactor();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game_new.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!tables[i].isFlag());
            JSONObject bord = new JSONObject();
            JSONObject player = new JSONObject();
            switch (gameStage) {
                case FLOP:{
                    for (int j = 0; j < 3; j++) {
                        bord.append("Bord", getTables()[i].getBord()[j].getId());
                    }
                    break;
                }
                case TURN:{
                    for (int j = 0; j < 4; j++) {
                        bord.append("Bord", getTables()[i].getBord()[j].getId());
                    }
                    break;
                }
                case RIVER:{
                    for (int j = 0; j < 5; j++) {
                        bord.append("Bord", getTables()[i].getBord()[j].getId());
                    }
                    break;
                }
            }
            
            //Counters counters = MonteCarlo.getFactor(tables[i].getPlayers(), tables[i].getDeck(), tables[i].getBord(), gameStage);
            Counters counters = getTables()[i].getFactor();
            for (int j = 0; j < getTables()[i].getPlayers().length; j++) {
                float winRate = (float)(counters.getWins()[j] + 1) / counters.iteration;
                float winFactor = 1 / winRate;
                if(winFactor >50 && winFactor < 147){
                    winFactor = 50;
                }else if(winFactor >= 147){
                    winFactor = -1;
                }else if(winFactor < 1.1f){
                    winFactor = 1;
                }
                int indicator = (int)(((float)(counters.getTie()[j] +1) / counters.iteration) * 100);
                JSONObject factor = new JSONObject();
                factor.put("Factor", String.format("%.2f", winFactor ));
                getTables()[i].getPlayers()[j].setFactor(winFactor);
                getTables()[i].getPlayers()[j].setIndicator(indicator);
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
        int[][] indexes = new int[getTables().length][];
        for (int i = 0; i < getTables().length; i++) {
            JSONObject jsCardId = new JSONObject();
            Player[] winners = getTables()[i].getWinners();
            indexes[i] = new int[winners.length];
            for (int j = 0; j < winners.length; j++) {
                indexes[i][j] = winners[j].getPlayerId();
                jsCardId.append("WinnHand", winners[j].getPlayerId());
                DBTools.setWinsHand(winners[j].getFirstPocketCardId(), winners[j].getSecondPocketCardId(), i, gameId);
                /*int[] cardsId = winners[j].getCombinationPover().winnCardsId;
                for (int k = 0; k < cardsId.length ; k++) {
                    jsCardId.append("Combination"+j, cardsId[k]);
                }*/
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
    
    private Table_new getFirstTable(){
        Table_new table = new Table_new(4);
        Cards[] bord = new Cards[5];
        bord[0] = DBTools.getCards(33);
        bord[1] = DBTools.getCards(38);
        bord[2] = DBTools.getCards(23);
        bord[3] = DBTools.getCards(51);
        bord[4] = DBTools.getCards(12);
        table.bord = bord;
        Player[] players = new Player[4];
        players[0] = new Player(new Cards[]{DBTools.getCards(24), DBTools.getCards(10)}, 0);
        players[1] = new Player(new Cards[]{DBTools.getCards(50), DBTools.getCards(19)}, 1);
        players[2] = new Player(new Cards[]{DBTools.getCards(35), DBTools.getCards(46)}, 2);
        players[3] = new Player(new Cards[]{DBTools.getCards(30), DBTools.getCards(6)}, 3);
        table.players = players;
        return table;
    }
    private Table_new getSecondTable(){
        Table_new table = new Table_new(6);
        Cards[] bord = new Cards[5];
        bord[0] = DBTools.getCards(36);
        bord[1] = DBTools.getCards(10);
        bord[2] = DBTools.getCards(14);
        bord[3] = DBTools.getCards(4);
        bord[4] = DBTools.getCards(28);
        table.bord = bord;
        Player[] players = new Player[6];
        players[0] = new Player(new Cards[]{DBTools.getCards(23), DBTools.getCards(51)}, 0);
        players[1] = new Player(new Cards[]{DBTools.getCards(17), DBTools.getCards(19)}, 1);
        players[2] = new Player(new Cards[]{DBTools.getCards(27), DBTools.getCards(2)}, 2);
        players[3] = new Player(new Cards[]{DBTools.getCards(42), DBTools.getCards(49)}, 3);
        players[4] = new Player(new Cards[]{DBTools.getCards(45), DBTools.getCards(37)}, 4);
        players[5] = new Player(new Cards[]{DBTools.getCards(34), DBTools.getCards(12)}, 5);
        table.players = players;
        return table;
    }
    private Table_new getThirdTable(){
        Table_new table = new Table_new(8);
        Cards[] bord = new Cards[5];
        bord[0] = DBTools.getCards(2);
        bord[1] = DBTools.getCards(30);
        bord[2] = DBTools.getCards(5);
        bord[3] = DBTools.getCards(41);
        bord[4] = DBTools.getCards(23);
        table.bord = bord;
        Player[] players = new Player[8];
        players[0] = new Player(new Cards[]{DBTools.getCards(10), DBTools.getCards(28)}, 0);
        players[1] = new Player(new Cards[]{DBTools.getCards(13), DBTools.getCards(24)}, 1);
        players[2] = new Player(new Cards[]{DBTools.getCards(50), DBTools.getCards(16)}, 2);
        players[3] = new Player(new Cards[]{DBTools.getCards(12), DBTools.getCards(17)}, 3);
        players[4] = new Player(new Cards[]{DBTools.getCards(15), DBTools.getCards(4)}, 4);
        players[5] = new Player(new Cards[]{DBTools.getCards(52), DBTools.getCards(20)}, 5);
        players[6] = new Player(new Cards[]{DBTools.getCards(31), DBTools.getCards(9)}, 6);
        players[7] = new Player(new Cards[]{DBTools.getCards(18), DBTools.getCards(45)}, 7);
        table.players = players;
        return table;
    }

    /**
     * @return the tables
     */
    public Table_new[] getTables() {
        return tables;
    }
}
