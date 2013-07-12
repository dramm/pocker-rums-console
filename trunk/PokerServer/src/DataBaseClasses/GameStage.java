/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class GameStage {
    private int id;
    private int stageId;
    private int gameId;
    public GameStage(){
        id=0;
        stageId=0;
        gameId=0;
    }
    public GameStage(int id, int stageId, int gameId){
        this.id=id;
        this.stageId=stageId;
        this.gameId=gameId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
