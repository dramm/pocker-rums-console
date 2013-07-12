/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class Hands {
    private int id;
    private int firstCardId;
    private int secondCardId;
    public Hands(){
        id=0;
        firstCardId=0;
        secondCardId=0;
    }
    public Hands(int id, int firstCardId, int secondCardId){
        this.id=id;
        this.firstCardId=firstCardId;
        this.secondCardId=secondCardId;
    }
}
