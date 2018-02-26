/*
 * TCSS 305 - Assignment 4: PowerPaint
 * 
 * Runs the PowerPaintGUI.
 */

package gui;

import java.awt.EventQueue;

/**
 * Runs PowerPaint by instantiating and starting the PowerPaintGUI.
 * 
 * @author Peter M Chu
 * @version 05/03/2016
 */
public final class PowerPaintMain {
    
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private PowerPaintMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the PowerPaintGUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PowerPaintGUI().start();
            }
        });
    }
}
