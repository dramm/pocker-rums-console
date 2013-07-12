/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class HandsInStage {
    private int id;
    private int stageGameId;
    private int handId;
    private float factor;
    public HandsInStage(int id, int stageGameId, int handId, float factor){
        this.id=id;
        this.stageGameId=stageGameId;
        this.handId=handId;
        this.factor =factor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStageGameId() {
        return stageGameId;
    }

    public void setStageGameId(int stageGameId) {
        this.stageGameId = stageGameId;
    }

    public int getHandId() {
        return handId;
    }

    public void setHandId(int handId) {
        this.handId = handId;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }
}
