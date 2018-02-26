/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The clockwise action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The rotate clock-wise action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class CWAction extends BoardAction {

    /** 
     *  Constructs the CWAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public CWAction(final TetrisPanel thePanel) {
        super(thePanel);
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (!myPanel.isPaused() && myPanel.isRunning()) {
            myBoard.rotateCW();
        }
    }
    
}
