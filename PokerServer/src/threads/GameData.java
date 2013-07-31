/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

/**
 *
 * @author Андрей
 */
public class GameData {
    private int[][] handCards;
    private int[] board;
    private int stage;

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

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
    
}
