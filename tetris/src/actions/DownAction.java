/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The down action for the board.
 */

package actions;

import panels.TetrisPanel;

/**
 * The down action for the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class DownAction extends BoardAction {

    /** 
     *  Constructs the DownAction.
     *  @param thePanel (TetrisPanel that is being played).
     */
    public DownAction(final TetrisPanel thePanel) {
        super(thePanel);
    }
    
    /** Makes a move on the board. */
    public void move() {
        if (!myPanel.isPaused() && myPanel.isRunning()) {
            myBoard.down();
        }
    }
    
}
