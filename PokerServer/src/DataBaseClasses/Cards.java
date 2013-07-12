/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class Cards {
    private int id;
    private int suitsId;
    private int dignitysId;
    public Cards(){
        this.id = 0;
        this.suitsId = 0;
        this.dignitysId = 0;
    }
    public Cards(int id, int suitsId, int dignitysId){
        this.id = id;
        this.suitsId = suitsId;
        this.dignitysId = dignitysId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setSuitsId(int suitsId){
        this.suitsId = suitsId;
    }
    public int getSuitsId(){
        return this.suitsId;
    }
    public void setDignitysId(int dignitysId){
        this.dignitysId = dignitysId;
    }
    public int getDignitysId(){
        return this.dignitysId;
    }
}
