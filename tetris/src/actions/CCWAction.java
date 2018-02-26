/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The counter-clockwise action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The rotate counter-clockwise action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class CCWAction extends BoardAction {

    /** 
     *  Constructs the CCWAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public CCWAction(final TetrisPanel thePanel) {
        super(thePanel);
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (!myPanel.isPaused() && myPanel.isRunning()) {
            myBoard.rotateCCW();
        }
    }

}
