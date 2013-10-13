/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

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
                    switch (Bridge.newData.getComand()) {
                        case 1500:{
                            writeStream(Bridge.newData.getComand());
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1510:{
                            writeStream(Bridge.newData.getComand());
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1520:{
                            writeStream(Bridge.newData.getComand());
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1530:{
                            writeStream(Bridge.newData.getComand());
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1540:{
                            writeStream(Bridge.newData.getComand());
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1550:{
                            writeStream(Bridge.newData.getComand());
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1560:{
                            writeStream(Bridge.newData.getComand(), true);
                            Bridge.newData.setFlag(false);
                            break;
                        }
                        case 1570:{
                            //отправить статистику по игре 
                        }
                    }

                }
                Thread.sleep(200);
            }
            
        }catch ( IOException | InterruptedException ex) {
            Logger.getLogger(SpeakerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeStream(int command) throws IOException{
        output.write(Functions.intToByteArray(command));
        output.write(Functions.intToByteArray(Bridge.newData.js.toString().length()));
        output.write(Xor.encode(Bridge.newData.js.toString().getBytes()));
        output.flush();
    }
    
    private void writeStream(int command, boolean flag) throws IOException{
        output.write(Functions.intToByteArray(command));
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
