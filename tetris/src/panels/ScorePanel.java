/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * A panel to let you see the next piece.
 */

package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The panel that shows the user the score of the game.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class ScorePanel extends JPanel implements PropertyChangeListener {
    
    /** The number of lines before one levels up. */
    private static final int LINES_TILL_LVL = 5;
    
    /** The highest line index of line array. */
    private static final int NUM_LINES = 4;
    
    /** The preferred dimensions. */
    private static final Dimension PREFERRED_SIZE = new Dimension(100, 50);
    
    /** Default Font Size. */
    private static final int FONT_SIZE = 11;
    
    /** The y-coordinate of the text. */
    private static final int Y_COR = 30;
    
    /** The increment space between my control text representations. */
    private static final int TEXT_INCREMENT = 15;
    
    /** The font. */
    private static final Font FONT = new Font(Font.SERIF, Font.BOLD 
                                              + Font.ITALIC, FONT_SIZE);
    
    /** Generated serial number. */
    private static final long serialVersionUID = 1730981322243596391L;
    
    /** An integer that represents what level you are on. */
    private int myLevel;
    
    /** An integer representing your score. */
    private int myScore;
    
    /** The number of lines cleared. */
    private int myLines;

    /** 
     * The constructor for this panel. 
     * @param thePlayer (String that tells me which player this is).
     */
    public ScorePanel(final String thePlayer) {
        super();
        setBackground(Color.WHITE);
        setPreferredSize(PREFERRED_SIZE);
        this.setBorder(BorderFactory.createTitledBorder(thePlayer));
        myLevel = 1;
        myScore = 0;
        myLines = 0;
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(FONT);
        final FontRenderContext renderContext = g2d.getFontRenderContext(); 

        final String[] lines = {" Level: " + myLevel, " Score: " + myScore, 
                                " Lines cleared: " + myLines, 
                                LINES_TILL_LVL - (myLines % LINES_TILL_LVL) 
                                + " lines till level"};
        
        for (int i = 0; i <= NUM_LINES - 1; i++) {
            paintString(lines[i], g2d, renderContext, Y_COR + TEXT_INCREMENT * i);
        }
        
    }
    
    /**
     * Paints a string on a graphic.
     * @param theString (The string you want painted).
     * @param theGraphics (The graphic you want to paint on).
     * @param theRender (The FontRenderContext).
     * @param theY (The y-coordinate as an integer).
     */
    private void paintString(final String theString, final Graphics2D theGraphics, 
                             final FontRenderContext theRender, final int theY) {
        final int x = getXText(theString, theRender);
        theGraphics.drawString(theString, x, theY);
    }
    
    /**
     * Gets the x-coordinate that centers a string horizontally.
     * @param theString (The string you want on the board).
     * @param theRContext (The render context given a set font and relative to the string).
     * @return an integer x-coordinate that centers a string horizontally
     */
    private int getXText(final String theString, final FontRenderContext theRContext) {
        final GlyphVector glyphVector = FONT.createGlyphVector(theRContext, theString);

        final Rectangle2D visualBounds = glyphVector.getVisualBounds().getBounds();
                
        return (int) ((getWidth() - visualBounds.getWidth()) / 2  - visualBounds.getX());
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("points".equals(theEvent.getPropertyName())) {
            myScore = (int) theEvent.getNewValue();
        } else if ("level".equals(theEvent.getPropertyName())) {
            myLevel = (int) theEvent.getNewValue();
        } else if ("linesCleared".equals(theEvent.getPropertyName())) {
            myLines += (int) theEvent.getNewValue();
        } else if ("clearLines".equals(theEvent.getPropertyName())) {
            myLines = 0;
        }
        repaint();
    }

}
