/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class Dignity {
    private int id;
    private int power;
    private String name;
    private String shortName;
    public Dignity(){
        this.id = 0;
        this.power = 0;
        this.name = "";
        this.shortName = "";
    }
    public Dignity(int id, int pover, String name, String shortName){
        this.id = id;
        this.power = pover;
        this.name = name;
        this.shortName = shortName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the pover
     */
    public int getPower() {
        return power;
    }

    /**
     * @param pover the pover to set
     */
    public void setPower(int pover) {
        this.power = pover;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
