/*
 * TCSS 305 - Assignment 4: PowerPaint
 * 
 * Makes the JMenuBar for the PowerPaint GUI.
 */

package gui;

import gui.PowerPaintGUI.DrawAction;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * Constructs the Top menu for the PowerPaint GUI.
 * 
 * @author Peter M Chu
 * @version 05/03/2016
 */
public class FileMenu {
    
    /** The minor tick spacing for the Thickness slider. */
    private static final int MINOR_TICK_SPACING = 1;

    /** The major tick spacing for the Thickness slider. */
    private static final int MAJOR_TICK_SPACING = 5;
    
    /** The initial thickness. */
    private static final int INITIAL_FRAMES_PER_SECOND = 1;

    /** The maximum thickness. */
    private static final int MAX_FRAMES_PER_SECOND = 20;
    
    /** Number three. */
    private static final int THREE = 3;
    
    /** The slider for thickness. */
    private JSlider mySlider;
    
    /** The menu bar. */
    private final JMenuBar myMenuBar;
    
    /** The names of each menu option in string form. */
    private final String[] myMenuOptions = {"File", "Options", "Tools", "Help"};
    
    /** Names of actions. */
    private final String[] myActionNames = 
    {"Draw Color..." , "Fill Color...", "Quit", "Clear"};

    /** A list of drawing actions. */
    private final List<gui.PowerPaintGUI.DrawAction> myDrawActions;
    
    /** The frame for this application's GUI. */
    private final JFrame myFrame;
    
    /** The panel for this application's GUI. */
    private DrawingPanel myPanel;
    
    /** The draw Color from ColorChooser (initially made black). */
    //private Color myDrawColor = Color.BLACK;
    
    /** The fill Color from ColorChooser (initially made black). */
    //private Color myFillColor = Color.BLACK;
    
    /** The draw Color from ColorChooser (initially made black). */
    private final ColorChooser myChooseDrawColor;
    
    /** The fill Color from ColorChooser (initially made black). */
    private final ColorChooser myChooseFillColor;
    
    /** The line width. */
    private int myLineWidth;
    

    /**
     * Makes the File Menu I hard coded. I had this as a separate class to make the code
     * more readable for me.
     * @param theDrawActions (the draw actions, in ArrayList from, that I'm taking in
     * because I may have to reuse some).
     * @param theFrame the JFrame from the GUI I want to access.
     * @param thePanel the JPanel from the GUI I want to access. 
     */
    public FileMenu(final List<DrawAction> theDrawActions, final JFrame theFrame, 
                    final DrawingPanel thePanel) {
        myChooseDrawColor = new ColorChooser();
        myChooseFillColor = new ColorChooser();
        myDrawActions = theDrawActions;  //don't want to make a copy
        myFrame = theFrame;
        myPanel = thePanel;
        myLineWidth = THREE;
        myMenuBar = makeMenuBar();  
    }
    
