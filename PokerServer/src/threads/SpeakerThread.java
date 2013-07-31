/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Андрей
 */
public class SpeakerThread extends Thread{
    private Socket sc = null;
    private OutputStream output = null;
    
    /**
     *
     */
    @Override
    public void run(){
        try {
            output = sc.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            if(Bridge.data.getStage() == 1){
                int [][] cards = Bridge.data.getHandCards();
                JSONObject js = new JSONObject();
                for (int i = 0; i < cards.length; i++) {
                    JSONArray jsArr = new JSONArray();
                    jsArr.put(cards[i][0]);
                    jsArr.put(cards[i][1]);
                    try {
                        js.append("player"+i, jsArr);
                    } catch (JSONException ex) {
                        Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                writeCommand(1500);
                writeJson(js.toString());
                Bridge.data.setStage(-1);

            }
        }
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }
    private void writeCommand(int command){
        try {
            output.write(Functions.intToByteArray(command), 0, 4);
        } catch (IOException ex) {
            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void writeJson(String json){
        try {
            writeCommand(json.length());
            output.write(json.getBytes(), 0, json.getBytes().length);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getSc() {
        return sc;
    }

    public void setSc(Socket sc) {
        this.sc = sc;
    }
}
