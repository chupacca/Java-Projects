/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The BoardAction array for the Tetris Game keys.
 */

package backend;

import actions.BoardAction;
import actions.CCWAction;
import actions.CWAction;
import actions.DownAction;
import actions.DropAction;
import actions.LeftAction;
import actions.PauseAction;
import actions.RightAction;

import panels.TetrisPanel;

/**
 * The class that constructs a BoardAction array.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class ActionMap {
    
    /** The BoardAction array with the BoardActions connected with myPanel. */
    private final BoardAction[] myActions;
    
    /**
     * Constructs the ActionMap.
     * @param thePanel (the TetrisPanel this class uses).
     */
    public ActionMap(final TetrisPanel thePanel) {
        final BoardAction[] actions = {new DownAction(thePanel), new LeftAction(thePanel), 
                                       new RightAction(thePanel), new CCWAction(thePanel),
                                       new CWAction(thePanel), new DropAction(thePanel),
                                       new PauseAction(thePanel)};
        myActions = (BoardAction[]) actions.clone();
    }
    
    /**
     * Returns the BoardAction array.
     * @return a BoardAction array with the Board Actions.
     */
    public BoardAction[] getActionMap() {
        
        return (BoardAction[]) myActions.clone();
    }
    
}
