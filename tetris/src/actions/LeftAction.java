/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The left action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The left action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class LeftAction extends BoardAction {

    /** 
     *  Constructs the LeftAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public LeftAction(final TetrisPanel thePanel) {
        super(thePanel);
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (!myPanel.isPaused() && myPanel.isRunning()) {
            myBoard.left();
        }
    }
    
}
