
/*
 * TCSS 305 - Assignment 4: PowerPaint
 * 
 * The Panel I am going to be drawing on.
 */

package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.MouseInputListener;



/**
 * The panel that I am drawing on.
 * 
 * @author Peter M Chu
 * @version 05/03/2016
 */
public class DrawingPanel extends JPanel {
    
    /*
     * final JCheckBox cB = (JCheckBox) myFrame.getJMenuBar().getMenu(1).
                            getMenuComponent(5);
     */
    
    /** A generated serialization ID. */
    private static final long serialVersionUID = 2302812257149047261L;
    
    /** A list of the names of myDrawACtions actions. */
    //private final String[] myActionStringArray = {"Pencil", "Line", "Rectangle", "Ellipse"};
    
    /** The FileMenu is the menu I set up for my GUI. */
    private FileMenu myMenu;
    
    /** The btn. */
    private JRadioButton myPenBtn;
    
    /** The btn. */
    private JRadioButton myLineBtn;
    /** . */
    private JRadioButton myRecBtn;
    /** . */
    private JRadioButton myEllBtn;
    
    /** The path being created. */
    private final Path2D myPath;
    
    /** The my two points in array form point. */
    private final Point2D.Double[] myPts = {new Point2D.Double(-1, -1), 
                    new Point2D.Double(-1, -1)};
    
    /** The line that I use when drawing. */
    private final Line2D.Double myLine;
    
    /** The line that I use when drawing. */
    private final Rectangle2D.Double myRectangle;
    
    /** The line that I use when drawing. */
    private final Ellipse2D.Double myEllipse;
    
    /** A the mouse listener for the GUI's panel. */
    private final MouseListener myListener;
    
    /** The graphic I will use. */
    private Graphics2D myG;

    /** 
     * Constructor to make an instance of DrawingPanel. 
     */
    public DrawingPanel() {
        super();
        myPath = new GeneralPath();
        myLine = new Line2D.Double();
        myListener = new MouseListener();
        myRectangle = new Rectangle2D.Double();
        myEllipse = new Ellipse2D.Double();        
        setUpVar();
    }
    
    /**
     * Sets up some of the variables.
     */
    private void setUpVar() {
        addListeners();
        myPath.setWindingRule(GeneralPath.WIND_EVEN_ODD);
        setBackground(Color.WHITE);
        final int threeHundred = 300;
        setPreferredSize(new Dimension(threeHundred * 2, threeHundred));
    }
    
    /** Adds the listeners to myPanel. */
    private void addListeners() {
        addMouseListener(myListener);
        addMouseMotionListener(myListener);
        addMouseWheelListener(myListener);
        //myFrame.add(myPanel);
        
    }
    
    /**
     * @param theMenu (The FileMenu in the GUI that I can use from here);
     */
    public void setMenu(final FileMenu theMenu) {
        //myMenu = theMenu;        myMenuBar = theMenuBar;
        myMenu = theMenu;
        //myListener = theListener;
        addMouseListener(myListener);
        myPenBtn = (JRadioButton) 
                        myMenu.getMenuBar().getMenu(2).getMenuComponent(0);
        myLineBtn = (JRadioButton) 
                        myMenu.getMenuBar().getMenu(2).getMenuComponent(1);
        myRecBtn = (JRadioButton) 
                        myMenu.getMenuBar().getMenu(2).getMenuComponent(2);
        myEllBtn = (JRadioButton) 
                        myMenu.getMenuBar().getMenu(2).getMenuComponent(2 + 1);
    }
    
    /**
     * Gives the Path2D.
     * @return the Path2D.
     */
    public Path2D getPath() {
        return myPath;
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        myG = (Graphics2D) theGraphics;
        myG.setPaint(Color.BLACK);
        myG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        //DO SOMETHING HERE SO I CAN USE DIFFERENT COLORS FOR DIFFERENT THINGS
        myG.setPaint(myMenu.getDrawColor());

        myG.setStroke(new BasicStroke(myMenu.getLineWidth()));

        
        myG.draw(myPath);
        if (myLineBtn.isSelected()) {
            myG.draw(myLine);
        } else if (myRecBtn.isSelected()) {
            myG.draw(myRectangle);
        } else if (myEllBtn.isSelected()) {
            myG.draw(myEllipse);
        }
    }
    
    
    
    /**
     * An event listener that listens for all possible mouse events. All events are
     * simply printed to standard error.
     * 
     * @author Daniel M. Zimmerman
     * @author Alan Fowler - minor changes, some new comments
     * @author Peter M Chu - adjusted this class to serve my PowerPaint project.
     * @version 1.1
     */
    public class MouseListener implements MouseInputListener, MouseWheelListener {
       
        @Override
        public void mousePressed(final MouseEvent theEvent) {

            final double x = theEvent.getX();
            final double y = theEvent.getY();
            
            if (myPenBtn.isSelected()) { //tool.equals(myActionStringArray[0])) {
                myPath.moveTo(x, y);
            } else if (myLineBtn.isSelected() || myRecBtn.isSelected() 
                            || myEllBtn.isSelected()) {
                myPts[0].setLocation(x, y);
                myPath.moveTo(x, y);
            }
        }

        @Override
        public void mouseDragged(final MouseEvent theEvent) {

            final double x = theEvent.getX();
            final double y = theEvent.getY();
            myPts[1].setLocation(x, y);
            
            final Point2D firstP = (Point2D) myPts[0].clone();
            final Point2D secP = (Point2D) myPts[1].clone();
            
            if (myPenBtn.isSelected()) {
                myPath.lineTo(x, y);   
            } else if (myLineBtn.isSelected()) { //Line Tool
                myLine.setLine( firstP, secP);
                myG.draw(myLine);       
                
            } else if (myRecBtn.isSelected()) {  //Rectangle Tool
                myRectangle.setFrameFromDiagonal(firstP, secP);
                myG.draw(myRectangle);
     
            } else if (myEllBtn.isSelected()) { //Ellipse Tool
                myEllipse.setFrameFromDiagonal(firstP, secP);
                myG.draw(myEllipse);
            }
            repaint();
        }

        @Override //myGraphicsList
        public void mouseReleased(final MouseEvent theEvent) {

            final double x = theEvent.getX();
            final double y = theEvent.getY();
 
            if (myPenBtn.isSelected()) {
                myG.draw(myPath);
            } else if (myLineBtn.isSelected()) {  //Line Tool
                myPath.lineTo(x, y);
                repaint(); //myPanel.repaint();

            } else if (myRecBtn.isSelected()) {  //Rectangle Tool
                myG.draw(myRectangle);
            } else if (myEllBtn.isSelected()) {  //Ellipse Tool
                myG.draw(myEllipse);
            }
        }
        
        @Override
        public void mouseClicked(final MouseEvent theEvent) {
            //final String tool = whichAction();
        }
        
        @Override
        public void mouseEntered(final MouseEvent theEvent) {

        }

        @Override
        public void mouseExited(final MouseEvent theEvent) {

        }

        @Override
        public void mouseWheelMoved(final MouseWheelEvent theEvent) {

        }

        @Override
        public void mouseMoved(final MouseEvent theEvent) {
            //System.out.println("move");
        }

       
    }

}
