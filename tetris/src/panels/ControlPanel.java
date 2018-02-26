
/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * Creates the panel with the controls showing.
 */

package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The controls panel to view the controls for tetris.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class ControlPanel extends JPanel {
    
    /** The preferred dimensions. */
    private static final Dimension PREFERRED_SIZE = new Dimension(100, 50);
    
    /** The y-coordinate of the text. */
    private static final int Y_COR = 25;
    
    /** Default Font Size. */
    private static final int FONT_SIZE = 10;
    
    /** The increment space between my control text representations. */
    private static final int TEXT_INCREMENT = 10;
    
    /** Generated serial number. */
    private static final long serialVersionUID = -6918826741958679016L;
    
    
    //** The collection of keystroke actions associated with some string. */
    //private final Map<Integer, String> myControlMap;
    
    /** The list of Strings for keys for myControls. */
    private final String[] myKeys = {"down", "left", "right", "ccw", 
                                     "cw", "drop", "pause"};

    /** The collection of keystroke actions associated with some string. */
    private final Map<Integer, String> myControls;
    
    /** The list of integers for values for myControls. */
    private int[] myValues;
    
    //** The string to know which player this is. */
    //private final String myPlayer;
    
    /** Creates an instance of Controls and fills in the values. 
     * @param theValues (The list of KeyEvents as integers).
     * @param thePlayer (String that tells me which player this is).
     */
    public ControlPanel(final int[] theValues, final String thePlayer) {
        super();
        myValues = (int[]) theValues.clone();
        myControls = new HashMap<Integer, String>();
        changeControls((int[]) myValues.clone());
        setBackground(Color.WHITE);
        setPreferredSize(PREFERRED_SIZE);
        this.setBorder(BorderFactory.createTitledBorder(thePlayer));
    }
    
    
    
    /**
     * Allows to change the action for a respective control
     * (down, left, right, ccw, cw, drop).
     * @param theValues (the that you want in the order of down, left, right, ccw, cw, drop).
     */
    public final void changeControls(final int[] theValues) {
        
        final int[] values = (int[]) theValues.clone();
        
        myValues = (int[]) values.clone();
        
        myControls.clear();
        
        for (int i = 0; i < values.length; i++) {
            myControls.put(theValues[i], myKeys[i]);
        }
        
        repaint();
    }
    
    /**
     * The integer array with the controls.
     * @return The keystorke integers.
     */
    public int[] getControls() {
        return (int[]) myValues.clone();
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        final Font font = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, FONT_SIZE);
        g2d.setFont(font);
     
        final FontRenderContext renderContext = g2d.getFontRenderContext();
        
        for (int i = 0; i < myKeys.length; i++) {
            
            final int keyEvent = myValues[i];
            final Object keyObject = Integer.valueOf(keyEvent);
            final String keyString = KeyEvent.getKeyText((Integer) keyObject);
            
            //final String text = ;
            
            paintText(g2d, myKeys[i].toUpperCase() + ": " + keyString, Y_COR + i 
                      * TEXT_INCREMENT, font, renderContext);
            

        }        
    }
    
    /**
     * Paints a text to the string.
     * @param theGraphics (The graphic I want to paint the text one).
     * @param theText (The String I want painted).
     * @param theIncrement (an integer to represent the increment of the y coordinate).
     * @param theFont (The Font object that represents what the font should be).
     * @param theRContext (The FontRenderContext).
     */
    private void paintText(final Graphics theGraphics, final String theText, 
                           final int theIncrement, final Font theFont, 
                           final FontRenderContext theRContext) {
        
        final GlyphVector glyphVector = theFont.createGlyphVector(theRContext, theText);

        final Rectangle2D visualBounds = glyphVector.getVisualBounds().getBounds();
        final int x =
                (int) ((getWidth() - visualBounds.getWidth()) / 2 
                                - visualBounds.getX());

        theGraphics.drawString(theText, x, theIncrement);
    }
    
    /**
     * The array of string noting the key names.
     * @return myKeys (String array with key names).
     */
    public String[] getKeyNames() {
        return (String[]) myKeys.clone();
    }
    
}
