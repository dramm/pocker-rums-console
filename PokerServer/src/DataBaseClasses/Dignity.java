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
    private int pover;
    private String name;
    private String shortName;
    public Dignity(){
        this.id = 0;
        this.pover = 0;
        this.name = "";
        this.shortName = "";
    }
    public Dignity(int id, int pover, String name, String shortName){
        this.id = id;
        this.pover = pover;
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
    public int getPover() {
        return pover;
    }

    /**
     * @param pover the pover to set
     */
    public void setPover(int pover) {
        this.pover = pover;
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
