package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Discount;
import sg.edu.nus.iss.se23pt2.pos.MemberDiscount;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class DiscountDSTest extends TestCase{
    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    Discount discount1 = null;
    Discount discount2 = null;
    ArrayList<Discount> discounts = null;

    @Before
    public void setUp() throws Exception {
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getDiscountDS();
        store = new SouvenirStore();
        discount1 = new MemberDiscount("Discount1", "Member Discount", "01-01-2015", "120", 20, "M");
        discount2 = new Discount("Discount2", "Non-member Discount", "ALWAYS", "ALWAYS", 5, "A");
    }
    
    @After
    public void tearDown() throws Exception {
        ds.remove(discount1);
        ds.remove(discount2);
        ds.close();
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(discount1);
            discounts = ds.load(store);
            assertTrue("Discount save failed", discounts.contains(discount1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            int newDiscountPercentage = 25;
            ds.create(discount1);
            discount1.setDiscountPercentage(newDiscountPercentage);
            ds.update(discount1);
            discounts = ds.load(store);
            assertSame("Discount Update failed", discounts.get(discounts.size()-1).getDiscountPercentage(), newDiscountPercentage);
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }catch(UpdateFailedException e){
            assertFalse("Update failed", true);
        }
    }

    @Test 
    public void testLoad(){
        try{
            ds.create(discount1);
            discounts = ds.load(store);
            assertTrue("Discount save failed", discounts.contains(discount1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        } 
    }

    @Test
    public void testRemove(){
        assertTrue(true); //No requirement to remove a Discount
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(DiscountDSTest.class);
        return suite;
    }
}
