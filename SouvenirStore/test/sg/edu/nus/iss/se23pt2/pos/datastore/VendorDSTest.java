package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Vendor;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class VendorDSTest extends TestCase{
    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    Vendor vendor1 = null;
    Vendor vendor2 = null;
    ArrayList<Vendor> vendors = null;

    @Before
    public void setUp() throws Exception {
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getVendorDS("TEST");
        store = new SouvenirStore();
        
        vendor1 = new Vendor("ABC", "ABC traders - Supplies Gift Wraps");
        vendor2 = new Vendor("DEF", "DEF traders - Supplies Gift Wraps");
    }
    
    @After
    public void tearDown() throws Exception {
        ds.remove(vendor1);
        ds.remove(vendor2);
        ds.close();
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(vendor1);
            vendors = ds.load(store);
            assertTrue("Vendor save failed", vendors.contains(vendor1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            String newDesc = "ABC Groups - Supplies Gift Wraps";
            ds.create(vendor1);
            vendor1.setDescription(newDesc);
            ds.update(vendor1);
            vendors = ds.load(store);
            assertTrue("Vendor Update failed", vendors.get(vendors.size()-1).getDescription().equals(newDesc));
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
            ds.create(vendor1);
            vendors = ds.load(store);
            assertTrue("Vendor load failed", vendors.contains(vendor1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testRemove(){
        try{
            ds.create(vendor1);
            ds.create(vendor2);
            vendors = ds.load(store);
            assertSame("Vendor load failed", vendors.size(), 2);
            
            ds.remove(vendor1);
            vendors = ds.load(store);
            assertSame("Vendor remove failed", vendors.size(), 1);
            assertTrue("Vendor remove failed", !vendors.contains(vendor1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(RemoveFailedException e) {
            assertFalse("Remove failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(VendorDSTest.class);
        return suite;
    }
}
