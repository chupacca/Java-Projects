/*
 * TCSS 305 - Assignment 4: PowerPaint
 * 
 * A PowerPaint GUI ColorChooser.
 */

package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;

/**
 * Demonstrates the use of a color chooser.
 * 
 * @author Daniel M. Zimmerman
 * @author Alan Fowler - minor changes, new comments
 * @version 1.1
 */
public final class ColorChooser {

    /** The active color at the time. */
    private Color myCurrentColor;
    

    /**
     * Private constructor to prevent instantiation.
     */
    public ColorChooser() {
        myCurrentColor = Color.BLACK;
    }
    
    /**
     * Lets the user get the color that was most recently used.
     * @return the most current selected color.
     */
    public Color getColor() {
        return myCurrentColor;
    }
    
    /**
     * Lets the user choose a color.
     */
    public void chooseColor() {
        Color result = null;
        
        result = JColorChooser.showDialog(null, "A Color Chooser", null);

        if (result != null) {
            myCurrentColor =  result;
        }  
    }

    /**
     * Makes and returns an icon of the color chosen.
     * @return an icon that represents the color that was chosen.
     */
    public ImageIcon getSmallDisplayIcon() {

        final BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D square = image.createGraphics();

        square.setColor(myCurrentColor);
        square.fillRect(0, 0, 2, 2);
 
        square.dispose();
        square.setColor(myCurrentColor);
        System.out.println(square);
        return new ImageIcon();
    }
    
    /**
     * Makes an icon with the color of myCurrentColor and returns it.
     * @return an icon with the color of myCurrentColor.
     
    public Icon getColorIcon() {
        Rectangle2D.Double icon = new Rectangle2D.Double();
        icon.set
        return null;
    }*/
    /*
    public static void main(String[] args) {
        ColorChooser c = new ColorChooser();
        c.chooseColor();
    }*/


}

