package pokerserver;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class ServerConf {
    private static  ServerConf config = null;
    
    private Properties prop;
    
    private static Logger log = Logger.getLogger(ServerConf.class.getName());
    
    protected ServerConf(){
        prop = new Properties();
        try{
            FileInputStream stream = new FileInputStream("config.conf");
            prop.load(stream);
        }
        catch (Exception e){
            log.info(e.getMessage());
        }
    }
    
    public static ServerConf getServerConf(){
        if(config == null){
            config = new ServerConf();
        }
        return config;
    }
    
    public static String getDBUrl(){
        ServerConf sc = getServerConf();
        return sc.prop.getProperty("db_url");
    }
    public static String getDBUser(){
        ServerConf sc = getServerConf();
        return sc.prop.getProperty("db_user");
    }
    public static String getDBPasswd(){
        ServerConf sc = getServerConf();
        return sc.prop.getProperty("db_passwd");
    }
}
