/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseClasses;

/**
 *
 * @author Андрей
 */
public class Distribution {
    private int cardId;
    private int gameStageId;
    public Distribution(){
        this.cardId = 0;
        this.gameStageId = 0;
    }
    public Distribution(int cardId, int gameStageId){
        this.cardId = cardId;
        this.gameStageId = gameStageId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getGameStageId() {
        return gameStageId;
    }

    public void setGameStageId(int gameStageId) {
        this.gameStageId = gameStageId;
    }
}
