package sg.edu.nus.iss.se23pt2.pos;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.se23pt2.pos.constant.TransactionConstant;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStore;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidTransactionException;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;

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
	 	private Date date1,date2,date3;


	@Before
	public void setUp() throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date1 = dateFormat.parse("2013-09-28");
		date2 = dateFormat.parse("2012-02-12");
		date3 = dateFormat.parse("2015-03-22");
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
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(item1);
		
		transaction1.setDate("2013-09-28");
		transaction1.setItems(items);
		transaction1.setId(11);
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
		ArrayList<Item> items2 = new ArrayList<Item>();
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
		if(transaction1!=null){
			ds.remove(transaction1);
		}
		if(transaction2!=null){
			ds.remove(transaction2);
		}
		if(transaction3!=null){
			ds.remove(transaction3);
		}		
	}
	
	@Test
	public void testRemoveAndLoadTransactions() throws RemoveFailedException, AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		ds.remove(transaction1);
		ds.remove(transaction2);
		ds.remove(transaction3);
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();		
		assertEquals(0,transactionMap.size());		
	}

	@Test
	public void testTransactionMapSize() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		assertEquals(2,transactionMap.size());		
	}
	
	@Test
	public void testTransactionMapDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
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
	public void testTransactionsForParticularDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
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
	public void testMultipleTransactionForParticularDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
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
	public void testTransactionItemForParticularDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
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
	public void testSortingOfTransactionItemForParticularDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
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
	public void testTransactionWithNullStartDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = dateFormat.parse("2013-09-27");		 
		try {
			ArrayList<Transaction> transactionList=store.getTransactions(startDate,endDate);
			assertNotNull(transactionList);
			assertEquals(2, transactionList.size());
		} catch (InvalidTransactionException e) {
			Assert.fail("Should not throw an exception");
		}		
	}
	
	@Test
	public void testTransactionWithNullEndDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = dateFormat.parse("2012-02-13");
		Date endDate = null;		 
		try {
			ArrayList<Transaction> transactionList=store.getTransactions(startDate,endDate);
			assertNotNull(transactionList);
			assertEquals(1, transactionList.size());
		} catch (InvalidTransactionException e) {
			Assert.fail("Should not throw an exception");
		}		
	}
	
	@Test
	public void testTransactionWithNullStartAndEndDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = null;
		Date endDate = null;		 
		try {
			ArrayList<Transaction> transactionList=store.getTransactions(startDate,endDate);
			assertNotNull(transactionList);
			assertEquals(3, transactionList.size());
		} catch (InvalidTransactionException e) {
			Assert.fail("Should not throw an exception");
		}		
	}
	
	@Test
	public void testTransactionWithStartDateGreaterThanEndDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
		assertNotNull(transactionMap);
		Date startDate = dateFormat.parse("2015-09-28");
		Date endDate = dateFormat.parse("2013-09-28");		 
		try {
			store.getTransactions(startDate,endDate);
			Assert.fail("Should throw an exception");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.INVALID_DATE_ORDER,e.getMessage());
		}		
	}
	
	@Test
	public void testTransactionBetweenDatesBoundaryCondition() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException, InvalidTransactionException {
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
		
		ArrayList<Transaction> transactionList = store.getTransactions(startDate,endDate);
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
	public void testTransactionBetweenDates_Date1() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException, InvalidTransactionException {
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
		
		ArrayList<Transaction> transactionList = store.getTransactions(startDate,endDate);
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
	public void testTransactionBetweenDates_Date2() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException, InvalidTransactionException {
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
		
		ArrayList<Transaction> transactionList = store.getTransactions(startDate,endDate);
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
	public void testTransactionBetweenSameStartAndEndDates() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException, InvalidTransactionException {
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
		
		ArrayList<Transaction> transactionList = store.getTransactions(startDate,endDate);
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
	public void testTransactionOutOfRangeStartAndEndDates() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException, InvalidTransactionException {
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
		
		ArrayList<Transaction> transactionList = store.getTransactions(startDate,endDate);
		assertNotNull(transactionList);
		assertEquals(0,transactionList.size());
		assertFalse(transactionList.contains(transaction1));
		assertFalse(transactionList.contains(transaction2));
		assertFalse(transactionList.contains(transaction3));		
	}
	
	@Test
	public void testSetNullTransaction() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = null;
		assertNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.TRANSACTION_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testSetNullTransactionId() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.TRANSACTION_ID_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testSetNullTransactionDate() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException ");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.TRANSACTION_DATE_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testSetNullTransactionCustomer() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.TRANSACTION_CUST_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testTransactionCustomerID_Defualt() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		assertNotNull(customer.getId());
		assertEquals("PUBLIC",customer.getId())	;
	}
	
	@Test
	public void testSetTransactionWithEmptyItems() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.ITEM_LIST_EMPTY,e.getMessage());
		}		
	}
	
	@Test
	public void testSetTransactionWithItemsLengthZero() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.ITEM_LIST_EMPTY,e.getMessage());
		}		
	}	
		
	@Test
	public void testSetTransactionWithNullProduct() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		Item item = new Item();
		items.add(item);
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.PRODUCT_ITEM_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testSetTransactionWithNullProductID() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		Item item = new Item();
		Product product = new Product();
		item.setProduct(product);
		items.add(item);
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.PRODUCT_ID_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testSetTransactionWithNullProductPrice() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		Item item = new Item();
		Product product = new Product();
		product.setId("PHN/1");
		item.setProduct(product);
		items.add(item);
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.ITEM_PRICE_NULL,e.getMessage());
		}		
	}
	
	@Test
	public void testSetTransactionWithNullProductQuantity() throws AccessDeniedException, CreationFailedException, IOException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		Item item = new Item();
		Product product = new Product();
		product.setId("PHN/1");
		item.setProduct(product);
		item.setPrice(24.05f);
		items.add(item);
		items.add(item);
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);
			Assert.fail("Should throw InvalidTransactionException");
		} catch (InvalidTransactionException e) {
			assertEquals(TransactionConstant.ITEM_QUANTITY_NULL,e.getMessage());
		}		
	}
	
	
	
	@Test
	public void testSetTransactionWithValidTransaction() throws AccessDeniedException, CreationFailedException, IOException, DataLoadFailedException, ParseException, RemoveFailedException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date3));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		Item item = new Item();
		Product product = new Product();
		product.setId("PHN/1");
		item.setProduct(product);
		item.setPrice(24.05f);
		item.setQuantity(24);
		items.add(item);
		items.add(item);
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);			
		} catch (InvalidTransactionException e) {
			Assert.fail("Should not throw InvalidTransactionException");
		}
		
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();	
		assertNotNull(transactionMap);
		assertTrue("Transaction Not present in the Map", transactionMap.containsKey(date3));
		
		ArrayList<Transaction> transactions = transactionMap.get(date3);
		assertNotNull(transactions);
		assertEquals(1,transactions.size());
		assertTrue(transactions.contains(transaction));
		
		ds.remove(transaction);
		
	}
	
	@Test
	public void testSetTransactionWithPreExistingDate() throws AccessDeniedException, CreationFailedException, IOException, DataLoadFailedException, ParseException, RemoveFailedException {
		Transaction transaction = new Transaction();
		transaction.setId(55);
		transaction.setDate(dateFormat.format(date2));
		Customer customer = new Customer();
		transaction.setCustomer(customer);
		ArrayList<Item> items = new ArrayList<>();
		Item item = new Item();
		Product product = new Product();
		product.setId("PHN/1");
		item.setProduct(product);
		item.setPrice(24.05f);
		item.setQuantity(24);
		items.add(item);
		items.add(item);
		transaction.setItems(items);
		assertNotNull(transaction);
		try {
			store.setTransaction(transaction);			
		} catch (InvalidTransactionException e) {
			Assert.fail("Should not throw InvalidTransactionException");
		}
		
		Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();	
		assertNotNull(transactionMap);
		assertTrue("Transaction Not present in the Map", transactionMap.containsKey(date2));
		
		ArrayList<Transaction> transactions = transactionMap.get(date2);
		assertNotNull(transactions);
		assertEquals(3,transactions.size());
		assertTrue(transactions.contains(transaction));		
		ds.remove(transaction);		
	}
		
	@Test
	public void testGetTransactionById() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Transaction transaction = store.getTransactionById(22);
		assertNotNull(transaction);
		assertEquals(transaction2,transaction);
	}
	
	@Test
	public void testGetTransactionByInvalidId() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		Transaction transaction = store.getTransactionById(222);
		assertNull(transaction);
	}
}
