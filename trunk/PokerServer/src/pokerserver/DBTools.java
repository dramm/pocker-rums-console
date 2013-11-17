/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import DataBaseClasses.Dignity;
import DataBaseClasses.Hand;
import DataBaseClasses.Suits;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Андрей
 */
public class DBTools {
    
    private static final Logger log = Logger.getLogger(DBTools.class.getName());
    
    private static Connection getConnection(){
        try{
            return DriverManager.getConnection(ServerConf.getDBUrl(),
                    ServerConf.getDBUser(), ServerConf.getDBPasswd());
            
        }
        catch(Exception e){
            log.info(e.getMessage());
            return null;
        }
    }
    
    public static Suits getSuits(int id){
        Connection conn = getConnection();
        try{
            //Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from suits where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new Suits(rs.getInt("id"), rs.getString("name"));
            }
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //log.info("return null");
        return null;
    }
    public static Dignity getDignity(int id){
        Connection conn = getConnection();
        try{
            
            PreparedStatement ps = conn.prepareStatement("select * from dignitys where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Dignity(rs.getInt("id"), rs.getInt("power"), rs.getString("name"), rs.getString("short_name"));
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }
        finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //log.info("return null");
        return null;
    }
    public static ArrayList<Cards> getCards(){
        ArrayList<Cards> deck = new ArrayList<>();
        Connection conn = getConnection();
        try{
            
            PreparedStatement ps = conn.prepareStatement("select * from cards");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                deck.add(new Cards(rs.getInt("id"), rs.getInt("suits_id"), rs.getInt("dignitys_id")));
            }
            return deck;
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }
        finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //log.info("return null");
        return null;
    }
    
