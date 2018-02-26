/*
 * TCSS 305
 * 
 * An ItemOrder class that allows you to order a certain item in your shopping cart.
 */

package model;

import java.util.Objects;

/**
 * Represents one Order you want of an Item.
 * 
 * @author Peter M Chu.
 * @version April 12, 2016.
 */
public final class ItemOrder {
    
    /** The item that was ordered. */
    private final Item myItem;
    
    /** The amount of items you want to order. */
    private final int myQuantity;
    
    /**
     * Construct the order of the Item of your desire with the quantity.
     * 
     * @param theItem (An Item object that represents the item you want).
     * @param theQuantity (An integer that represents the amount of the desired item).
     */
    public ItemOrder(final Item theItem, final int theQuantity) {
        
        myItem = Objects.requireNonNull(theItem);
        final int zero = 0;
        if (theQuantity < zero) {
            throw new IllegalArgumentException("The quantity the item cannot be negative!");
        }
        myQuantity = Objects.requireNonNull(theQuantity);
    }


    /**
     * what is the item I ordered?
     * 
     * @return the item that you wanted in this order.
     */
    public Item getItem() {
        
        return myItem;
    }
    
    /**
     * What is the quantity of the item I ordered?
     * 
     * @return the quantity you ordered of the item of your desire.
     */
    public int getQuantity() {
        
        return myQuantity;
    }


    /**
     * {@inheritDoc}
     * 
     * The String representation of this ItemOrder will be formatted as follows:
     * <br>Item: myItem, Quantity: myQuantity.
     */
    @Override
    public String toString() {
        
        final StringBuilder builder = new StringBuilder(18); // default initial size = 16
        builder.append("Item: ");
        builder.append(myItem); // the class name without the package name
        builder.append(", Quantity: ");
        builder.append(myQuantity);
        return builder.toString();
    }

}
