/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * The JMenuBar for the tetris game.
 */

package backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import listeners.KeyReader;
import panels.ControlPanel;
import panels.TetrisPanel;

/**
 * The a version of JMenuBar I want to use in my Tetris Game.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class FileMenu extends JMenu implements PropertyChangeListener {
    
    /** The list of integers for values for the first player's original. */
    private static final int[] FP_ORIGINAL_VALUES = {KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, 
                                                     KeyEvent.VK_RIGHT, KeyEvent.VK_M, 
                                                     KeyEvent.VK_UP, KeyEvent.VK_SPACE,
                                                     KeyEvent.VK_P};
    
    /** The list of integers for values for second player's original. */
    private static final int[] SP_ORIGINAL_VALUES = {KeyEvent.VK_S, KeyEvent.VK_A, 
                                                     KeyEvent.VK_D, KeyEvent.VK_Q, 
                                                     KeyEvent.VK_W, KeyEvent.VK_E, 
                                                     KeyEvent.VK_G};
    
    /** The list of integers for values for first player's alternative. */
    private static final int[] FP_ALT_VALUES = {KeyEvent.VK_K, KeyEvent.VK_J, 
                                                KeyEvent.VK_L, KeyEvent.VK_O, 
                                                KeyEvent.VK_I, KeyEvent.VK_SPACE,
                                                KeyEvent.VK_P};
    
    /** The list of integers for values for second player's alternative. */
    private static final int[] SP_ALT_VALUES = {KeyEvent.VK_X, KeyEvent.VK_Z, 
                                                KeyEvent.VK_C, KeyEvent.VK_D, 
                                                KeyEvent.VK_S, KeyEvent.VK_W,
                                                KeyEvent.VK_ALT};
    
    /** The fourth index of the setting integer arrays. */
    private static final int FOURTH_SETTING_IDX = 3;
    
    /** The measurements for a 10X20 setting. */
    private static final int[] TEN_BY_20_SETTING = {300, 600, 10, 20};
    
    /** The measurements for a 10X10 setting. */
    private static final int[] TEN_BY_TEN_SETTING = {400, 400, 10, 10};
    
    /** The measurements for a 10X30 setting. */
    private static final int[] TEN_BY_30_SETTING = {200, 600, 10, 30};
    
    /** String for property change listener to know to disable single player option. */
    private static final String SINGLE_OFF = "singleOff";
    
    /** String for property change listener to know to disable single player option. */
    private static final String MULTI_OFF = "multiOff";
    
    /** Generated serial number. */
    private static final long serialVersionUID = 7921245965838836762L;
    
    /** The panel to view the controls for this game for the first player. */
    private final ControlPanel myControlPanel;
    
    /** The panel to view the controls for this game for the second player. */
    private final ControlPanel mySControlPanel;
    
    /** The panel showing the tetris game on the GUI for the first player. */
    private final TetrisPanel myGamePanel;
    
    /** The panel showing the tetris game on the GUI for the second player. */
    private final TetrisPanel mySGamePanel;
    
    /** The JMenuBar for this JMenu. */
    private final JMenuBar myBar;
    
    /** A boolean representing if this game is in multiplayer mode. */
    private boolean myIsMultiPlayer;
    
    /** A boolean to indicate whether or not the tetris panel show a trippy view. */
    private boolean myIsTrippy;
    
    //** The names of each menu option in string form. */
    //private final String[] myMenuOptions = {"File", "Options", "Help", "More"};

    /** Constructs the FileMenu. 
     *  @param theFirstCPanel (the ControlPanel for the first player).
     *  @param theSecondCPanel (the ControlPanel for the second player).
     *  @param theFirstGPanel (The TetrisPanel for the first player).
     *  @param theSecGPanel (The TetrisPanel for the second player).
     */
    public FileMenu(final ControlPanel theFirstCPanel, final ControlPanel theSecondCPanel, 
                    final TetrisPanel theFirstGPanel, final TetrisPanel theSecGPanel) {
        super();
        myControlPanel = theFirstCPanel;
        mySControlPanel = theSecondCPanel;
        myGamePanel = theFirstGPanel;
        mySGamePanel = theSecGPanel;
        myBar = new JMenuBar();
        myIsMultiPlayer = true;
        myIsTrippy = false;
        build();
    }
    
    /** Builds the FileMenu. */
    private void build() {
        myBar.add(file());
        myBar.add(options());
        myBar.add(sizes());
        myBar.add(blocks());
        myBar.add(about());
    }
    
    
    /**
     * Makes the fileMenu.
     * @return the fileMenu.
     */
    private JMenu file() {
        final JMenu fileMenu = new JMenu("File");
        
        final Action newG = new AbstractAction("New Game") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7360347554851445303L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGamePanel.setIsDisplay(true);
                mySGamePanel.setIsDisplay(true);
                myGamePanel.stop();
                mySGamePanel.stop();
                myGamePanel.start();
                if (myIsMultiPlayer) {
                    mySGamePanel.start();
                }
            }
        };
        fileMenu.add(newG);
        
        final Action endG = new AbstractAction("End Game") {

            /** Generated serial version UID */
            private static final long serialVersionUID = -7360347554851445303L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGamePanel.setIsDisplay(false);
                mySGamePanel.setIsDisplay(false);
                myGamePanel.stop();
                mySGamePanel.stop();
                myGamePanel.repaint();
                mySGamePanel.repaint();
            }
        };
        fileMenu.add(endG);
        /*
        final Action set = new AbstractAction("End Game") {

            /** Generated serial version UID */ /*
            private static final long serialVersionUID = -7360347554851445303L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGamePanel.setIsDisplay(false);
                mySGamePanel.setIsDisplay(false);
                myGamePanel.stop();
                mySGamePanel.stop();
                myGamePanel.repaint();
                mySGamePanel.repaint();
            }
        };
        fileMenu.add(endG);
        */
        return fileMenu;
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theArg) {
        if (SINGLE_OFF.equals(theArg.getPropertyName())) {
            myBar.getMenu(1).getItem(0).setEnabled(false);
            myBar.getMenu(1).getItem(1).setEnabled(true);
        } else if (MULTI_OFF.equals(theArg.getPropertyName())) {
            myBar.getMenu(1).getItem(0).setEnabled(true);
            myBar.getMenu(1).getItem(1).setEnabled(false);
        }
        
    }
    
    /**
     * Makes the optionsMenu.
     * @return the optionsMenu.
     */
    private JMenu options() {
        final JMenu optionMenu = new JMenu("Options");
        
        final Action singleP = new AbstractAction("Single Player") {

            /** Generated serial version UID */
            private static final long serialVersionUID = -7360347554851445303L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsMultiPlayer = false;
                mySGamePanel.setIsDisplay(false);
                mySGamePanel.stop();
                mySGamePanel.clearViewNPanel();
                mySGamePanel.repaint();
                firePropertyChange(SINGLE_OFF, null, null);
            }
        };
        singleP.addPropertyChangeListener(this);
        optionMenu.add(singleP);     
        final Action multiP = new AbstractAction("MultiPlayer") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7360347554851445303L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsMultiPlayer = true;
                mySGamePanel.setIsDisplay(true);
                if (myGamePanel.getTimer().isRunning() && !mySGamePanel.isRunning()) {
                    JOptionPane.showMessageDialog(null, "Second player will be enabled"
                                    + " at the start of the next game.", "NOTICE",  
                                                  JOptionPane.PLAIN_MESSAGE);
                }
                mySGamePanel.repaint();
                firePropertyChange(MULTI_OFF, null, null);
            }
        };
        multiP.addPropertyChangeListener(this);
        multiP.setEnabled(false);
        optionMenu.add(multiP); 
        final Action swapAct = new AbstractAction("SWAP KEYS") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7938856108042816176L;
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setLists((int[]) mySControlPanel.getControls().clone(), 
                         (int[]) myControlPanel.getControls().clone());
                JOptionPane.showMessageDialog(null, "Player keys have been swapped", 
                                              "Key Changes", JOptionPane.NO_OPTION);
            }
        };
        optionMenu.add(swapAct);
        final Action altAct = new AbstractAction("ALTERNATE KEYS") { 
            /** Generated serial version UID */
            private static final long serialVersionUID = -2557446367273797772L;
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setLists((int[]) FP_ALT_VALUES.clone(), 
                         (int[]) SP_ALT_VALUES.clone());
                JOptionPane.showMessageDialog(null, "Alternate Controls made", 
                                              "Alternate", JOptionPane.NO_OPTION);
            }
        };
        optionMenu.add(altAct);
        final Action originalAct = new AbstractAction("ORIGINAL KEYS") {
            
            /** Generated serial version UID */
            private static final long serialVersionUID = -3088528055010819705L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                setLists((int[]) FP_ORIGINAL_VALUES.clone(), 
                         (int[]) SP_ORIGINAL_VALUES.clone());
                JOptionPane.showMessageDialog(null, "Original Controls made", 
                                              "Original", JOptionPane.NO_OPTION);
            }
        };
        optionMenu.add(originalAct);
        final JCheckBoxMenuItem trippy = new JCheckBoxMenuItem("Trippy Mode");
        trippy.addActionListener(new ActionListener() {
            
            public void actionPerformed(final java.awt.event.ActionEvent theEvent) {
                if (myIsTrippy) {
                    myIsTrippy = false;
                    myGamePanel.enableTrippy(myIsTrippy);
                    mySGamePanel.enableTrippy(myIsTrippy);
                } else {
                    myIsTrippy = true;
                    myGamePanel.enableTrippy(myIsTrippy);
                    mySGamePanel.enableTrippy(myIsTrippy);
                }
            }
        });
        optionMenu.add(trippy);
        return optionMenu;
    }
    
    /**
     * Changes the control keys.
     * @param theFirstKeys (the keys for the first player).
     * @param theSecKeys (the keys for the second player).
     */
    private void setLists(final int[] theFirstKeys, final int[] theSecKeys) {
       
        final boolean match = doKeysMatch((int[]) theFirstKeys.clone(),
                                          (int[]) theSecKeys.clone());
        
        if (match) {
            myControlPanel.changeControls(theFirstKeys);
            myGamePanel.setActionMap(myGamePanel.makeActionMap(theFirstKeys)); 
            firePropertyChange(myGamePanel.getPlayer(), null, new KeyReader(myGamePanel));
            
            mySControlPanel.changeControls(theSecKeys);
            mySGamePanel.setActionMap(mySGamePanel.makeActionMap(theSecKeys)); 
            firePropertyChange(mySGamePanel.getPlayer(), null, new KeyReader(mySGamePanel));
        }    
    }
    
    /**
     * Checks to see if to integer arrays have any matching elements.
     * @param theKeyL1 (The first array of keys represented as integers).
     * @param theKeyL2 (The second array of keys represented as integers).
     * @return a boolean to represent if the two sets of keys match.
     */
    private boolean doKeysMatch(final int[] theKeyL1, final int[] theKeyL2) {
        
        final int[] key1Copy = (int[]) theKeyL1.clone();
        final int[] key2Copy = (int[]) theKeyL2.clone();
        
        final Set<Integer> matches = new HashSet<Integer>();
        for (int i = 0; i < theKeyL1.length; i++) {
            matches.add(key1Copy[i]);
            matches.add(key2Copy[i]);
        }

        return matches.size() == (key1Copy.length * 2);
    }
    
    /**
     * Makes the sizesMenu.
     * @return the optionsMenu.
     */
    private JMenu sizes() {
        final JMenu sizeMenu = new JMenu("Sizes");
        
        final Action tenby20 = new AbstractAction("10 X 20") {
            
            /** Generated serial version UID */
            private static final long serialVersionUID = -3088528055010819705L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                myGamePanel.setGameSize(TEN_BY_20_SETTING[0], TEN_BY_20_SETTING[1], 
                                         TEN_BY_20_SETTING[2], 
                                         TEN_BY_20_SETTING[FOURTH_SETTING_IDX]);
                mySGamePanel.setGameSize(TEN_BY_20_SETTING[0], TEN_BY_20_SETTING[1], 
                                        TEN_BY_20_SETTING[2], 
                                        TEN_BY_20_SETTING[FOURTH_SETTING_IDX]);
                
            }
        };
        sizeMenu.add(tenby20);
        
        final Action tenbyten = new AbstractAction("10 X 10") {
            
            /** Generated serial version UID */
            private static final long serialVersionUID = -3088528055010819705L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                myGamePanel.setGameSize(TEN_BY_TEN_SETTING[0], TEN_BY_TEN_SETTING[1], 
                                        TEN_BY_TEN_SETTING[2], 
                                        TEN_BY_TEN_SETTING[FOURTH_SETTING_IDX]);
                mySGamePanel.setGameSize(TEN_BY_TEN_SETTING[0], TEN_BY_TEN_SETTING[1], 
                                         TEN_BY_TEN_SETTING[2], 
                                         TEN_BY_TEN_SETTING[FOURTH_SETTING_IDX]);
                
            }
        };
        sizeMenu.add(tenbyten);
        
        final Action fifteenby25 = new AbstractAction("10 X 30") {
            
            /** Generated serial version UID */
            private static final long serialVersionUID = -3088528055010819705L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                myGamePanel.setGameSize(TEN_BY_30_SETTING[0], TEN_BY_30_SETTING[1], 
                                        TEN_BY_30_SETTING[2], 
                                        TEN_BY_30_SETTING[FOURTH_SETTING_IDX]);
                mySGamePanel.setGameSize(TEN_BY_30_SETTING[0], TEN_BY_30_SETTING[1], 
                                         TEN_BY_30_SETTING[2], 
                                         TEN_BY_30_SETTING[FOURTH_SETTING_IDX]);
                
            }
        };
        sizeMenu.add(fifteenby25);
        
        return sizeMenu;
    }
    
    /**
     * Makes the Block Types menu.
     * @return a menu that lets you change the tetris blocks.
     */
    private JMenu blocks() {
        final JMenu blocks = new JMenu("Block Types");
        addBlocksPlayer("First Player to ", blocks, myGamePanel, null);
        addBlocksPlayer("Second Player to ", blocks, mySGamePanel, null);
        addBlocksPlayer("Both Player to ", blocks, myGamePanel, mySGamePanel);
        return blocks;
    }
    
    /**
     * Adds the block options for a single player.
     * @param thePlayer (The name of the player).
     * @param theMenu (the JMenu I want to add these actions to).
     * @param thePanel (the TetrisPanel I want to modify).
     * @param theOtherPanel (another TetrisPanel one may want to modify).
     */
    private void addBlocksPlayer(final String thePlayer, final JMenu theMenu, 
                                 final TetrisPanel thePanel, final TetrisPanel theOtherPanel) {
        final Action square = new AbstractAction(thePlayer + " Square Blocks") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7954114178707277022L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final String rec = "rectangle";
                thePanel.setShape(rec);
                if (theOtherPanel != null) {
                    theOtherPanel.setShape(rec);
                }
            }
        };
        theMenu.add(square);
        
        final Action roundedRec = new AbstractAction(thePlayer + "Rounded Blocks") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7954114178707277022L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final String roundRec = "rounded rec";
                thePanel.setShape(roundRec);
                if (theOtherPanel != null) {
                    theOtherPanel.setShape(roundRec);
                }
            }
        };
        theMenu.add(roundedRec);
        
        final Action circles = new AbstractAction(thePlayer + "Circle Blocks") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7954114178707277022L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final String ellipse = "ellipse";
                thePanel.setShape(ellipse);
                if (theOtherPanel != null) {
                    theOtherPanel.setShape(ellipse);
                }
            }
        };
        theMenu.add(circles);
    }
    
    /**
     * Makes the aboutMenu.
     * @return the aboutMenu.
     */
    private JMenu about() {
        final JMenu aboutMenu = new JMenu("About");
        final Action scoring = new AbstractAction("Scoring Algorithm...") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7954114178707277022L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, "4 point for every piece"
                                + "\n10 points for every line cleared"
                                + "\nLevel goes up every time 5 lines are cleared"
                                + "\nEvery level, 100 miliseconds are cut per step"
                                , "Algorithm",  
                                JOptionPane.PLAIN_MESSAGE);
            }
        };
        aboutMenu.add(scoring);
        
        final Action credits = new AbstractAction("Credits...") {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7954114178707277022L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, "Icon: http://icons.iconarchive.com/icons"
                                + "/chrisbanks2/cold-"
                                + "fusion-hd/128/tetris-icon.png\n", "Credits",  
                                JOptionPane.PLAIN_MESSAGE);
            }
        };
        aboutMenu.add(credits);
        
        return aboutMenu;    
    }
    
    
    /**
     * Gives the myBar access.
     * @return myBar (The JMenuBar I make).
     */
    public JMenuBar getMenuBar() {
        return myBar;
    }

    

    
}
