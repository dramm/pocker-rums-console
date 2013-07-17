/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class Cards implements Comparable{
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
    @Override
    public int compareTo(Object obj){
        Cards tmp = (Cards)obj;
        if(this.dignitysId < tmp.dignitysId){
            return -1;
        }
        else if(this.dignitysId > tmp.dignitysId){
            return 1;
        }
        return 0;
    }
    public boolean isMore(Cards card){
        if(this.dignitysId > card.getDignitysId()){
            return true;
        }
        return false;
    }
    public boolean isLess(Cards card){
        return !isMore(card);
    }
}
