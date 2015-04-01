package sg.edu.nus.iss.se23pt2.pos.datastore;

import java.util.ArrayList;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Category;
import sg.edu.nus.iss.se23pt2.pos.Member;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class MemberDSTest extends TestCase{
    DataStoreFactory dsFactory = null;
    DataStore ds = null;
    SouvenirStore store = null;
    Member member1 = null;
    Member member2 = null;
    ArrayList<Member> members = null;

    @Before
    public void setUp() throws Exception {
        dsFactory = DataStoreFactory.getInstance();
        ds = dsFactory.getMemberDS();
        store = new SouvenirStore();
        member1 = new Member("Member1", "1234");
        member1.addLoyaltyPoints(100);
        member2 = new Member("Member2", "5678");
    }
    
    @After
    public void tearDown() throws Exception {
        ds.remove(member1);
        ds.remove(member2);
        ds.close();
    }
    
    @Test
    public void testCreate(){
        try{
            ds.create(member1);
            members = ds.load(store);
            assertTrue("Member save failed", members.contains(member1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        }
    }

    @Test
    public void testUpdate(){
        try{
            int newLoyaltyPoints = 20;
            int curLoylatyPoints = member1.getLoyaltyPoints();
            ds.create(member1);
            member1.addLoyaltyPoints(newLoyaltyPoints);
            ds.update(member1);
            members = ds.load(store);
            assertSame("Member Update failed", members.get(members.size()-1).getLoyaltyPoints(), curLoylatyPoints + newLoyaltyPoints);
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
            ds.create(member1);
            members = ds.load(store);
            assertTrue("Member load failed", members.contains(member1));
        }catch(CreationFailedException e){
            assertFalse("Save failed", true);
        }catch(DataLoadFailedException e){
            assertFalse("Data Load failed", true);
        } 
    }

    @Test
    public void testRemove(){
        assertTrue(true); //No requirement to remove a Member
    }

    public static TestSuite suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(MemberDSTest.class);
        return suite;
    }
}
