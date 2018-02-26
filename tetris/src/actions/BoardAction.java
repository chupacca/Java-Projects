/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The types of actions for the board.
 */

package actions;

import model.Board;
import panels.TetrisPanel;

/**
 * The Actions to utlizie the board methods.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class BoardAction  {
    
    /** The board that the tetris game is using. */
    protected final Board myBoard;
    
    /** The panel that the tetris panel is being played. */
    protected final TetrisPanel myPanel;
    
    
    
    /** 
     * This class is not meant to be instantiated. However, I cannot make an array of
     *  abstract objects, so I made this class to hold common code for child classes.
     *  Also makes a myBoard class.
     *  @param thePanel (TetrisPanel that is being played).
     */
    protected BoardAction(final TetrisPanel thePanel) {
        super();
        myPanel = thePanel;
        myBoard = thePanel.getBoard();
    }
    
    /** This board method doesn't do anything, but is here for polymorphism purposes. */
    public void move() {
        throw new IllegalStateException();
    }

}
