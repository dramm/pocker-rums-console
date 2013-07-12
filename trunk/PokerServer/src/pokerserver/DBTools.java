/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerserver;

import DataBaseClasses.Dignity;
import DataBaseClasses.Suits;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            log.info("connect to data base");
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
}
