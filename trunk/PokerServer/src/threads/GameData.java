/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import Enums.GameStages.Stage;

/**
 *
 * @author Андрей
 */
public class GameData {
    private int[][] handCards;
    private int[] board;
    private Stage stage;
    private boolean flag;
    private boolean goNext;
    private String tableName;

    public GameData() {
        this.stage = Stage.STARTING;
    }

    public int[][] getHandCards() {
        return handCards;
    }

    public void setHandCards(int[][] handCards) {
        this.handCards = handCards;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public synchronized boolean isFlag() {
        return flag;
    }

    public synchronized void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public synchronized boolean isGoNext() {
        return goNext;
    }

    public synchronized void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }
    
}
