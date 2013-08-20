/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import static Enums.GameStages.Stage.PREFLOP;
import Enums.Xor;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Андрей
 */
public class SpeakerThread extends Thread{
    private OutputStream output = null;
    private boolean flag = true;
    
    /**
     * 
     *
     */
    @Override
    public void run(){
        try{
            while (isFlag()) {
                if(Bridge.newData.isFlag()){
                    switch (Bridge.newData.getGameStage()) {
                        case STARTING:{
                            writeStream(1500);
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case PREFLOP:{
                            writeStream(1510);
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case FLOP:{
                            writeStream(1520);
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case TURN:{
                            writeStream(1530);
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case RIVER:{
                            writeStream(1540);
                            Bridge.newData.setFlag(false);
                            break;
                        }
                    }

                }
                Thread.sleep(10);
            }
            
        }catch (IOException ex) {
            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeStream(int command) throws IOException{
        output.write(Functions.intToByteArray(command));
        output.write(Functions.intToByteArray(Bridge.newData.js.toString().length()));
        output.write(Xor.encode(Bridge.newData.js.toString().getBytes()));
        output.flush();
    }
    
    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = new BufferedOutputStream(output);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