    public static Cards getCards(int id){
        Connection conn = getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from cards where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new Cards(rs.getInt("id"), rs.getInt("suits_id"), rs.getInt("dignitys_id"));
            }
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }
        finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //log.info("return null");
        return null;
    }
    
    public static int setHand( Cards[] cards){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("insert into hands(first_card_id, second_card_id) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cards[0].getId());
            ps.setInt(2, cards[1].getId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
        }catch(SQLException ex){
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static void setBet(int handInStage, int playerId, double value, int betId, boolean express){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("insert into bets(hand_in_stage_id, player_id, value, bet_id, express) values(?, ?, ?, ?, ?)");
            ps.setInt(1, handInStage);
            ps.setInt(2, playerId);
            ps.setDouble(3, value);
            ps.setInt(4, betId);
            ps.setBoolean(5, express);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void setHandInStage(int gameStageId, int handId, float factor, int indicator){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("insert into hands_in_stage(game_stage_id, hand_id, factor, indicator)"
                    + " values(?, ?, ?, ?)");
            ps.setInt(1, gameStageId);
            ps.setInt(2, handId);
            ps.setFloat(3, factor);
            ps.setInt(4, indicator);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static int setGame(){
        Connection con = getConnection();
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        try {
            PreparedStatement ps = con.prepareStatement("insert into game(start_date) values(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, currentTime);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static int getGameId(int playerId, int betId){
        int result = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT game_id FROM bets, hands_in_stage, game_stage WHERE "
                    + "player_id = ? AND bet_id = ? AND hand_in_stage_id = hands_in_stage.id AND hands_in_stage.game_stage_id = game_stage.id;");
            ps.setInt(1, playerId);
            ps.setInt(2, betId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result = res.getInt("game_id");
                break;
            }
        } catch (Exception e) {
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static int setGameStage(int stage, int gameId, int tableId){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into game_stage(stage_id, game_id, table_id) values(?,?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, stage);
            ps.setInt(2, gameId);
            ps.setInt(3, tableId);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static int getGameStageId(int gameId, int stage){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT id from game_stage where game_id = ? and stage_id = ?");
            ps.setInt(1, gameId);
            ps.setInt(2, stage);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                return res.getInt("id");
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static int getGameStageId(int handInStageId){
        int result = -1;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT game_stage_id FROM hands_in_stage WHERE id = ?");
            ps.setInt(1, handInStageId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result = res.getInt("game_stage_id");
                break;
            }
        } catch (Exception e) {
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static void setDistribution(Cards card, int stageId){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into distribution(card_id, game_stage_id) values(?, ?)");
            ps.setInt(1, card.getId());
            ps.setInt(2, stageId);
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static  int getOlder(int first, int second){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("select max(dignitys_id) as val, max(id) as id from cards where id = ? || id = ?");
            ps.setInt(1, first);
            ps.setInt(2, second);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        }catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);   
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static  int getJunior(int first, int second){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("select min(dignitys_id) as val, min(id) as id from cards where id = ? || id = ?");
            ps.setInt(1, first);
            ps.setInt(2, second);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        }catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);   
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static  int getCardDignitys(int id){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("select dignitys_id as val from cards where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("val");
            }
        }catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);   
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static int getStageId(int userId, int betId){
        int result = -1;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT stage_id FROM bets, hands_in_stage, game_stage WHERE bets.bet_id = ? AND bets.player_id = ? AND bets.hand_in_stage_id = hands_in_stage.id AND hands_in_stage.game_stage_id = game_stage.id;");
            ps.setInt(1, betId);
            ps.setInt(2, userId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result = res.getInt("stage_id");
                break;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static int[] getGameStagesIds(int gameId, int stageId){
        int[] result = new int[3];
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT id FROM game_stage WHERE stage_id = ? AND game_id = ?");
            ps.setInt(1, stageId);
            ps.setInt(2, gameId);
            ResultSet res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                result[i] = res.getInt("id");
                i++;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static JSONObject getStatistics(int betId, int userId){
        JSONObject result = new JSONObject();
        int gameId = getGameId(userId, betId);
        System.out.println("gameId " + gameId);
        int stageId = getStageId(userId, betId);
        List<Integer> targetHands = getTargetHands(userId, betId);
        //System.out.println("gameId " + gameId);
        try {
            result.put("BetInfo", getBetInfo(betId, userId, stageId));
            for (int i = 0; i < 3; i++) {
                JSONObject tmp = new JSONObject();
                tmp.put("Board", getBord(gameId, i));
                tmp.put("Hands", getHands(targetHands, gameId, stageId, i));
                result.put("Table" + i, tmp);
            }
        } catch (JSONException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static JSONObject getHands(List<Integer> targetHands, int gameId, int stageId, int tableId){
        JSONObject result = new JSONObject();
        ArrayList<Hand> winnersHand = getWinnersHand(gameId, tableId);
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT hand_id, factor, indicator, first_card_id, second_card_id FROM game_stage, hands_in_stage, hands "
                    + "WHERE hands.id = hands_in_stage.hand_id AND game_stage.stage_id = ? AND game_stage.game_id = ? AND game_stage.table_id = ? AND hands_in_stage.game_stage_id = game_stage.id;");
            ps.setInt(1, stageId);
            ps.setInt(2, gameId);
            ps.setInt(3, tableId);
            ResultSet res = ps.executeQuery();
            int handNum = 0;
            while (res.next()) {
                int handId = res.getInt("hand_id");
                int first = res.getInt("first_card_id");
                int second = res.getInt("second_card_id");
                JSONObject hand = new JSONObject();
                hand.put("Factor", String.format("%.2f", res.getFloat("factor")));
                hand.put("FirstCard", first);
                hand.put("SecondCard", second);
                hand.put("Indicator", res.getInt("indicator"));
                hand.put("Target", false);
                hand.put("Wins", false);
                for (Integer item : targetHands) {
                    if(item.intValue() == handId){
                        hand.put("Target", true);
                        break;
                    }
                }
                for (Hand item : winnersHand) {
                    if(item.first == first && item.second == second){
                        hand.put("Wins", true);
                        break;
                    }
                }
                result.put("Hand" + handNum++, hand);
            }
        } catch (SQLException | JSONException e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static List<Integer> getTargetHands(int userId, int betId){
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT hand_id FROM bets, hands_in_stage "
                    + "WHERE bets.player_id = ? AND bets.bet_id = ? AND bets.hand_in_stage_id = hands_in_stage.id;");
            ps.setInt(1, userId);
            ps.setInt(2, betId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result.add(new Integer(res.getInt("hand_id")));
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static JSONObject getBord(int gameId, int tableId){
        JSONObject js = new JSONObject();
        JSONArray result = new JSONArray();
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT card_id FROM game_stage, distribution WHERE game_stage.game_id = ? AND game_stage.table_id = ? AND game_stage.id = distribution.game_stage_id;");
            ps.setInt(1, gameId);
            ps.setInt(2, tableId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result.put(res.getInt("card_id"));
            }
            js.put("Board", result);
        } catch (SQLException | JSONException e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return js;
    }
    
    public static JSONObject getBetInfo(int betId, int userId, int stageId){
        JSONObject betInfo = new JSONObject();
        Connection con = getConnection();
        String stage = getStage(stageId);
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bets, bet_result "
                    + "WHERE bets.bet_id = ? AND bets.player_id = ? AND bets.bet_id = bet_result.bet_id_in_bets;");
            ps.setInt(1, betId);
            ps.setInt(2, userId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                betInfo.put("PlayerId", res.getInt("player_id"));
                betInfo.put("Value", res.getDouble("value"));
                betInfo.put("BetId", res.getInt("bet_id"));
                betInfo.put("Express", res.getBoolean("express"));
                betInfo.put("WinSize", res.getDouble("win_size"));
                betInfo.put("Stage", stage);
            }
        }catch ( SQLException | JSONException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);   
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return betInfo;
    }
    
    public static List<Integer> getHandInStageId(int betId, int userId){
        List<Integer> result = new ArrayList<>();
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT hand_in_stage_id FROM bets WHERE bet_id = ? AND player_id = ?");
            ps.setInt(1, betId);
            ps.setInt(2, userId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result.add(new Integer(res.getInt("hand_in_stage_id")));
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static void setWinsHand(int firstCardId, int secondCardId, int tableId, int gameId){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO wins_hands(first_card_id, second_card_id, table_id, game_id) VALUES(?, ?, ?, ?)");
            ps.setInt(1, firstCardId);
            ps.setInt(2, secondCardId);
            ps.setInt(3, tableId);
            ps.setInt(4, gameId);
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ArrayList<Hand> getWinnersHand(int gameId, int tableId){
        Connection con = getConnection();
        ArrayList<Hand> result = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM wins_hands WHERE game_id = ? AND table_id = ?;");
            ps.setInt(1, gameId);
            ps.setInt(2, tableId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Hand tmp = new Hand();
                tmp.first = res.getInt("first_card_id");
                tmp.second = res.getInt("second_card_id");
                result.add(tmp);
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static void setBetResult(int beId, double winSize){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO bet_result(bet_id_in_bets, win_size) VALUES(?, ?)");
            ps.setInt(1, beId);
            ps.setDouble(2, winSize);
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static int getBetId(int betId, int playerId){
        Connection con = getConnection();
        int result = -1;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT id FROM bets WHERE bet_id = ? AND player_id = ?");
            ps.setInt(1, betId);
            ps.setInt(2, playerId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result = res.getInt("id");
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    /*public static void generateDeck(){
        try{
            Connection c = getConnection();
            for(int i=2; i<15; i++){
                for(int j=1; j<5; j++){
                    PreparedStatement ps = c.prepareStatement("insert into cards(suits_id, dignitys_id) values(?,?)");
                    ps.setInt(1, j);
                    ps.setInt(2, i);
                    ps.execute();
                }
            }
        }catch(SQLException e){
            log.info(e.getMessage());
        }
        log.info("complite");
    }*/

    private static String getStage(int stageId) {
        String result = "";
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT name FROM stage WHERE id = ?");
            ps.setInt(1, stageId);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                result = res.getString("name");
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static int getPersent(){
        int result = 10;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT profit_persent FROM money ORDER BY id DESC LIMIT 1");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                result = res.getInt("profit_persent");
                break;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static double getTotalMoney(){
        double result = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT total_money FROM money ORDER BY id DESC LIMIT 1");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                result = res.getDouble("total_money");
                break;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static double getBalance(){
        double result = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM casino_profit ORDER BY id DESC LIMIT 1");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                result = res.getDouble("balance");
                break;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static void setBalance(double balanse){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO casino_profit(balance, profit, spare_money) VALUES(?, 0, 0)");
            ps.setDouble(1, balanse);
            ps.execute();
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
            
    public static double getProfit(){
        double result = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT profit FROM casino_profit ORDER BY id DESC LIMIT 1");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                result = res.getDouble("profit");
                break;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }       
    
    public static double getSpareMoney(){
        double result = 0;
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT spare_money FROM casino_profit ORDER BY id DESC LIMIT 1");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                result = res.getDouble("spare_money");
                break;
            }
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }    
    public static void setMoney(double money, int persent){
        Connection con = getConnection();
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO money(total_money, profit_persent, insert_date) VALUES (?, ?, ?)");
            ps.setDouble(1, money);
            ps.setInt(2, persent);
            ps.setString(3, currentTime);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
