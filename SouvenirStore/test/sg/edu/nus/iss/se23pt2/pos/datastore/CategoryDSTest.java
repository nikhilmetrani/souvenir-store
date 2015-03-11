package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Category;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Vendor;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class CategoryDSTest extends TestCase{
    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    Category category1 = null;
    Category category2 = null;
    Vendor vendor1 = null;
    Vendor vendor2 = null;
    Vendor vendor3 = null;
    ArrayList<Category> categories = null;

    @Before
    public void setUp() throws Exception {
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getCategoryDS();
        store = new SouvenirStore();
        category1 = new Category("WRAP", "Gift Wraps");
        category2 = new Category("LED", "LED Torch Lights");
        
        vendor1 = new Vendor("ABC", "ABC traders - Supplies Gift Wraps");
        vendor2 = new Vendor("DEF", "DEF traders - Supplies Gift Wraps");
        vendor3 = new Vendor("XYZ", "XYZ traders - Supplies Torch Lights");
        
        category1.addVendor(vendor1);
        category1.addVendor(vendor2);
        category2.addVendor(vendor3);
    }
    
    @After
    public void tearDown() throws Exception {
        ds.remove(category1);
        ds.remove(category2);
        ds.close();
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(category1);
            categories = ds.load(store);
            assertTrue("Category save failed", categories.contains(category1));
            
            category1 = categories.get(categories.size()-1);
            assertSame("Category save failed", category1.getVendors().size(), 2);
            assertTrue("Category save failed", category1.getVendors().get(0).equals(vendor1));
            assertTrue("Category save failed", category1.getVendors().get(1).equals(vendor2));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            String newName = "Colourful Gift Wraps";
            ds.create(category1);
            category1.setName(newName);
            ds.update(category1);
            categories = ds.load(store);
            assertTrue("Category Update failed", categories.get(categories.size()-1).getName().equals(newName));
            
            category1.removeVendor("DEF");
            ds.update(category1);
            categories = ds.load(store);
            assertSame("Category update failed", category1.getVendors().size(), 1);
            assertTrue("Category update failed", !category1.getVendors().contains(vendor2));
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
            ds.create(category1);
            categories = ds.load(store);
            assertTrue("Category load failed", categories.contains(category1));

            category1 = categories.get(categories.size()-1);
            assertSame("Category load failed", category1.getVendors().size(), 2);
            assertTrue("Category load failed", category1.getVendors().get(0).equals(vendor1));
            assertTrue("Category load failed", category1.getVendors().get(1).equals(vendor2));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        } 
    }

    @Test
    public void testRemove(){
        assertTrue(true); //No requirement to remove a Category
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(CategoryDSTest.class);
        return suite;
    }
}
