/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * A panel to let you see the next piece.
 */

package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.MovableTetrisPiece;
import model.Point;
import model.Rotation;
import model.TetrisPiece;

/**
 * The panel that shows the user the next piece that is coming in the TetrisGUI.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class ViewNextPanel extends JPanel implements PropertyChangeListener {
    
    /** The preferred dimensions. */
    private static final Dimension PREFERRED_SIZE = new Dimension(100, 50);
    
    /** The index on a a MovableTetrisPiece's string which is the least index I want. */
    private static final int PIECE_PREFIX_INDEX = 12;
    
    /** The index of a substring piece string that lets me capture the piece. */
    private static final int PIECE_INDEX = 9;
    
    /** The index of a substring piece string that lets me capture the I piece. */
    private static final int I_INDEX = 4;
    
    /** The amount of pixels representing half an increment. */
    private static final int HALF_INC = 5;
    
    //** The amount I want to increment when converting from point to pixel. */
    //private static final int INCREMENT = 20;
    
    /** The height and width I want the blocks of myPiece to be. */
    private static final int HEIGHT_N_WIDTH = 10;
    
    /** Generated serial number. */
    private static final long serialVersionUID = 529244364436736042L;
    
    /** The player that is playing. */
    private final String myPlayer;
    
    /** The new piece that will be generated. */
    private TetrisPiece myPiece;
    
    /** The version of myPiece I can rotate. */
    private MovableTetrisPiece myMovablePiece;
    
    /** The string representation of myMovablePiece. */
    private String myMovPStr;
    
    /** The color myPiece. */
    private Color myPieceColor;
    
    /** 
     * Constructs the panel. 
     * @param thePlayer (String that tells me which player this is). 
     */
    public ViewNextPanel(final String thePlayer) {
        super();
        setBackground(Color.WHITE);
        setPreferredSize(PREFERRED_SIZE);
        this.setBorder(BorderFactory.createTitledBorder(thePlayer));
        myMovPStr = "";
        myPlayer = thePlayer;
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        //System.out.println(this.getHeight() + " " + this.getWidth());
        
        final int tempXCor = this.getWidth() / 2;
        int xCor = tempXCor - (tempXCor / 2) + HALF_INC;
        
        final int xCopy = xCor;
        
        int yCor = this.getHeight() / 2 - HEIGHT_N_WIDTH;
        
        if (myMovPStr.length() > 0 && myMovPStr.charAt(0) == 'I') {
            yCor += HALF_INC;  
        }
        
        for (int i = 0; i < myMovPStr.length(); i++) {
            
            if (i == I_INDEX) {
                yCor += HEIGHT_N_WIDTH;
                xCor = xCopy;
            }
            
            if (myMovPStr.charAt(i) != ' ') {
                
                g2d.setColor(myPieceColor);
                g2d.fillRect(xCor, yCor, HEIGHT_N_WIDTH, HEIGHT_N_WIDTH);
                g2d.setColor(Color.WHITE);
                g2d.drawRect(xCor, yCor, HEIGHT_N_WIDTH, HEIGHT_N_WIDTH);
            }
            xCor += HEIGHT_N_WIDTH;   
            
        }


    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("stop".equals(theEvent.getPropertyName()) && myPlayer.equals((String) theEvent.
                                                                         getNewValue())) {
            myMovPStr = "";
            repaint();
        } else if ("piece".equals(theEvent.getPropertyName())) {   
            myPiece = (TetrisPiece) theEvent.getNewValue(); 
            
            myMovablePiece = new MovableTetrisPiece(myPiece, new Point(0, 0), Rotation.NONE);
            
            myMovPStr = myMovablePiece.toString().substring(PIECE_PREFIX_INDEX).
                            substring(0, PIECE_INDEX);
            if (myMovPStr.charAt(0) == 'I') {
                myMovPStr = myMovPStr.substring(0, I_INDEX);
            }
            myMovPStr = myMovPStr.replace("\n", "");
            
            
        } else if ("color".equals(theEvent.getPropertyName())) {
            myPieceColor = (Color) theEvent.getNewValue();            
            repaint();
        }
        
    }

}
