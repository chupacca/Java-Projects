/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * Allows the user to play a tetris game.
 */

package view;

import backend.FileMenu;
import backend.TetrisFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import panels.ControlPanel;
import panels.ScorePanel;
import panels.TetrisPanel;
import panels.ViewNextPanel;

/**
 * Tetris GUI to let you play tetris.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class TetrisGUI implements PropertyChangeListener {
    
    /** The minimum size I want my JFrame. */
    private static final Dimension MINIMUM_SIZE = new Dimension(600, 300);
    
    /** A width adjuster for resizing. */
    private static final int MARGIN_ERROR = 15;
    
    /** The width of the tetris panel. */
    private static final int TETRIS_PANEL_WIDTH = 300;
    
    /** The height of the tetris panel. */
    private static final int TETRIS_PANEL_HEIGHT = 600;
    
    /** The string to show in the games that it is the first player. */
    private static final String FIRST = "First Player";
    
    /** The string to show in the games that it is the second player. */
    private static final String SECOND = "Second Player";
    
    /** Property name for a change of keys. */
    private static final String KEY_PROP_NAME = "keyChange";
    
    /** The list of integers for values for myControls. */
    private static final int[] FP_VALUES = {KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, 
                                            KeyEvent.VK_RIGHT, KeyEvent.VK_M, 
                                            KeyEvent.VK_UP, KeyEvent.VK_SPACE,
                                            KeyEvent.VK_P};
    
    /** The list of integers for values for myControls. */
    private static final int[] SP_VALUES = {KeyEvent.VK_S, KeyEvent.VK_A, 
                                            KeyEvent.VK_D, KeyEvent.VK_Q, 
                                            KeyEvent.VK_W, KeyEvent.VK_E, 
                                            KeyEvent.VK_G};
      
    //** The frame for this application's GUI. */
    //private final TetrisFrame frame;
    
    /** The timer that controls the progression of the game. */
    //private final Timer myGameTimer;
    
    //First Player
    
    /** The panel showing the tetris game on the GUI for the first player. */
    private final TetrisPanel myGamePanel;
    
    /** The panel to view the controls for this game for the first player. */
    private final ControlPanel myControlPanel;
    
    /** The panel that will show the next piece for the first player.*/
    private final ViewNextPanel myNextPanel;
    
    //Second Player
    
    /** The panel showing the tetris game on the GUI for the second player. */
    private final TetrisPanel mySGamePanel;
    
    /** The panel to view the controls for this game for the second player. */
    private final ControlPanel mySControlPanel;
    
    /** The panel that will show the next piece for the second player. */
    private final ViewNextPanel mySNextPanel;
    
    /** The menu bar for the GUI. */
    private final FileMenu myMenu;
    
    /** The score panel for the first player. */
    private final ScorePanel myScoreP;
    
    /** The score panel for the second player. */
    private final ScorePanel mySScoreP;
    
    /** The JPanel that has two TetrisPanels attached. */
    private final JPanel myTwoPanel;
    
    /** The frame for game. */
    private TetrisFrame myFrame;
    
    /** Constructs and instance to TetrisGUI and initializes necessary fields. */
    public TetrisGUI() {
        //myGameTimer = d;
        
        //FIRST PLAYER
        myControlPanel = new ControlPanel((int[]) FP_VALUES.clone(), FIRST);
        myGamePanel = new TetrisPanel(myControlPanel.getControls(),
                                      TETRIS_PANEL_WIDTH, TETRIS_PANEL_HEIGHT, FIRST);
        myNextPanel = new ViewNextPanel(FIRST);
        
        //SECOND PLAYER
        mySControlPanel = new ControlPanel((int[]) SP_VALUES.clone(), SECOND);
        mySGamePanel = new TetrisPanel(mySControlPanel.getControls(),
                                      TETRIS_PANEL_WIDTH, TETRIS_PANEL_HEIGHT, SECOND);
        mySNextPanel = new ViewNextPanel(SECOND);
        
        //NEITHER FIRST OR SECOND
        myMenu = new FileMenu(myControlPanel, mySControlPanel, myGamePanel, mySGamePanel);
        
        myTwoPanel = new JPanel(new GridLayout(1, 2));
        
        myScoreP = new ScorePanel(FIRST);
        
        mySScoreP = new ScorePanel(SECOND);
    }
    
    /** This is the start method that will be the first method run (Executes GUI). 
     * @throws IOException */
    public void start() {
        
        myTwoPanel.add(myGamePanel);
        myTwoPanel.add(mySGamePanel);
        
        
        final JPanel panel = new JPanel(new GridLayout(4, 1));
        addToPanel(panel, myNextPanel, myControlPanel, myScoreP);
        addToPanel(panel, mySNextPanel, mySControlPanel, mySScoreP);
        
        myFrame  = new TetrisFrame(panel, myTwoPanel, myGamePanel);
        
        
        myFrame.setTitle("Tetris");
        try {
            final BufferedImage icon = ImageIO.read(new File("images\\tetris-icon.png"));
            myFrame.setIconImage(icon);
        } catch (final IOException e) { 
            JOptionPane.showMessageDialog(null, "UW Icon was not found!");
        }
        
        
        
        myFrame.setJMenuBar(myMenu.getMenuBar());
        myMenu.addPropertyChangeListener(myFrame);
        setListeners(myFrame, myGamePanel, myNextPanel, myScoreP);
        
        myGamePanel.addPropertyChangeListener(mySGamePanel);
        mySGamePanel.addPropertyChangeListener(myNextPanel);
        setListeners(myFrame, mySGamePanel, mySNextPanel, mySScoreP);

        myFrame.add(myTwoPanel, BorderLayout.CENTER); 
        
        myFrame.add(panel, BorderLayout.EAST); 
        
        myGamePanel.addPropertyChangeListener(this);
        mySGamePanel.addPropertyChangeListener(this);
        
        myFrame.getContentPane().setBackground(Color.RED);
        
        myFrame.setMinimumSize(MINIMUM_SIZE);
        myFrame.pack();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        
    }
    
    /**
     * Adds two panels to one other panel.
     * @param theMainPanel (the main panel I wan to add panels to).
     * @param theAddedPanel1 (the first panel I am adding).
     * @param theAddedPanel2 (the second panel I am adding).
     * @param theAddedPanel3 (the third panel I am adding).
     */
    private void addToPanel(final JPanel theMainPanel, final JPanel theAddedPanel1, 
                            final JPanel theAddedPanel2, final JPanel theAddedPanel3) {
        theMainPanel.add(theAddedPanel1);
        theMainPanel.add(theAddedPanel2);
        theMainPanel.add(theAddedPanel3);
    }
    
    /**
     * Attaches necessary listeners.
     * @param theFrame (The TetrisFrame of my GUI).
     * @param theGPanel (The TetrisPanel being played on).
     * @param theNPanel (The ViewNextPanel for the TetrisPanel).
     * @param theScoreP (The score panel).
     */
    private void setListeners(final TetrisFrame theFrame, final TetrisPanel theGPanel, 
                              final ViewNextPanel theNPanel, final ScorePanel theScoreP) {
        theFrame.addKeyListener(theGPanel.getKeyReader());
        theGPanel.addPropertyChangeListener(theNPanel);
        theFrame.addPropertyChangeListener(theGPanel);
        theGPanel.addPropertyChangeListener(theScoreP);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theArg) {
        if ((KEY_PROP_NAME + FIRST).equals(theArg.getPropertyName())) {
            myFrame.removeKeyListener(myFrame.getKeyListeners()[0]);
            myFrame.addKeyListener(myGamePanel.getKeyReader());
            
            myFrame.setRatio(myGamePanel.getBoardWidth(), myGamePanel.getBoardHeight());
            myFrame.pack();
        } else if ((KEY_PROP_NAME + SECOND).equals(theArg.getPropertyName())) {
            myFrame.removeKeyListener(myFrame.getKeyListeners()[0]);
            myFrame.addKeyListener(mySGamePanel.getKeyReader());

            myFrame.setRatio(mySGamePanel.getBoardWidth(), mySGamePanel.getBoardHeight());
            myFrame.pack();
        } else if (("changeTwoPanel" + SECOND).equals(theArg.getPropertyName())) {
            final Dimension dim = (Dimension) theArg.getNewValue();
            myTwoPanel.setSize(new Dimension(((int) myFrame.getWidth()) 
                                             - myControlPanel.getWidth() * 2 - MARGIN_ERROR, 
                                             (int) dim.getHeight()));
            myFrame.pack();
        }
        
        
    }
    
}
