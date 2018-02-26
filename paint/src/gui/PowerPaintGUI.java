/*
 * TCSS 305 - Assignment 4: PowerPaint
 * 
 * A PowerPaint GUI that allows the user to make a 2D image while having certain tools
 * available to them.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;




/**
 * PowerPaint GUI to let you make a simple 2D graphic.
 * 
 * @author Peter M Chu
 * @version 05/03/2016
 */
public class PowerPaintGUI {
    
    /** A list of the names of myDrawACtions actions. */
    private final String[] myActionStringArray = {"Pencil", "Line", "Rectangle", "Ellipse"};
    
    /** The frame for this application's GUI. */
    private final JFrame myFrame;
    
    /** The panel for this application's GUI. */
    private final DrawingPanel myPanel;
    
    /** A list of drawing actions. */
    private final List<DrawAction> myDrawActions;
    
    /** A list of the toggle methods. */
    private final List<JToggleButton> myToggleBtns;
    
    /** The FileMenu is the menu I want for my GUI. */
    private FileMenu myMenu;
    
    /** A the mouse listener for this panel serialization ID. */
    //private final MouseListener myListener;
    
    
    
    /**
     * Constructor to initialize fields.
     */
    public PowerPaintGUI() {
        myFrame = new JFrame();
        myToggleBtns = new ArrayList<JToggleButton>();
        myDrawActions = new ArrayList<DrawAction>();
        setupActions();
        myPanel = new DrawingPanel();
        myMenu = new FileMenu(myDrawActions, myFrame, myPanel);   
        //myListener = new MouseListener(myDrawActions);
        
    }
    
    /** Adds the listeners to myPanel.
    private void addListeners() {
        myPanel.addMouseListener(myListener);
        myPanel.addMouseMotionListener(myListener);
        myPanel.addMouseWheelListener(myListener);
        myFrame.add(myPanel);
        
    }*/
    
    /**
     * A search method for myActionStringArray.
     * @param theStr is the String I am searching for.
     * @return a integer indicating the index of theStr but
     * returns -1 if none is found.
     */
    public int arrayContains(final String theStr) {
        int idx = -1;
        for (int i = 0; i < myActionStringArray.length; i++) {
            if (theStr.equals(myActionStringArray[i])) {
                idx = i;
            }
        }
        return idx;
    }
    
    /**
     * This is the start method that will be the first method runs (Execute GUI).
     */
    public void start() {
        //addListeners();
        
        myFrame.setTitle("PowerPaint");
        try {
            final BufferedImage uWIcon = ImageIO.read(new File("images\\uw_icon.gif"));
            myFrame.setIconImage(uWIcon);
        } catch (final IOException e) { 
            JOptionPane.showMessageDialog(null, "UW Icon was not found!");
        }
        //DO NOT RE ORDER THIS
        
        //DO NOT RE ORDER THIS
        myMenu = new FileMenu(myDrawActions, myFrame, myPanel);
    
        //System.out.println(myMenu.getMenuBar());
        myFrame.setJMenuBar(myMenu.getMenuBar());
        myPanel.setMenu(myMenu); //, myListener);
        myFrame.add(myPanel);
        /*
        //Is the fill box checked
        final JCheckBox cB = (JCheckBox) myFrame.getJMenuBar().getMenu(1).getMenuComponent(5);
        myIsFill = cB.isSelected();
        */
        
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Sets up all the Actions and makes a fully-stocked tool bar.
     */
    private void setupActions() {

        final JToolBar bar = new JToolBar();
        final ButtonGroup buttons = new ButtonGroup();
        for (int i = 0; i < myActionStringArray.length; i++) {
            
            //The DrawAction class will add the actionPerformed
            final DrawAction drawA = new DrawAction(myActionStringArray[i]); 
            myDrawActions.add(drawA);
            
            final JToggleButton button = new JToggleButton(drawA);
            button.setName(myActionStringArray[i]);
            button.setSelected(false);
            buttons.add(button);
            myToggleBtns.add(button);
            
            bar.add(button);
        }
        
        myFrame.add(bar, BorderLayout.SOUTH);
    }
    
    
    
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Allows me to make a make a certain type of drawing action.
     * 
     * @author Peter M Chu
     * @version 05/03/2016
     */
    public class DrawAction extends AbstractAction { 
        
        /** A generated serialization ID. */
        private static final long serialVersionUID = -7087139868367787254L;
        
        /** The name of the action. */
        private final String myAction;
        
        /** The path being created. */
        //private Path2D myPath;
        
        /** The DrawingPanel I'm drawing on. */
        //private DrawingPanel myCopyPanel;
      //myCopyPanel = thePanel.copy();
        
        /** A boolean to represent if this action is made active. */
        //private boolean myIsActive;
        
        
        /**
         * Constructs an action with the specified name to set the
         * panel to the specified drawing action.
         * 
         * @param theName The name.
         */
        DrawAction(final String theName) {
            super(theName);
            myAction = theName;
        } 
        
        /**
         * Sets the panel to the specified drawing tool.
         * 
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (arrayContains(myAction) > -1) {
                final int index = arrayContains(myAction);
                myToggleBtns.get(index).setSelected(true);
                final JRadioButton btn = (JRadioButton) 
                                myFrame.getJMenuBar().getMenu(2).getMenuComponent(index);
                btn.setSelected(true);
                //toolAction(theEvent, arrayContains(myAction));
                
            }
        }
        
        /**
         * The actionPerformed for anything in the tools menu.
         * @param theEvent the Action that was executed.
         * @param theIndex the integer to let me know which on of the tools was used.
         
        public void toolAction(final ActionEvent theEvent, final int theIndex) {
            
        }*/

        /**
         * Returns myAction string.
         * @return myAction (The string form of the type of action this is).
         */
        public String getMyAction() {
            return myAction;
        }
        
        
        
    }  
    
}



