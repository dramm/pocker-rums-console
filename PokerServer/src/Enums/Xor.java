/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author Андрей
 */
public class Xor {
    public static byte[] encode(byte[] message){
        byte[] result = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            result[i] = (byte)(message[i] ^ XorKey.key[i % XorKey.key.length]);
        }
        return result;
    }
}
