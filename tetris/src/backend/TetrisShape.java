/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * Creates a type of shape for the tetris block.
 */

package backend;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;

/**
 * The type of tetris block shape.
 * 
 * @author Peter M Chu
 * @version 06/02/2016
 */
public class TetrisShape {

    /** String representation of rectangle. */
    private static final String RECTANGLE = "rectangle";
    
    /** The integer for rounding a RoundedRectangle. */
    private static final int ROUNDED_REC_NUM = 10;
    
    /** String representation of rounded rectangle. */
    private static final String ROUNDED_REC = "rounded rec";
    
    /** The integer for rounding a RoundedRectangle to an ellipse. */
    private static final int ROUNDED_REC_ELL = 50;
    
    /** String representation of rectangle. */
    private static final String ELLIPSE = "ellipse";
    
    /** The RectangularShape object (the specific shape will be dependent on inputs). */
    private RectangularShape myShape;
    
    /**
     * Constructs the TetrisShape.
     * @param x1 (the first x-coordinate).
     * @param y1 (the first y-coordinate).
     * @param x2 (the second x-coordinate).
     * @param y2 (the second y-coordinate).
     * @param theShape (the string representation of the shape).
     */
    public TetrisShape(final int x1, final int y1, final int x2, final int y2, 
                       final String theShape) {
        makeShape(x1, y1, x2, y2, theShape);
    }
    /** 
     * Given two x-coordinates and two y-coordinates, a Rectangular shape.
     * depending on what myShape is.
     * @param x1 (the first x-coordinate).
     * @param y1 (the first y-coordinate).
     * @param x2 (the second x-coordinate).
     * @param y2 (the second y-coordinate).
     * @param theShape (the string representation of the shape).
     */
    public void makeShape(final int x1, final int y1, final int x2, final int y2,
                          final String theShape) {
        
        if (RECTANGLE.equals(theShape)) {
            myShape = new Rectangle2D.Double(x1, y1, x2, y2);
        } else if (ROUNDED_REC.equals(theShape)) {
            myShape = new RoundRectangle2D.Double(x1, y1, x2, y2, ROUNDED_REC_NUM, 
                                                ROUNDED_REC_NUM);
        } else if (ELLIPSE.equals(theShape)) {
            myShape = new RoundRectangle2D.Double(x1, y1, x2, y2, ROUNDED_REC_ELL, 
                                                ROUNDED_REC_ELL);
        } else {
            myShape = new Rectangle2D.Double(0, 0, 0, 0);
        }
        
    }
    
    /**
     * Returns a RectangularShape.
     * @return myShape (the type of RectangularShape).
     */
    public RectangularShape getShape() {
        return (RectangularShape) myShape.clone();
    }

}
