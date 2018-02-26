/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The right action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The right action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class RightAction extends BoardAction {

    /** 
     *  Constructs the RightAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public RightAction(final TetrisPanel thePanel) {
        super(thePanel);
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (!myPanel.isPaused() && myPanel.isRunning()) {
            myBoard.right();
        }
    }
    
}
