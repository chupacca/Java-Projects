/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The pause action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The pause action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class PauseAction extends BoardAction {
    
    /** The integer that represents which move to make (0 for pause and 1 for un-pause). */
    private int myMoveNote;
    
    /** 
     *  Constructs the PauseAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public PauseAction(final TetrisPanel thePanel) {
        super(thePanel);
        myMoveNote = 0;
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (myMoveNote == 0 && myPanel.isRunning()) {
            myMoveNote = 1;
            myPanel.stop();
            myPanel.setIsPaused(true);
            myPanel.repaint();
        } else if (myMoveNote == 1 && myPanel.isPaused()) {
            myPanel.setIsPaused(false);
            myPanel.repaint();
            myMoveNote = 0;
            myPanel.getTimer().start();
        }
    }
    
}