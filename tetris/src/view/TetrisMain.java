/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * Runs the TetrisGUI.
 */

package view;

import java.awt.EventQueue;

/**
 * Runs PowerPaint by instantiating and starting the PowerPaintGUI.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public final class TetrisMain {
    
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the TetrisGUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
}
