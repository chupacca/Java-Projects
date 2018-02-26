/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The Frame for the Tetris Game.
 */

package backend;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import listeners.KeyReader;
import panels.TetrisPanel;

/**
 * The a version of JFrame I want to use.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class TetrisFrame extends JFrame implements ComponentListener, PropertyChangeListener {
    
    /** Generated serial number. */
    private static final double MARGIN_OF_ERROR = 0.05;
    
    /** Generated serial number. */
    private static final long serialVersionUID = 7628512233200678049L;
    
    /** The panels that show the pieces being played. */
    private final JPanel myPrevNIntr;
    
    /** The panels that show the pieces being played. */
    private final JPanel myGamePanels;
    
    /** The index of the first player's KeyReader. */
    private int myFKeyReaderIdx;
    
    /** The index of the second player's KeyReader. */
    private int mySKeyReaderIdx;
    
    /** The consistent height adjuster. */
    private double myLBound; // = 1.95;
    
    /** The consistent width adjuster. */
    private double myHBound; // = 2.10;
    
    /**
     * Constructs the TetrisFrame by calling super and connecting the component listener.
     * @param thePrevNInstr (the panel with the instructions and the previews).
     * @param theGamePanels (The panels that show the pieces being played).
     * @param theGamePanel (One of the panels a tetris game is being played on).
     */
    public TetrisFrame(final JPanel thePrevNInstr, 
                final JPanel theGamePanels, final TetrisPanel theGamePanel) {
        super();
        myFKeyReaderIdx = 0;
        mySKeyReaderIdx = 1;
        myPrevNIntr = thePrevNInstr;
        myGamePanels = theGamePanels;
        
        setRatio(theGamePanel.getBoardWidth(), theGamePanel.getBoardHeight());
                        
        this.addComponentListener(this);
    }
    
    /**
     * Set up the ratio bounds for the this panel.
     * @param theGamePanelWidth (width for the game board).
     * @param theGamePanelHeight (height for the game board).
     */
    public final void setRatio(final int theGamePanelWidth, final int theGamePanelHeight) {
        myLBound = theGamePanelHeight / theGamePanelWidth - MARGIN_OF_ERROR;
        myHBound = theGamePanelHeight / theGamePanelWidth + MARGIN_OF_ERROR;
    }

    @Override
    public void componentHidden(final ComponentEvent theArg) {
        // Doesn't do anything. But had to make the method when implementing ComponentListener.
        
    }

    @Override
    public void componentMoved(final ComponentEvent theArg) {
        // Doesn't do anything. But had to make the method when implementing ComponentListener.
    }

    @Override
    public void componentResized(final ComponentEvent theArg) {
        
        //width of other frames 100
        //final int height = myGamePanels.getHeight();
        //final int width = myGamePanels.getWidth();
        //System.out.println("Panels w " + myGamePanels.getWidth() + " " 
                           //+ myGamePanels.getHeight());

        final Dimension frameD = this.getContentPane().getSize(); 
        //final int tempHeight = (int) frameD.getHeight();
        final int tempWidth = (int) frameD.getWidth();
        //System.out.println("Frame: w " + tempWidth + " " + tempHeight);
        
        //Toolbar is about 20 for height
        
        final int maxHeight = myGamePanels.getHeight();
        final int maxWidth = (tempWidth - myPrevNIntr.getWidth()) / 2;
        
        //System.out.println("Work:  w" + desiredW + "  h" + maxHeight);
        final boolean flag = findRatio(maxWidth, maxHeight, 'h');
        
        /*
        for (int i = maxHeight; i > 1; i--) {
            final double ratio = (i + 0.0) / maxWidth;
            //System.out.println(ratio);
            if (ratio > L_BOUND && ratio < H_BOUND) {
                flag = false;
                //System.out.println("Fire: w " + desiredW + "  " + i);
                final int[] hWRC = {maxWidth, i}; //WIDTH, HEIGHT
                myGamePanels.setSize(new Dimension(maxWidth * 2, i));
                firePropertyChange("resize", null, (int[]) hWRC.clone());
                //this.setSize(desiredW * 2 + myPrevNIntr.getWidth(), maxHeight);
                break;
            }
        }*/
        if (!flag) { 
            
            findRatio(maxHeight, maxWidth, 'w');
            /*
            for (int i = maxWidth; i > 1; i--) {
                final double ratio = (maxHeight + 0.0) / i;
                if (ratio > L_BOUND && ratio < H_BOUND) {
                    System.out.println("Found");
                    final int[] hWRC = {i, maxHeight}; //WIDTH, HEIGHT
                    myGamePanels.setSize(new Dimension(i * 2, maxHeight));
                    firePropertyChange("resize", null, (int[]) hWRC.clone());
                    break;
                }
            }*/
            
        }
        
        myGamePanels.setSize(new Dimension(tempWidth - myPrevNIntr.getWidth(), maxHeight));
        
    }
    
    /**
     * This method finds a 2:1 ratio between height and width.
     * @param theMaxNum (the number I don't want to change).
     * @param theChangeNum (the number I do want to change).
     * @param theHeightorWidth (a character to let me know if I am looking for 
     * height or width).
     * @return a boolean (lets me know if this process was completed or not).
     */
    private boolean findRatio(final int theMaxNum, final int theChangeNum, 
                               final char theHeightorWidth) {
        boolean completed = false;
        for (int i = theChangeNum; i > 1; i--) {
            double ratio = 0;
            if (theHeightorWidth == 'h') {
                ratio = (i + 0.0) / theMaxNum;
            } else {
                ratio = (theMaxNum + 0.0) / i;
            }
            if (ratio > myLBound && ratio < myHBound) {
                final String resize = "resize";
                if (theHeightorWidth == 'h') {
                    completed  = true;
                    final int[] hWRC = {theMaxNum, i}; //WIDTH, HEIGHT
                    myGamePanels.setSize(new Dimension(theMaxNum * 2, i));
                    firePropertyChange(resize, null, (int[]) hWRC.clone());
                } else {
                    final int[] hWRC = {i, theMaxNum}; //WIDTH, HEIGHT
                    myGamePanels.setSize(new Dimension(i * 2, theMaxNum));
                    firePropertyChange(resize, null, (int[]) hWRC.clone());
                    break;
                } 
            }
        }
        return completed;
    }
    
    @Override
    public void componentShown(final ComponentEvent theArg) {
        // Doesn't do anything. But had to make the method when implementing ComponentListener.
        
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        /** The string to show in the games that it is the first player. */
        //private static final String FIRST = "First Player";
        
        /** The string to show in the games that it is the second player. */
        //private static final String SECOND = "Second Player";
        
        
        //this.addKeyListener((KeyListener) theEvent.getNewValue());
        
        
        if ("First Player".equals(theEvent.getPropertyName())) {
            
            this.removeKeyListener(this.getKeyListeners()[myFKeyReaderIdx]);
            
            final KeyReader kR = (KeyReader) theEvent.getNewValue();
            
            this.addKeyListener(kR);
            if (myFKeyReaderIdx == 0) {
                myFKeyReaderIdx = 1;
                mySKeyReaderIdx = 0;
            } else {
                myFKeyReaderIdx = 1;
                mySKeyReaderIdx = 0;
            }
            
        } else if ("Second Player".equals(theEvent.getPropertyName())) {
            this.removeKeyListener(this.getKeyListeners()[mySKeyReaderIdx]);
            
            final KeyReader kR = (KeyReader) theEvent.getNewValue();
            
            this.addKeyListener(kR);
            
            if (mySKeyReaderIdx == 0) {
                mySKeyReaderIdx = 1;
                myFKeyReaderIdx = 0;
            } else {
                mySKeyReaderIdx = 1;
                myFKeyReaderIdx = 0;
            }
        }
        
    }
    
}
