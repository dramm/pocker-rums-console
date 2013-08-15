/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import static Enums.GameStages.Stage.SHOWDOWN;
import Enums.Xor;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Андрей
 */
public class SpeakerThread extends Thread{
    private OutputStream output = null;
    
    /**
     * 
     *
     */
    @Override
    public void run(){
        while (true) {
            if(Bridge.data.isFlag()){
                switch (Bridge.data.getStage()) {
                    case PREFLOP:{
                        try {
                            System.out.println("PREFLOP");
                            int [][] cards = Bridge.data.getHandCards();
                            JSONObject js = new JSONObject();
                            for (int i = 0; i < cards.length; i++) {
                                js.append("Player"+i, cards[i][0]);
                                js.append("Player"+i, cards[i][1]);
                            }
                            js.put("Stage", Bridge.data.getStage().toString());
                            js.put("Table", Bridge.data.getTableName());
                            output.write(Functions.intToByteArray(1500));
                            output.write(Functions.intToByteArray(js.toString().length()));
                            output.write(Xor.encode(js.toString().getBytes()));
                            output.flush();
                            System.out.println(js.toString());
                            Bridge.data.setFlag(false);
                        } catch (IOException | JSONException ex) {
                            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Preflop");
                            Bridge.data.setFlag(false);
                        }
                        break;
                    }
                    case SHOWDOWN:{
                        try{
                            System.out.println("SHOWDOWN");
                            JSONObject js = new JSONObject();
                            js.put("Stage", Bridge.data.getStage().toString()); 
                            js.put("Table", Bridge.data.getTableName());
                            js.put("Winner", "Player 1");
                            js.put("Combination", "FlushRoyal");
                            output.write(Functions.intToByteArray(1510));
                            output.write(Functions.intToByteArray(js.toString().length()));
                            output.write(Xor.encode(js.toString().getBytes()));
                            output.flush();
                            System.out.println(js.toString());
                            Bridge.data.setFlag(false);
                        }catch(IOException | JSONException ex){
                            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
                            Bridge.data.setFlag(false);

                        }
                        break;
                    }
                    case STARTING:{
                        try{
                            System.out.println("STARTING");
                            JSONObject js = new JSONObject();
                            js.put("Stage", Bridge.data.getStage().toString()); 
                            js.put("Round", "1");
                            output.write(Functions.intToByteArray(1520));
                            output.write(Functions.intToByteArray(js.toString().length()));
                            output.write(Xor.encode(js.toString().getBytes()));
                            output.flush();
                            System.out.println(js.toString());
                            Bridge.data.setFlag(false);
                        }catch(IOException | JSONException ex){
                            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
                            Bridge.data.setFlag(false);
                        }
                        break;
                    }
                    default:{
                        try{
                            System.out.println("DEFOLT");
                            int[] cards = Bridge.data.getBoard();
                            JSONObject js = new JSONObject();
                            for (int i = 0; i < cards.length; i++) {
                                js.append("Board", cards[i]);
                            }
                            js.put("Stage", Bridge.data.getStage().toString()); 
                            js.put("Table", Bridge.data.getTableName());
                            output.write(Functions.intToByteArray(1510));
                            output.write(Functions.intToByteArray(js.toString().length()));
                            output.write(Xor.encode(js.toString().getBytes()));
                            output.flush();
                            System.out.println(js.toString());
                            Bridge.data.setFlag(false);
                        }catch(IOException | JSONException ex){
                            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Defolt");
                        }
                        break;
                    }
                }
                    
            }
        }
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = new BufferedOutputStream(output);
    }
}
