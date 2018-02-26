/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * An action listener that makes a move.
 */

package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Board;

/**
 * A class that listens for timer events and moves the shape, checking for
 * the window boundaries and changing direction as appropriate.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class MoveListener implements ActionListener {
    
    /** The board I am manipulating. */
    private final Board myBoard;
    
    /** The board I am manipulating. */
    private final Board mySecondBoard;
    
    /** Constructs the MoveListener. 
     *  @param theBoard (the tetris board I will be manipulating).
     *  @param theSecondBoard (the second tetris board I will be manipulating).
     */
    public MoveListener(final Board theBoard, final Board theSecondBoard) {
        myBoard = theBoard;
        mySecondBoard = theSecondBoard;
    }
    
    @Override
    public void actionPerformed(final ActionEvent theArgument) {
        myBoard.step();
        mySecondBoard.step();
    }

}
