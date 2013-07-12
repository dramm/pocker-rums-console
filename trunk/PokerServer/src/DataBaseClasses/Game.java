/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

import java.util.Date;

/**
 *
 * @author Андрей
 */
public class Game {
    private int id;
    private Date startDate;
    public Game(){
        id=0;
        startDate = new Date();
    }
    
    public Game(int id, Date startDate){
        this.id=id;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
}
