/* 
 * TCSS 305 – Spring 2016.
 * 
 * A Test for the Item class.
*/

package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.Item;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the Item class.
 * 
 * @author Peter M Chu.
 * @version 13 April, 2016.
 */
public class ItemTest {

    /** An Item object to use for the tests. */
    private Item myItem;
    
    /** . */
    @Before
    public void setUp() throws Exception {
        
        //some test methods make a new instance of myItem (for invalid inputs)
        myItem = new Item("ramen", new BigDecimal("1.00"), 10, new BigDecimal("5.00"));
    }

    /** Tests the constructor to make the instance of Item I create is the type 
     * of Item I wanted. */
    @Test
    public void testItemStringBigDecimal() {
        
        myItem = new Item("ramen", new BigDecimal("1.00"));
        assertEquals(myItem.getPrice(), new BigDecimal("1.00"));
        assertEquals(myItem.toString(),"ramen, $1.00");
    }
    
    /** Tests the overloaded constructor to make the instance of Item I create is the type 
     * of Item I wanted. */
    @Test
    public void testItemStringBigDecimalIntBigDecimal() {
        assertEquals(myItem.getBulkPrice(), new BigDecimal("5.00"));
        assertEquals(myItem.getBulkQuantity(), 10);
        assertEquals(myItem.getPrice(), new BigDecimal("1.00"));
        assertEquals(myItem.toString(),"ramen, $1.00 (10 for $5.00)");
    }
    
    /** Test to make sure theName is not null. */
    @Test(expected = NullPointerException.class)
    public void testItemNullName() {
        
        myItem = new Item((String) null, new BigDecimal("1.00"), 10, new BigDecimal("5.00"));
    }   
    
    /** Test to make sure thePrice is not null. */
    @Test(expected = NullPointerException.class)
    public void testItemNullPrice() {
        myItem = new Item("ramen", (BigDecimal) null, 10, new BigDecimal("5.00"));
    }
    
    /** Test to make sure theBulkQuantity is not null. */
    @SuppressWarnings("null")
    @Test(expected = NullPointerException.class)
    public void testItemNullBulkQuantity() {

        myItem = new Item("ramen", new BigDecimal("1.00"), (Integer) null, 
                          new BigDecimal("5.00"));
    }
    
    /** Test to make sure theBulkPrice is not null. */
    @Test(expected = NullPointerException.class)
    public void testItemNullBulkPrice() {
        
        myItem = new Item("ramen", new BigDecimal("1.00"), 10, (BigDecimal) null);
    }
    
    /** . */
    @Test(expected = IllegalArgumentException.class)
    public void testItemInvalidPrice() {

        myItem = new Item("ramen", new BigDecimal("-1.00"), 10, new BigDecimal("5.00"));
    }
    
    /** . */
    @Test(expected = IllegalArgumentException.class)
    public void testItemInvalidBulkPrice() {

        myItem = new Item("ramen", new BigDecimal("1.00"), 10, new BigDecimal("-5.00"));
    }
    
    /** Test to make sure the hash code is what I'm expecting. */
    @Test
    public void testHashCode() {
        
        assertEquals(myItem.hashCode(), 216512046);
    }

    
    /** Test to make sure if the isBulk() method returns the boolean I'm expecting. */
    @Test
    public void testIsBulk() {
        assertEquals(myItem.isBulk(), true);
        myItem = new Item("ramen", new BigDecimal("1.00"));
        assertEquals(myItem.isBulk(), false);
    }

    /** Test to make sure the toString() method returns the string I'm expecting. */
    @Test
    public void testToString() {
        
        assertEquals(myItem.toString(),"ramen, $1.00 (10 for $5.00)");
        myItem = new Item("ramen", new BigDecimal("1.00"));
        assertEquals(myItem.toString(),"ramen, $1.00");
    }

    /** Test that the equals() method can actually identify equal Item Objects. */
    @Test
    public void testEqualsObject1() {
        
        assertEquals(myItem.equals(myItem), true);
        
        final Item newItem = new Item("ramen", new BigDecimal("1.00"), 10, 
                                      new BigDecimal("5.00"));
        assertEquals(myItem.equals(newItem), true);
        
        assertEquals(myItem.equals((Item) null), false);
        
        final String notItem = "ramen, $1.00 (10 for $5.00)";
        //I am intentionally comparing different types of Objects below this line
        assertEquals(myItem.equals(notItem), false); 
    }
    
    /** Additional test that the equals() method can actually identify equal Item Objects. */
    @Test
    public void testEqualsObject2() {
        
        Item newItem = new Item("pamen", new BigDecimal("1.00"), 10, new BigDecimal("5.00"));
        assertEquals(myItem.equals(newItem), false);
        newItem = new Item("ramen", new BigDecimal("1.01"), 10, new BigDecimal("5.00"));
        assertEquals(myItem.equals(newItem), false);
        newItem = new Item("ramen", new BigDecimal("1.00"), 11, new BigDecimal("5.00"));
        assertEquals(myItem.equals(newItem), false);
        newItem = new Item("ramen", new BigDecimal("1.00"), 10, new BigDecimal("5.01"));
        assertEquals(myItem.equals(newItem), false);

    }
}
