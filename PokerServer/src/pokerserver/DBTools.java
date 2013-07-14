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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class DBTools {
    
    private static Logger log = Logger.getLogger(DBTools.class.getName());
    
    private static Connection getConnection(){
        try{
            //log.info("connect to data base");
            return DriverManager.getConnection(ServerConf.getDBUrl(),
                    ServerConf.getDBUser(), ServerConf.getDBPasswd());
            
        }
        catch(Exception e){
            log.info(e.getMessage());
            return null;
        }
    }
    
    public static Suits getSuits(int id){
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from suits where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Suits(rs.getInt("id"), rs.getString("name"));
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }
        //log.info("return null");
        return null;
    }
    public static Dignity getDignity(int id){
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from dignitys where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Dignity(rs.getInt("id"), rs.getInt("power"), rs.getString("name"), rs.getString("short_name"));
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }
        //log.info("return null");
        return null;
    }
    public static ArrayList<Cards> getCards(){
        ArrayList<Cards> deck = new ArrayList<>();
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from cards");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                deck.add(new Cards(rs.getInt("id"), rs.getInt("suits_id"), rs.getInt("dignitys_id")));
            }
            return deck;
        }catch(SQLException e){
            log.log(Level.INFO, "SQL {0}", e.getMessage());
        }
        //log.info("return null");
        return null;
    }
    public static void generateDeck(){
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
    }
}
