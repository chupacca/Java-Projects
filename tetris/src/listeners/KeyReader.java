/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The types of class that listens for keystrokes.
 */

package listeners;

import actions.BoardAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import panels.TetrisPanel;

/**
 * The KeyReader that will listen for keystrokes and act accordingly.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class KeyReader implements KeyListener {

    /** The frame for this application's GUI. */
    //private final TetrisPanel myGamePanel;
    
    /** The board for the Tetris Game. */
    //private final Board myBoard;
    
    /** The integer that is associated with some key and a type of 
     * BoardAction. */
    private Map<Integer, BoardAction> myActionMap;
    
    /**
     * The constructor for the KeyReader.
     * @param theGamePanel (the TetrisPanel object so I can make changes to 
     * the game from here).
     */
    public KeyReader(final TetrisPanel theGamePanel) {
        //myGamePanel = theGamePanel;
        //myBoard = theGamePanel.getBoard();
        myActionMap = theGamePanel.getContActMap();
    }
    
    @Override
    public void keyPressed(final KeyEvent theEvent) {
        final BoardAction action = 
                        myActionMap.get(theEvent.getKeyCode());
        if (action != null) {
            action.move();
        }
    }
    
    /**
     * Sets the action map. 
     * @param theActionMap (the map I want to set to this KeyReader).
     * */
    public void setActionMap(final Map<Integer, BoardAction> theActionMap) {
        final Map<Integer, BoardAction> map = new HashMap<Integer, BoardAction>();
        map.putAll(theActionMap);
        myActionMap = map;
    }
    
    @Override
    public void keyReleased(final KeyEvent theEvent) {
        //System.out.println("Released");
        
    }

    @Override
    public void keyTyped(final KeyEvent theEvent) {
        //System.out.println("Typed");
        
    }

}
