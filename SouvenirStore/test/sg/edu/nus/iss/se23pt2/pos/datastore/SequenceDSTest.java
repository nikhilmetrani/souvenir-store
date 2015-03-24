package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Sequence;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class SequenceDSTest extends TestCase{

    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    Sequence sequence1 = null;
    Sequence sequence2 = null;
    ArrayList<Sequence> sequences = null;

    @Before
    public void setUp() throws Exception{
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getSequenceDS();
        store = new SouvenirStore();
        sequence1 = new Sequence("Seq1");
        sequence2 = new Sequence("Seq2");
        sequence2.setPrefix("SEQ");
    }
    
    @After
    public void tearDown() throws Exception{
        ds.remove(sequence1);
        ds.remove(sequence2);
        ds.close();
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(sequence1);
            sequences = ds.load(store);
            assertTrue("Sequence save failed", sequences.contains(sequence1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            ds.create(sequence1);
            String newSequence = sequence1.getNextSequence(); //This increments the Sequnce.nextNumber
            Integer nextNumber = sequence1.getNextNumber();
            ds.update(sequence1);
            sequences = ds.load(store);
            assertEquals("Sequence Update failed", sequences.get(sequences.size()-1).getNextNumber(), nextNumber);
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
            ds.create(sequence1);
            sequences = ds.load(store);
            assertTrue("StoreKeeper load failed", sequences.contains(sequence1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        } 
    }

    @Test
    public void testRemove(){
        assertTrue(true); //No requirement to remove a Sequence
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SequenceDSTest.class);
        return suite;
    }

}
