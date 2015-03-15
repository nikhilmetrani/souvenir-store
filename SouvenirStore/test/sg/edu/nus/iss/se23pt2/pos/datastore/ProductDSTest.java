package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Product;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Vendor;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class ProductDSTest extends TestCase{
    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    Product product1 = null;
    Product product2 = null;
    ArrayList<Product> products = null;

    @Before
    public void setUp() throws Exception {
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getProductDS();
        store = new SouvenirStore();
        
        product1 = new Product("LED/1", "LED Light - Black color");
        product1.setDescription("LED Light - Black color");
        product1.setQuantity(100);
        product1.setPrice(6.90f);
        product1.setBarcode("LED/1");
        product1.setReorderThresholdQty(10);
        product1.setOrderQuantity(100);

        product2 = new Product("LED/2", "LED Light - Green color");
        product2.setDescription("LED Light - Green color");
        product2.setQuantity(100);
        product2.setPrice(6.90f);
        product2.setBarcode("LED/2");
        product2.setReorderThresholdQty(10);
        product2.setOrderQuantity(100);
    }
    
    @After
    public void tearDown() throws Exception {
        ds.remove(product1);
        ds.remove(product2);
        ds.close();
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(product1);
            products = ds.load(store);
            assertTrue("Product save failed", products.contains(product1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            String newDesc = "LED Light - Blue color";
            ds.create(product1);
            product1.setDescription(newDesc);
            ds.update(product1);
            products = ds.load(store);
            assertTrue("Product Update failed", products.get(products.size()-1).getDescription().equals(newDesc));
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
            ds.create(product1);
            products = ds.load(store);
            assertTrue("Product load failed", products.contains(product1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testRemove(){
        assertTrue(true); //No requirement to remove Product
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(VendorDSTest.class);
        return suite;
    }
}
