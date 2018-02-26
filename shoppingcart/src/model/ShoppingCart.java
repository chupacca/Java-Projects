/*
 * TCSS 305
 * 
 * A ShoppingCart class that allows you to store ItemOrders of Items that you order and 
 * calculates price.
 */

package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents your shopping cart as you order Items.
 * 
 * @author Peter M Chu.
 * @version April 12, 2016.
 */
public class ShoppingCart {

    /** The HashSet stores all your ItemOrders. */
    private Set<ItemOrder> myShoppingCart;
    
    /** The boolean that states whether you having membership is true or false. */
    private boolean myMembership;
    
    /**
     * Constructs the shopping cart given the constructor is called without arguments.
     */
    public ShoppingCart() {
        
        myShoppingCart = new HashSet<ItemOrder>();
        myMembership = false;
    }


    /**
     * Adds an order of an Item and replaces a a previously existing one if one previously 
     * exists.
     * 
     * @param theOrder (ItemOrder object that represents your order of a certain Item).
     */
    public void add(final ItemOrder theOrder) {
        
        final ItemOrder orderCopy = Objects.requireNonNull(theOrder);
        
        for (final ItemOrder inShoppingCart: myShoppingCart) {
            
            final Item cartItem = inShoppingCart.getItem();
            final Item paraItem = orderCopy.getItem();
            if (cartItem.equals(paraItem)) {
                myShoppingCart.remove(inShoppingCart);
                break;
            }
        }
        myShoppingCart.add(theOrder);
    }


    /**
     * Allows you to set or undo your membership.
     * 
     * @param theMembership (a boolean that represents if you do or don't have a membership).
     */
    public void setMembership(final boolean theMembership) {
        
        myMembership = Objects.requireNonNull(theMembership);
    }


    /**
     * What is the cost of my order(s)?
     * 
     * @return a BigDecimal object that represents the total cost of your orders.
     */
    public BigDecimal calculateTotal() {
        
        BigDecimal cost = BigDecimal.ZERO;
        
        for (final ItemOrder inShoppingCart: myShoppingCart) {
            
            final Item cartItem = inShoppingCart.getItem();
            
            final BigDecimal itemCost = cartItem.getPrice();
            
            int orderQuantity = inShoppingCart.getQuantity();
            
            final int bulkQuantity = cartItem.getBulkQuantity();
            final BigDecimal bulkCost = cartItem.getBulkPrice();
            
            if (cartItem.isBulk() && myMembership) {
                final int zero = 0;
                while (orderQuantity - bulkQuantity >= zero) {
                    orderQuantity -= bulkQuantity;
                    cost = cost.add(bulkCost); 
                }
            }
            
            final BigDecimal quantityCost = 
                            itemCost.multiply(new BigDecimal(orderQuantity));
            cost = cost.add(quantityCost);d
        }
        
        return cost.setScale(2, RoundingMode.HALF_EVEN);
    }
    
    /**
     * This method allows you remove all previous orders made.
     */
    public void clear() {
        
        myShoppingCart = new HashSet<ItemOrder>();
    }

    /**
     * {@inheritDoc}
     * 
     * The String representation of this ShoppingCart will be formatted to have
     * a list of all the items and the total cost of all the items:
     * <br>Items: item(quantity) Cost: $calculateTotal().
     */
    @Override
    public String toString() {
        
        final StringBuilder builder = new StringBuilder(50);
        builder.append("Number of ItemOrders: ");
        builder.append(myShoppingCart.size());
        builder.append(", Cost: $");
        builder.append(calculateTotal());
        if (myMembership) {
            builder.append(" (Has Membership)");
        }
        return builder.toString();
    }

}
