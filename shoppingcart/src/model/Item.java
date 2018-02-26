/*
 * TCSS 305
 * 
 * An Item class that represents individual Items and any details about that specific Item.
 */

package model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents one specific Item.
 * 
 * @author Peter M Chu.
 * @version April 12, 2016.
 */
public final class Item {
    
    /** the name of my Item. */
    private String myName;
    
    /** the price of my Item. */
    private BigDecimal myPrice;
    
    /** the bulk quantity that my Item where discount applies. */    
    private final int myBulkQuantity;
    
    /** the price of multiples of myBulkQuantity. */
    private final BigDecimal myBulkPrice;
    
    /** the boolean as to whether there is a bulk discount. */
    private final boolean myIsBulk;
    
    /**
     * Constructs an Item given a name and a price.
     * 
     * @param theName (a String of name of the Item).
     * @param thePrice (the price of that Item as a BigDecimal object).
     */
    public Item(final String theName, final BigDecimal thePrice) {

        invalidBigDecimal(thePrice);
        nameAndPrice(theName, thePrice);
        myBulkQuantity = 0;
        myBulkPrice = BigDecimal.ZERO;
        myIsBulk = false;
    }


    /**
     * Constructs an Item given a name, a price, bulk quantity, and bulk price.
     * 
     * @param theName (a String of name of the Item).
     * @param thePrice (the price of that Item as a BigDecimal object).
     * @param theBulkQuantity (the integer of the bulk quantity where a discount applies).
     * @param theBulkPrice (the cost of the every multiple of the bulk quantity as a 
     * BigDecimal object).
     */
    public Item(final String theName, final BigDecimal thePrice, final int theBulkQuantity,
                final BigDecimal theBulkPrice) {
        
        invalidBigDecimal(thePrice);
        nameAndPrice(theName, thePrice);
        myBulkQuantity = Objects.requireNonNull(theBulkQuantity);
        invalidBigDecimal(theBulkPrice); //checks to see if the price is less than 0
        myBulkPrice = Objects.requireNonNull(theBulkPrice);
        myIsBulk = true;
    }
    
    /**
     * To reduce redundancy in code, separate private method was made to set myName and myPrice
     * , however this method is only called from a constructor.
     * 
     * @param theName (a String of name of the Item).
     * @param thePrice (the price of that Item as a BigDecimal object).
     */
    private void nameAndPrice(final String theName, final BigDecimal thePrice) {
        
        myName = Objects.requireNonNull(theName);
        myPrice = Objects.requireNonNull(thePrice);
    }
    
    /**
     * Checks to see if the BigDecimalPrice is less than 0 (made to reduce code redundancy).
     * 
     * @param theBigDecimalPrice (a BigDecimal object representing some type of price).
     */
    private void invalidBigDecimal(final BigDecimal theBigDecimalPrice) {
        
        final int one = 1;
        if (new BigDecimal("0.00").compareTo(theBigDecimalPrice) == one) {
            throw new IllegalArgumentException("The price of the Item cannot be negative!");
        }
    }
    
    /**
     * What is the price of my Item?
     * 
     * @return myPrice (the price of that Item as a BigDecimal object).
     */
    public BigDecimal getPrice() {
        
        return myPrice;
    }
    

    /**
     * What is bulk quantity I can get a discount on my Item?
     * 
     * @return myBulk Quantity (the integer of the bulk quantity where a discount applies).
     */
    public int getBulkQuantity() {
        
        return myBulkQuantity;
    }
    

    /**
     * What is the price I get given a multiple of myBulkQuantity?
     * 
     * @return myBulkPrice (the cost of the every multiple of the bulk quantity as a 
     * BigDecimal object).
     */
    public BigDecimal getBulkPrice() {
        
        return myBulkPrice;
    }

    
    /**
     * Do I have the option to get a discount buying this Item in bulk?
     * 
     * @return a boolean representing if there is a bulk quantity discount.
     */
    public boolean isBulk() {
        
        return myIsBulk;
    }

    /**
     * {@inheritDoc}
     * 
     * The String representation of this Item will be formatted as follows:
     * If there are no bulk variables: <br>myName, $myPrice.
     * If there are bulk variables: <br>myName, $myPrice (myBulkQuantity for $myBulkPrice).
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(16); // default initial size = 16
        builder.append(myName); // the class name without the package name
        builder.append(", ");
        
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        builder.append(nf.format(myPrice));

        if (myIsBulk) {
            builder.append(" (");
            builder.append(myBulkQuantity);
            builder.append(" for $");
            builder.append(myBulkPrice);
            builder.append(')');
        }
        return builder.toString();
    }
    
    /**
     * {@inheritDoc}
     * 
     * This method compares the Item's name, price, bulk price , and bulk quantity.
     */
    @Override
    public boolean equals(final Object theOther) {
        
        boolean result = false;

        if (this == theOther) {
            result = true;
        } else if ((theOther != null) && (theOther.getClass() == getClass())) {
            // cast theOther to the correct type
            final Item otherItem = (Item) theOther;
            result = Objects.equals(myPrice, otherItem.myPrice) 
                            && Objects.equals(myBulkPrice, otherItem.myBulkPrice)
                            && myBulkQuantity == otherItem.myBulkQuantity
                            && myName.equals(otherItem.myName); 
        }
        return result;
    }


    /**
     * {@inheritDoc}
     * 
     * This method gives the hash code of the compared values.
     */
    @Override
    public int hashCode() {
        
        return Objects.hash(myPrice, myBulkPrice, myBulkQuantity, myName);
    }

}
