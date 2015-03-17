package sg.edu.nus.iss.se23pt2.pos.datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.Customer;
import sg.edu.nus.iss.se23pt2.pos.Item;
import sg.edu.nus.iss.se23pt2.pos.Product;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Transaction;

/**
 * 
 * @author Rushabh Shah
 * Date    18/3/2015
 * 
 */

public class TransactionTest {
	 	private DataStoreFactory dsFactory;
	 	private DataStore ds;
	 	private SouvenirStore store;
	 	private Transaction transaction1;
	 	private Transaction transaction2;
	 	private Transaction transaction3;
	 	private Item item;
	 	private SimpleDateFormat dateFormat;
	 	private Date date1,date2;


	@Before
	public void setUp() throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date1 = dateFormat.parse("2013-09-28");
		date2 = dateFormat.parse("2012-02-12");
		dsFactory = DataStoreFactory.getInstance();
	    ds = dsFactory.getTransactionDS();		
		transaction1 = new Transaction();
		Customer cust = new Customer();
		cust.setId("Fkldjf234");
		item = new Item();
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
		
		transaction1.setDate("2013-09-28");
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
		items2.add(item);
		items2.add(item1);
		items2.add(item2);
		items2.add(item3);
		
		transaction2.setDate("2012-02-12");
		transaction2.setItems(items2);
		transaction2.setId(22);
		transaction2.setCustomer(cust2); 
		
		
		Customer cust3 = new Customer();
		cust3.setId("JKSDKDF324");
		transaction3 = new Transaction();
		transaction3.setDate("2012-02-12");
		transaction3.setItems(items);
		transaction3.setId(123);
		transaction3.setCustomer(cust3); 
		
		ds.create(transaction1);
        ds.create(transaction2);
        ds.create(transaction3);
		
