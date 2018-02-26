/* 
 * TCSS 305 – Spring 2016.
 * 
 * A Test for the ItemOrder class.
*/

package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.Item;
import model.ItemOrder;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the ItemOrder class.
 * 
 * @author Peter M Chu.
 * @version 13 April, 2016.
 */
public class ItemOrderTest {

    /** An ItemOrder object to use for the tests. */
    private ItemOrder myOrder;
    
    /** Set up method before each test method is executed. */
    @Before
    public void setUp() {
        
        //The test methods may make a new instance of myOrder
        myOrder = new ItemOrder(new Item("cookie", new BigDecimal("1.00")), 5);
    }
    
    /** Tests to make sure inputting a null in the Item argument returns an exception, 
     * whether the Item object itself has a null value inside will be tested in the 
     * ItemTest. */
    @Test(expected = NullPointerException.class)
    public void testItemOrderNullItem() {
        
        myOrder = new ItemOrder((Item) null, 5);
    }
    
    /** Tests to make sure inputting a null in the quantity argument returns a null 
     * exception. */
    @SuppressWarnings("null")
    @Test(expected = NullPointerException.class)
    public void testItemOrderNullQuantity() {
        
        myOrder = new ItemOrder(new Item("cookie", new BigDecimal("1.00")), (Integer) null);
    }

    /** Tests to make sure that inputting a negative value for the quantity argument 
     * is invalid. */
    @Test(expected = IllegalArgumentException.class)
    public void testItemOrderIllegalQuantity() {
        
        myOrder = new ItemOrder(new Item("cookie", new BigDecimal("1.00")), -2);
    }
    
    /** Tests to make sure the getItem() returns the Item object I am expecting. */
    @Test
    public void testGetItem() {
 
        assertEquals(myOrder.getItem().toString(), "cookie, $1.00");
    }

    /** Tests to make sure the quantity I entered is what I get back. */
    @Test
    public void testGetQuantity() {
        
        assertEquals(myOrder.getQuantity(), 5);
    }

    /** Tests to make sure the toSting() method returns what I'm expecting. */
    @Test
    public void testToString() {

        assertEquals(myOrder.toString(), "Item: cookie, $1.00, Quantity: 5");
    }

}
