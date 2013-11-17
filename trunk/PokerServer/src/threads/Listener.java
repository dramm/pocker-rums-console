/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import Enums.Xor;
import PokerEngyne.Bet;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pokerserver.DBTools;
import pokerserver.Game_new;

/**
 *
 * @author Андрей
 */
public class Listener extends Thread{
    private Socket clientSocket = null;
    private InputStream input = null;
    private Game_new game;
    public Listener() {
    this.game = new Game_new();
    }
    @Override
    public void run(){
        SpeakerThread sp = new SpeakerThread();
        try {
            Thread th = new Thread(game);
            sp.setOutput(clientSocket.getOutputStream());
            input = new BufferedInputStream(clientSocket.getInputStream());
            int flag = 1;
            while (flag > 0) {
                byte[] command = new byte[4];
                flag = input.read(command, 0, 4); 
                int result = Functions.byteArrayToInt(command);
                switch (result) {
                    case 1000:{//стартуем сервер
                        System.out.println("Starting game");
                        if(!game.isRun()){
                            th.start();
                            sp.start();
                        }
                        break;
                    }
                    case 1010:{//следующий раунд
                        if(!Bridge.newData.isGoNext()){
                            Bridge.newData.setGoNext(true);
                        }
                        break;
                    }
                    case 1020:{//принимаю ставку
                        byte[] len = new byte[4];
                        flag = input.read(len, 0, 4);
                        byte[] message = new byte[Functions.byteArrayToInt(len)];
                        flag = input.read(message, 0, Functions.byteArrayToInt(len));
                        getBets(new String(Xor.encode(message)));
                        Bridge.newData.setComand(1560);
                        Bridge.newData.setFlag(true);
                        break;
                    }
                    case 1030:{//команда для статистики {GameId=200,UserId=1}
                        class RunStat implements Runnable{
                            JSONObject js;
                            public RunStat(JSONObject tmp) {
                                js = tmp;
                            }
                            
                            @Override
                            public void run() {
                                try {
                                    JSONObject res = DBTools.getStatistics(js.getInt("BetId"), js.getInt("UserId"));
                                    Bridge.newData.js = res;
                                    Bridge.newData.setComand(1570);
                                    Bridge.newData.setFlag(true);
                                    System.out.println(res.toString());
                                } catch (JSONException ex) {
                                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                        }
                        System.out.println("Get Statistics");
                        byte[] len = new byte[4];
                        flag = input.read(len, 0, 4);
                        byte[] message = new byte[Functions.byteArrayToInt(len)];
                        flag = input.read(message, 0, Functions.byteArrayToInt(len));
                        System.out.println(new String(Xor.encode(message)));
                        JSONObject tmp = new JSONObject(new String(Xor.encode(message)));
                        Thread t = new Thread(new RunStat(tmp));
                        t.start();
                        //System.out.println(DBTools.getStatistics(tmp.getInt("BetId"), tmp.getInt("UserId")));
                        //Bridge.newData.js = DBTools.getStatistics(tmp.getInt("BetId"), tmp.getInt("UserId"));
                        //Bridge.newData.setComand(1570);
                        //Bridge.newData.setFlag(true);
                        break;
                    }
                    case 1040:{//закрывается прием ставок
                        System.out.println("Bet closed");
                        break;
                    }
                    case 1050:{//при подключении клиент присылает сумму всех депозитов
                        //{"summ":1665.2}
                        byte[] len = new byte[4];
                        flag = input.read(len, 0, 4);
                        byte[] message = new byte[Functions.byteArrayToInt(len)];
                        flag = input.read(message, 0, Functions.byteArrayToInt(len));
                        JSONObject pack = new JSONObject(new String(Xor.encode(message)));
                        double money = pack.getDouble("summ");
                        setMoney(money, 15);
                        System.out.println(pack.toString());
                        break;
                    }
                    case 1060:{//пользователь пополнил счет
                        break;
                    }
                    case 1070:{//вывод пользовательских средств
                        break;
                    }
                    case 1080:{//списание прибыли казино
                        break;
                    }
                }
            }
            clientSocket.close();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                game.setRun(false);
                sp.setFlag(false);
                
        }
    }
    
    private void setMoney(double money, int persent){
        double balance = DBTools.getBalance();
        double profit = DBTools.getProfit();
        double spareMoney = DBTools.getSpareMoney();
        if(balance == 0){
            DBTools.setMoney(money, 15);
            DBTools.setBalance(money);
        }
        if((balance + profit + spareMoney) == money){
            DBTools.setMoney(money, 15);
            //DBTools.setBalance(money);
        }else{
            
        }
        
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    private void getBets(String json) throws JSONException{
        JSONArray arr = new JSONArray(json);
        if(arr.length() > 0){
            System.out.println("Users Bets");
            for (int i = 0; i < arr.length(); i++) {
                System.out.println(arr.get(i).toString());
                Bet bet = new Bet(arr.getJSONObject(i));
                game.bets.addBet(bet);
                for (int j = 0; j < bet.getHandsId().size(); j++) {
                    int tableId = bet.getHandsId().get(j).intValue() / 10;
                    int handId = bet.getHandsId().get(j).intValue() % 10;
                    int handInStage = game.getTables()[tableId].getPlayers()[handId].getHandInStageId();
                    DBTools.setBet(handInStage, bet.getUserId(), bet.getBetSize(), bet.getBetId(), bet.isExpress());
                }
            }
        }
    }
}
