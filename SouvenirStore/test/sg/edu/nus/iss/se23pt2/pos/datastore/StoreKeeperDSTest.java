package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class StoreKeeperDSTest extends TestCase{

    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    StoreKeeper storekeeper1 = null;
    StoreKeeper storekeeper2 = null;
    ArrayList<StoreKeeper> storekeepers = null;

    @Before
    public void setUp() throws Exception{
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getStoreKeeperDS();
        store = new SouvenirStore();
        storekeeper1 = new StoreKeeper("Tom", "abcd1234");
        storekeeper2 = new StoreKeeper("Jerry", "efgh5678");
    }
    
    @After
    public void tearDown() throws Exception{
        ds.remove(storekeeper1);
        ds.remove(storekeeper2);
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(storekeeper1);
            storekeepers = ds.load(store);
            assertTrue("StoreKeeper save failed", storekeepers.contains(storekeeper1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            String oldPassword = "abcd1234";
            String newPassowrd = "Abcd1234";
            ds.create(storekeeper1);
            storekeeper1.setPassword(oldPassword, newPassowrd, newPassowrd);
            ds.update(storekeeper1);
            storekeepers = ds.load(store);
            assertTrue("StoreKeeper Update failed", storekeepers.get(storekeepers.size()-1).validatePassword(newPassowrd));
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
            ds.create(storekeeper1);
            storekeepers = ds.load(store);
            assertTrue("StoreKeeper load failed", storekeepers.contains(storekeeper1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        } 
    }

    @Test
    public void testRemove(){
        assertTrue(true); //No requirement to remove a StoreKeeper
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(StoreKeeperDSTest.class);
        return suite;
    }

}