    /** 
     * Make the menu bar I want for my GUI.
     * @return menuBar the menu bar after I add the options for each fileMenu.
     */
    private JMenuBar makeMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        for (int i = 0; i < myMenuOptions.length; i++) {
            JMenu fileMenu = new JMenu(myMenuOptions[i]); //MISSING THE UNDERLINE 
            fileMenu.setMnemonic(myMenuOptions[i].charAt(0));
            if (i == 0) {
                fileMenu = file(fileMenu);
            } else if (i == 1) {
                fileMenu = options(fileMenu);
            } else if (i == 2) {
                fileMenu = tools(fileMenu);
            } else {
                fileMenu = help(fileMenu);
            }
            
            menuBar.add(fileMenu);
        }
        return menuBar;
    }
    
    /**
     * Makes the options for the file menu option.
     * @param theFileMenu (the menu option that I want to add drop down options for).
     * @return theFileMenu (a JMenu with the relevant options drop down options attached).
     */
    private JMenu file(final JMenu theFileMenu) {
        
        final Action clearAct = 
                        makeAction(myActionNames[THREE], null);
        clearAct.putValue(Action.SHORT_DESCRIPTION, myActionNames[THREE]);
        theFileMenu.add(clearAct);
        
        theFileMenu.addSeparator();

        final Action exitAct = 
                        makeAction(myActionNames[2], new ImageIcon("images\\x-red.gif"));
        exitAct.putValue(Action.SHORT_DESCRIPTION, myActionNames[2]);
        //exitAct.putValue(Action.ACCELERATOR_KEY,
                                //KeyStroke.getKeyStroke('Q', 
                                //java.awt.event.InputEvent.CTRL_MASK));
        theFileMenu.add(exitAct);
        
        return theFileMenu;
    }
    
    /**
     * Makes the options for the options menu option.
     * @param theFileMenu (the menu option that I want to add drop down options for).
     * @return theFileMenu (a JMenu with the relevant options drop down options attached).
     */
    private JMenu options(final JMenu theFileMenu) {
        
        theFileMenu.add(makeSlider());
        theFileMenu.addSeparator();
        
        final Action chooseDrawColor = 
                        makeAction(myActionNames[0], myChooseDrawColor.getSmallDisplayIcon()); 
                        //new ImageIcon("images\\pencil.gif"));
        theFileMenu.add(chooseDrawColor);
        
        final Action chooseFillColor = 
                        makeAction(myActionNames[1], myChooseFillColor.getSmallDisplayIcon()); 
                        //new ImageIcon("images\\ellipse.gif"));
        theFileMenu.add(chooseFillColor);
        
        theFileMenu.addSeparator();
        
        final JCheckBoxMenuItem cB = new JCheckBoxMenuItem("Fill", false);

        cB.addActionListener(new ActionListener() {
            
            public void actionPerformed(final java.awt.event.ActionEvent theEvent) {
                if (cB.isSelected()) {
                    cB.setSelected(true);
                } else {
                    cB.setSelected(false);
                } 
            }
        });
        cB.setAccelerator(KeyStroke.getKeyStroke('F', 
                                java.awt.event.InputEvent.CTRL_MASK));
        theFileMenu.add(cB);

        return theFileMenu;
    }
    
    /**
     * The actions made for certain options.
     * @param theName (the name of the action).
     * @param theIcon (the icon you want for the action).
     * @return a Color object.
     */
    private Action makeAction(final String theName, final ImageIcon theIcon) {

        final Action action = new AbstractAction(theName, theIcon) {
            /** Generated serial version UID */
            private static final long serialVersionUID = -7938856108042816176L;
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myActionNames[0].equals(theName) || myActionNames[1].equals(theName)) {
                    colorAction(theName);  
                } else if (myActionNames[2].equals(theName)) {
                    myFrame.dispose(); //exit
                } else if (myActionNames[THREE].equals(theName)) {
                    myPanel.removeAll();
                    //myPanel = new DrawingPanel(); //myPanel.repaint(); //clear
                }
            }
        };
        return action;
    }
    
    /**
     * The actions made for the color chooser options.
     * @param theName (the name of the action).
     */
    private void colorAction(final String theName) {
        
        if (myActionNames[0].equals(theName)) {
            myChooseDrawColor.chooseColor();
            final JMenuItem item = 
                            (JMenuItem) this.getMenuBar().getMenu(1).getMenuComponent(2);
            item.setIcon(myChooseDrawColor.getSmallDisplayIcon());
        } else { 
            myChooseFillColor.chooseColor();
            final JMenuItem item = 
                            (JMenuItem) this.getMenuBar().getMenu(1).getMenuComponent(3);
            item.setIcon(myChooseFillColor.getSmallDisplayIcon());
        }
    }
    
    /**
     * Makes the JSlider option for the options menu.
     * @return a JMenu with a JSlider within.
     */
    private JMenu makeSlider() {
        mySlider = new JSlider(SwingConstants.HORIZONTAL, 0, MAX_FRAMES_PER_SECOND,
                               INITIAL_FRAMES_PER_SECOND);
        mySlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        mySlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        mySlider.setPaintLabels(true);
        mySlider.setPaintTicks(true);
        mySlider.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                //final int value = mySlider.getValue();
                myLineWidth = mySlider.getValue();
            }
        });
        
        final JMenu item = new JMenu("Thickness");
        item.add(mySlider);
        
        return item;
    }
    
    /**
     * Makes the options for the tools menu option.
     * @param theFileMenu (the menu option that I want to add drop down options for).
     * @return theFileMenu (a JMenu with the relevant options drop down options attached).
     */
    private JMenu tools(final JMenu theFileMenu) {
        final ButtonGroup buttonGroup = new ButtonGroup();
        for (final DrawAction drawAction: myDrawActions) {
            
            final JRadioButton button = new JRadioButton(drawAction.getMyAction());
            
            button.addActionListener(new ActionListener() {
                
                public void actionPerformed(final java.awt.event.ActionEvent theEvent) {
                    //System.out.print("FileMenu  |");
                    drawAction.actionPerformed(theEvent);
                }
            });
            buttonGroup.add(button);
            theFileMenu.add(button);
        }
        return theFileMenu;
    }
    
    
    
    /**
     * Makes the options for the help menu option.
     * @param theFileMenu (the menu option that I want to add drop down options for).
     * @return theFileMenu (a JMenu with the relevant options drop down options attached).
     */
    private JMenu help(final JMenu theFileMenu) {
        
        final String message = "TCSS 305 PowerPaint\nSpring 2016\nPeter M Chu";
        final String about = "About";
        /**
         * A UWT icon from an image file.
         */
        final Icon uWT = new ImageIcon("images\\uw_icon.gif");
        final Action action = new AbstractAction(about + "...") {
            
            /** Generated serial number */
            private static final long serialVersionUID = -6888394426198069197L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, message,
                                              about, JOptionPane.PLAIN_MESSAGE, uWT);
            }
        };
        
        theFileMenu.add(action);
        
        return theFileMenu;
    }
    
    /**
     * Returns the menu bar I made.
     * @return myMenuBar (The menu bar that I want my PowerPaint GUI to have).
     */
    public JMenuBar getMenuBar() {
        return myMenuBar;
    }
    
    /**
     * Returns the chosen draw color (If none chosen, default is black).
     * @return myColor (A color object for line).
     */
    public Color getDrawColor() {
        return myChooseDrawColor.getColor();
    }
    
    /**
     * Returns the chosen fill color (If none chosen, default is black).
     * @return myColor (A color object for fill).
     */
    public Color getFillColor() {
        return myChooseFillColor.getColor();
    }
    
    /**
     * Returns the chosen thickness bar number (line width).
     * @return myLineWidth (an integer representing the line width).
     */
    public int getLineWidth() {
        return myLineWidth;
    }
}
