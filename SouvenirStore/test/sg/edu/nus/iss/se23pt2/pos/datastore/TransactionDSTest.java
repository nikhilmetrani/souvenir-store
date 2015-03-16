package sg.edu.nus.iss.se23pt2.pos.datastore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Customer;
import sg.edu.nus.iss.se23pt2.pos.Item;
import sg.edu.nus.iss.se23pt2.pos.Product;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Transaction;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;

/*
 *  @Author : Rushabh Shah
 *  Date    : 16/3/2015
 * 
 */
public class TransactionDSTest {
	
	    DataStoreFactory dsFactory;
	    DataStore ds;
	    SouvenirStore store;
	    Transaction transaction1;
	    Transaction transaction2;
	    ArrayList<Transaction> transactions;

	    @Before
	    public void setUp() throws Exception {
	        dsFactory = DataStoreFactory.getInstance();
	        ds = dsFactory.getTransactionDS();
	        store = new SouvenirStore();
	       
	        
	        transaction1 = new Transaction();
			Customer cust = new Customer();
			cust.setId("Fkldjf234");
			Item item = new Item();
			item.setPrice(20.5f);
			Product product = new Product();
			product.setId("MUG/2");
			item.setProduct(product);
			item.setQuantity(5);
			
			Item item1 = new Item();
			item1.setPrice(10.15f);
			Product product1= new Product();
			product1.setId("LED/1");
			item1.setProduct(product1);
			item1.setQuantity(25);
			ArrayList<Item> items = new ArrayList<>();
			items.add(item);
			items.add(item1);
			
			transaction1.setDate("12/3/2015");
			transaction1.setItems(items);
			transaction1.setId(21389);
			transaction1.setCustomer(cust);     
	       
			
			transaction2 = new Transaction();
			Customer cust2 = new Customer();
			cust2.setId("Abldjf234");
			Item item2 = new Item();
			item2.setPrice(31.5f);
			Product product2= new Product();
			product2.setId("LCD/1");
			item2.setProduct(product2);
			item2.setQuantity(52);
			
			Item item3 = new Item();
			item3.setPrice(14.15f);
			Product product3= new Product();
			product3.setId("ZTV/1");
			item3.setProduct(product3);
			item3.setQuantity(22);
			ArrayList<Item> items2 = new ArrayList<>();
			items2.add(item2);
			items2.add(item3);
			
			transaction2.setDate("15/2/2015");
			transaction2.setItems(items2);
			transaction2.setId(22);
			transaction2.setCustomer(cust2);   
	    }
	    
	    @After
	    public void tearDown() throws Exception {
	        ds.deleteAll();
	    }
	    
	    @Test
	    public void testCreateAndLoad(){
	        try{
	            ds.create(transaction1);
	            transactions = ds.load(store);	            
	            assertTrue("Transaction save failed", transactions.contains(transaction1));
	        }catch(CreationFailedException e){
	            assertFalse("Save failed", true);
	        }catch(DataLoadFailedException e){
	            assertFalse("Data Load failed", true);
	        }
	    }

	    
	    @Test
	    public void testCreateAndLoadMultipleTransactions(){
	        try{
	        	ds.create(transaction1);
	            ds.create(transaction2);
	            transactions = ds.load(store);	            
	            assertTrue("Transaction save failed", transactions.contains(transaction2));
	        }catch(CreationFailedException e){
	            assertFalse("Save failed", true);
	        }catch(DataLoadFailedException e){
	            assertFalse("Data Load failed", true);
			}
	    }


}
