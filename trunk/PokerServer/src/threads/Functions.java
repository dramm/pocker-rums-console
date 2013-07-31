/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.nio.ByteBuffer;

/**
 *
 * @author Андрей
 */
public class Functions {
    public static int byteArrayToInt( byte [] bytes){
        ByteBuffer bbuf = ByteBuffer.wrap(bytes);
        return bbuf.getInt();
    }
    public static byte[] intToByteArray(int value){
        ByteBuffer bbuf = ByteBuffer.allocate(4);
        bbuf.putInt(value);
        return bbuf.array();
    }
    
}
