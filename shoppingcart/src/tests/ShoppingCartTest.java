/* 
 * TCSS 305 – Spring 2016.
 * 
 * A Test for the ShoppingCart class.
*/

package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.Item;
import model.ItemOrder;
import model.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the ShoppingCart class.
 * 
 * @author Peter M Chu.
 * @version 13 April, 2016.
 */
public class ShoppingCartTest {
    
    /** A ShoppingCart object to use for the tests. */
    private ShoppingCart myCart;
    
    /**
     * A method to initialize the test ShoppingCart fixture before each test.
     */
    @Before
    public void setUp() {
        
        myCart = new ShoppingCart();
    }

    /**
     * Tests to make sure the constructor made an instance ShoppingCart.
     */
    @Test
    public void testShoppingCart() {
        
        assertEquals(myCart.toString(), "Number of ItemOrders: 0, Cost: $0.00");
    }

    /**
     * Tests to make sure the add method adds the ItemOrder into the ShoppingCart and 
     * also takes over a identical Item.
     */
    @Test
    public void testAdd() {
        
        myCart.add(new ItemOrder(new Item("cookie", new BigDecimal("0.50")), 5));
        assertEquals(myCart.calculateTotal(), new BigDecimal("2.50"));
        myCart.add(new ItemOrder(new Item("cookie", new BigDecimal("0.50")), 10));
        assertEquals(myCart.calculateTotal(), new BigDecimal("5.00"));
    }

    /**
     * Tests the method when a null value is entered.
     * Whether the ItemOrder object has a null within it will be tested in the ItemOrderTest.
     */
    @Test(expected = NullPointerException.class)
    public void testAddNull() {
        
        myCart.add((ItemOrder) null);
        //myCart.add(new ItemOrder(new Item("cookie", new BigDecimal("0.50")), 5));
    }
    
    /**
     * Tests to see if the setMembership method changes the myMembership boolean.
     */
    @Test
    public void testSetMembership() {
        
        myCart.setMembership(true);
        assertEquals(myCart.toString(), 
                     "Number of ItemOrders: 0, Cost: $0.00 (Has Membership)");
        myCart.setMembership(false);
        assertEquals(myCart.toString(), "Number of ItemOrders: 0, Cost: $0.00");
    }

    /**
     * Tests calculateTotal() to make sure the calculation is done correctly.
     */
    @Test
    public void testCalculateTotal() {
        
        
        myCart.add(new ItemOrder(new Item("ramen", new BigDecimal("3.00"), 3, 
                                          new BigDecimal("5.00")), 5)); 
        myCart.add(new ItemOrder(new Item("cookie", new BigDecimal("0.50")), 5));
        assertEquals(myCart.calculateTotal(), new BigDecimal("17.50"));
        
        myCart.setMembership(true);
        //Expected cost for ramen: 5.00 + 3.00 + 3.00 = 11.00
        //Expected cost for cookie: 2.50
        assertEquals(myCart.calculateTotal(), new BigDecimal("13.50"));
    }

    /**
     * Tests the clear method to make sure all ItemOrders are erased from the ShoppingCart 
     * object.
     */
    @Test
    public void testClear() {
        
        myCart.add(new ItemOrder(new Item("cookie", new BigDecimal("0.50")), 5));
        myCart.clear();
        assertEquals(myCart.calculateTotal(), new BigDecimal("0.00"));
    }

    /**
     * Tests to make sure to .
     */
    @Test
    public void testToString() {
        
        assertEquals(myCart.toString(), "Number of ItemOrders: 0, Cost: $0.00");
        myCart.add(new ItemOrder(new Item("cookie", new BigDecimal("0.50")), 5));
        assertEquals(myCart.toString(), "Number of ItemOrders: 1, Cost: $2.50");
    }

}
