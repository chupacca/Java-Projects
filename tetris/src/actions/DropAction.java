/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The drop action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The drop action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class DropAction extends BoardAction {

    /** 
     *  Constructs the DropAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public DropAction(final TetrisPanel thePanel) {
        super(thePanel);
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (!myPanel.isPaused() && myPanel.isRunning()) {
            myBoard.drop();
        }
    }
    
}
