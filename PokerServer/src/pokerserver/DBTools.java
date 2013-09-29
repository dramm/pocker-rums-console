/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Cards;
import DataBaseClasses.Dignity;
import DataBaseClasses.Suits;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class DBTools {
    
    private static final Logger log = Logger.getLogger(DBTools.class.getName());
    
    private static Connection getConnection(){
        try{
            //log.info("connect to data base");
            //i++;
           // log.log(Level.INFO, "{0} ", i);
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
    
    public static void setHand( Cards[] cards){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("insert into hands(first_card_id, second_card_id) values(?,?)");
            ps.setInt(1, cards[0].getId());
            ps.setInt(2, cards[1].getId());
            ps.execute();
        }catch(SQLException ex){
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
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
    
    public static int getGameId(String uuid){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("select id from game_n where table_id = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt("id");
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
    
    public static void setGameStage(int stage, int gameId){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into game_stage(stage_id, game_id) values(?,?)");
            ps.setInt(1, stage);
            ps.setInt(2, gameId);
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
    
    public static void setDistribution(Cards card, int gameId, int stage){
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into distribution(card_id, game_stage_id) values(?, ?)");
            ps.setInt(1, card.getId());
            ps.setInt(2, getGameStageId(gameId, stage));
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
    public static int getLastGameId(){
        Connection con = getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("select max(id) as val from game");
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
}