        store = new SouvenirStore();
        
	}

	@After
	public void tearDown() throws Exception {
		ds.deleteAll();
	}

	@Test
	public void testTransactionMapSize() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		assertEquals(2,transactionMap.size());		
	}
	
	@Test
	public void testTransactionMapDate() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Set<Date> dateSet = transactionMap.keySet();
		assertTrue(dateSet.contains(date1));	
		assertTrue(dateSet.contains(date2));
		try {
			assertFalse(dateSet.contains(dateFormat.parse("2012-03-20")));
		} catch (ParseException e) {
			assertTrue("Cannot parse the date",false);
		}
		assertEquals(2,dateSet.size());
	}
	
	@Test
	public void testTransactionsForParticularDate() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Set<Date> dateSet = transactionMap.keySet();
		assertTrue(dateSet.contains(date1));	
		ArrayList<Transaction> transactions = transactionMap.get(date1);
		assertNotNull(transactions);
		assertEquals(1,transactions.size());
		assertTrue(transactions.contains(transaction1));		
	}
	
	
	@Test
	public void testMultipleTransactionForParticularDate() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Set<Date> dateSet = transactionMap.keySet();
		assertTrue(dateSet.contains(date2));	
		ArrayList<Transaction> transactions = transactionMap.get(date2);
		assertNotNull(transactions);
		assertEquals(2,transactions.size());
		assertTrue(transactions.contains(transaction2));
		assertTrue(transactions.contains(transaction3));		
	}
	
	
	@Test
	public void testTransactionItemForParticularDate() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Set<Date> dateSet = transactionMap.keySet();
		assertTrue(dateSet.contains(date2));	
		ArrayList<Transaction> transactions = transactionMap.get(date2);
		assertNotNull(transactions);
		assertEquals(2,transactions.size());
		assertTrue(transactions.contains(transaction2));
		assertTrue(transactions.contains(transaction3));	
		ArrayList<Item> items = transaction3.getItems();
		assertNotNull(items);
		assertEquals(2, items.size());
		assertTrue(items.contains(item));
	}
	
	@Test
	public void testSortingOfTransactionItemForParticularDate() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Set<Date> dateSet = transactionMap.keySet();
		assertTrue(dateSet.contains(date2));	
		ArrayList<Transaction> transactions = transactionMap.get(date2);
		assertNotNull(transactions);
		assertEquals(2,transactions.size());
		assertTrue(transactions.contains(transaction2));
		assertTrue(transactions.contains(transaction3));	
		ArrayList<Item> items = transaction2.getItems();
		assertNotNull(items);
		assertEquals(4, items.size());
        assertEquals("LCD/1",items.get(0).getProduct().getId());
        assertEquals("LED/1",items.get(1).getProduct().getId());
        assertEquals("MUG/2",items.get(2).getProduct().getId());
        assertEquals("ZTV/1",items.get(3).getProduct().getId());
	}
	
	
	@Test
	public void testTransactionBetweenDatesBoundaryCondition() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse("2012-02-12");
			endDate = dateFormat.parse("2013-09-28");
		} catch (ParseException e) {
			assertTrue("Cannot parse the date",false);
		}
		
		ArrayList<Transaction> transactionList = store.getTransactionsBetweenDates(startDate,endDate);
		assertNotNull(transactionList);
		assertEquals(3,transactionList.size());
		assertTrue(transactionList.contains(transaction1));
		assertTrue(transactionList.contains(transaction2));
		assertTrue(transactionList.contains(transaction3));		
		
		ArrayList<Item> items = transaction3.getItems();
		assertNotNull(items);
		assertEquals(2, items.size());
		assertTrue(items.contains(item));
	}
	
	
	
	@Test
	public void testTransactionBetweenDates_Date1() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse("2013-09-23");
			endDate = dateFormat.parse("2013-09-29");
		} catch (ParseException e) {
			assertTrue("Cannot parse the date",false);
		}
		
		ArrayList<Transaction> transactionList = store.getTransactionsBetweenDates(startDate,endDate);
		assertNotNull(transactionList);
		assertEquals(1,transactionList.size());
		assertTrue(transactionList.contains(transaction1));
		assertFalse(transactionList.contains(transaction2));
		assertFalse(transactionList.contains(transaction3));		
		
		ArrayList<Item> items = transaction1.getItems();
		assertNotNull(items);
		assertEquals(2, items.size());
		assertTrue(items.contains(item));
	}
	
	
	@Test
	public void testTransactionBetweenDates_Date2() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse("2011-02-12");
			endDate = dateFormat.parse("2012-02-12");
		} catch (ParseException e) {
			assertTrue("Cannot parse the date",false);
		}
		
		ArrayList<Transaction> transactionList = store.getTransactionsBetweenDates(startDate,endDate);
		assertNotNull(transactionList);
		assertEquals(2,transactionList.size());
		assertFalse(transactionList.contains(transaction1));
		assertTrue(transactionList.contains(transaction2));
		assertTrue(transactionList.contains(transaction3));		
		
		ArrayList<Item> items = transaction3.getItems();
		assertNotNull(items);
		assertEquals(2, items.size());
		assertTrue(items.contains(item));
	}
	
	
	@Test
	public void testTransactionBetweenSameStartAndEndDates() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse("2012-02-12");
			endDate = dateFormat.parse("2012-02-12");
		} catch (ParseException e) {
			assertTrue("Cannot parse the date",false);
		}
		
		ArrayList<Transaction> transactionList = store.getTransactionsBetweenDates(startDate,endDate);
		assertNotNull(transactionList);
		assertEquals(2,transactionList.size());
		assertFalse(transactionList.contains(transaction1));
		assertTrue(transactionList.contains(transaction2));
		assertTrue(transactionList.contains(transaction3));		
		
		ArrayList<Item> items = transaction3.getItems();
		assertNotNull(items);
		assertEquals(2, items.size());
		assertTrue(items.contains(item));
	}
	
	@Test
	public void testTransactionOutOfRangeStartAndEndDates() {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse("2011-01-22");
			endDate = dateFormat.parse("2011-06-12");
		} catch (ParseException e) {
			assertTrue("Cannot parse the date",false);
		}
		
		ArrayList<Transaction> transactionList = store.getTransactionsBetweenDates(startDate,endDate);
		assertNotNull(transactionList);
		assertEquals(0,transactionList.size());
		assertFalse(transactionList.contains(transaction1));
		assertFalse(transactionList.contains(transaction2));
		assertFalse(transactionList.contains(transaction3));		
	}
	
	

}
