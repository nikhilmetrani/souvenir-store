package sg.edu.nus.iss.se23pt2.pos;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

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

/**
 *  Initial Setup
 *  Transaction1 done on 2013-09-28 date 
 *  Transaction2 and Transasction3 done on 2012-02-12 date 
 *  
 *  Transaction1 and Transaction3 contains Two product items
 *  Transaction2 contains Three product items
 * 
 *
 */

public class TransactionTest extends TestCase{
    private DataStoreFactory dsFactory;
    private DataStore ds1, ds2, ds3;
    private SouvenirStore store;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Customer customer;
    private Item item1,item2,item3,item4;
    private Product product1,product2,product3,product4;
    private SimpleDateFormat dateFormat;
    private Date date1,date2,date3;
    private ArrayList<Item> items1,items2,items;

    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dateFormat.parse("2013-09-28");
        date2 = dateFormat.parse("2012-02-12");
        date3 = dateFormat.parse("2015-03-22");

        dsFactory = DataStoreFactory.getInstance();
        ds1 = dsFactory.getTransactionDS(); 
        ds2 = dsFactory.getMemberDS();
        ds3 = dsFactory.getProductDS();

        /** Data set 01 **/
        customer = new Member();
        customer.setId("TEST_MEMBER1");
        ds2.create(customer);

        items1 = new ArrayList<Item>();

        product1 = new Product();
        product1.setId("CTY/2");
        product1.setQuantity(40);
        product1.setPrice(4.1f);
        ds3.create(product1);

        item1 = new Item();
        item1.setProduct(product1);
        item1.setQuantity(5);
        item1.setPrice(20.5f);
        items1.add(item1);

        product2= new Product();
        product2.setId("CTY/5");
        product2.setQuantity(100);
        product2.setPrice(0.40f);
        ds3.create(product2);

        item2 = new Item();
        item2.setProduct(product2);
        item2.setQuantity(25);
        item2.setPrice(10.15f);
        items1.add(item2);

        transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setCustomer(customer);
        transaction1.setItems(items1);
        transaction1.setDate("2013-09-28");

        /** Data set 02 **/
        customer = new Member();
        customer.setId("TEST_MEMBER2");
        ds2.create(customer);

        items2 = new ArrayList<Item>();
        items2.addAll(transaction1.getItems()); //Make use of previously created items 

        product3= new Product();
        product3.setId("TIE/1");
        product3.setQuantity(150);
        product3.setPrice(0.60f);
        ds3.create(product3);

        item3 = new Item();
        item3.setProduct(product3);
        item3.setQuantity(52);
        item3.setPrice(31.5f);
        items2.add(item3);

        product4 = new Product();
        product4.setId("TIE/2");
        product4.setQuantity(50);
        product4.setPrice(7.07f);
        ds3.create(product4);

        item4 = new Item();
        item4.setProduct(product4);
        item4.setQuantity(2);
        item4.setPrice(14.15f);
        items2.add(item4);

        transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setCustomer(customer); 
        transaction2.setItems(items2);
        transaction2.setDate("2012-02-12");

        /** Data set 03 **/
        customer = new Member();
        customer.setId("TEST_MEMBER3");
        ds2.create(customer);

        transaction3 = new Transaction();
        transaction3.setId(3);
        transaction3.setCustomer(customer);
        transaction3.setItems(items1); //Make use of previously created items
        transaction3.setDate("2012-02-12");

        ds1.create(transaction1);
        ds1.create(transaction2);
        ds1.create(transaction3);

        store = new SouvenirStore();
    }

    @After
    public void tearDown() throws Exception {
        ds1.remove(transaction1);
        ds1.remove(transaction2);
        ds1.remove(transaction3);

        ds2.remove(transaction1.getCustomer());
        ds2.remove(transaction2.getCustomer());
        ds2.remove(transaction3.getCustomer());

        items = new ArrayList<Item>();
        items.addAll(transaction1.getItems());
        items.addAll(transaction2.getItems());
        items.addAll(transaction3.getItems());

        for(Item item : items){
            ds3.remove(item.getProduct());
        }

        transaction1 = null;
        transaction2 = null;
        transaction3 = null;
    }

    @Test
    public void testRemoveAndLoadTransactions() throws RemoveFailedException, AccessDeniedException, DataLoadFailedException, IOException, ParseException {
        ds1.remove(transaction1);
        ds1.remove(transaction2);
        ds1.remove(transaction3);
        Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();		
        assertTrue(!transactionMap.values().contains(transaction1));
        assertTrue(!transactionMap.values().contains(transaction2));
        assertTrue(!transactionMap.values().contains(transaction3)); 
    }

    @Test
    public void testTransactionMapSize() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
        Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
        assertNotNull(transactionMap);
        assertTrue(transactionMap.size()>0); 		
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
    }

    @Test
    public void testTransactionsForParticularDate() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
        Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();
        assertNotNull(transactionMap);
        Set<Date> dateSet = transactionMap.keySet();
        assertTrue(dateSet.contains(date1));	
        System.out.println(transactionMap);
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
        assertTrue(items.contains(item1));
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
        assertEquals("CTY/2",items.get(0).getProduct().getId());
        assertEquals("CTY/5",items.get(1).getProduct().getId());       
        assertEquals("TIE/1",items.get(2).getProduct().getId());
        assertEquals("TIE/2",items.get(3).getProduct().getId());

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
            assertEquals(2,transactionList.size());
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
            assertTrue(transactionList.size()>0);
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
        assertTrue(items.contains(item1));
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
        assertTrue(items.contains(item1));
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
        assertTrue(items.contains(item1));
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
        assertTrue(items.contains(item1));
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
    public void testSetNullTransaction() throws Exception {
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
    public void testSetNullTransactionId() throws Exception {
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
    public void testSetNullTransactionDate() throws Exception {
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
    public void testSetNullTransactionCustomer() throws Exception {
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
    public void testSetTransactionWithEmptyItems() throws Exception {
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
    public void testSetTransactionWithItemsLengthZero() throws Exception {
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
    public void testSetTransactionWithNullProduct() throws Exception {
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
    public void testSetTransactionWithNullProductID() throws Exception {
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
    public void testSetTransactionWithNullProductPrice() throws Exception {
        transaction1.setId(5); //Make resuse of existing transaction object
        transaction1.setDate(dateFormat.format(date3));
        transaction1.getItems().get(0).setPrice(0.0f);
        try {
            store.setTransaction(transaction1);
            Assert.fail("Should throw InvalidTransactionException");
        } catch (InvalidTransactionException e) {
            assertEquals(TransactionConstant.ITEM_PRICE_NULL,e.getMessage());
        } 
        transaction1.setId(1); //Reset the transaction Id to allow the tearDown() to clear the data
    }

    @Test
    public void testSetTransactionWithNullProductQuantity() throws Exception {
        transaction1.setId(5); //Make resuse of existing transaction object
        transaction1.setDate(dateFormat.format(date3));
        transaction1.getItems().get(0).setQuantity(0);
        try {
            store.setTransaction(transaction1);
            Assert.fail("Should throw InvalidTransactionException");
        } catch (InvalidTransactionException e) {
            assertEquals(TransactionConstant.ITEM_QUANTITY_NULL,e.getMessage());
        }
        transaction1.setId(1); //Reset the transaction Id to allow the tearDown() to clear the data
    }



    @Test
    public void testSetTransactionWithValidTransaction() throws Exception {
        transaction1.setId(5); //Make resuse of existing transaction object
        transaction1.setDate(dateFormat.format(date3));
        try {
            store.setTransaction(transaction1);			
        } catch (InvalidTransactionException e) {
            Assert.fail("Should not throw InvalidTransactionException");
        }

        Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();	
        assertNotNull(transactionMap);
        assertTrue("Transaction Not present in the Map", transactionMap.containsKey(date3));

        ArrayList<Transaction> transactions = transactionMap.get(date3);
        assertNotNull(transactions);
        assertEquals(1,transactions.size());
        assertTrue(transactions.contains(transaction1));

        ds1.remove(transaction1);
        transaction1.setId(1); //Reset the transaction Id to allow the tearDown() to clear the data
    }

    @Test
    public void testSetTransactionWithPreExistingDate() throws Exception {
        transaction2.setId(5); //Reuse the existing transaction object
        try {
            store.setTransaction(transaction2);			
        } catch (InvalidTransactionException e) {
            Assert.fail("Should not throw InvalidTransactionException");
        }

        Map<Date, ArrayList<Transaction>> transactionMap = store.getTransactions();	
        assertNotNull(transactionMap);
        assertTrue("Transaction Not present in the Map", transactionMap.containsKey(date2));

        ArrayList<Transaction> transactions = transactionMap.get(date2);
        assertNotNull(transactions);
        assertEquals(3,transactions.size());
        assertTrue(transactions.contains(transaction2));		
        ds1.remove(transaction2); 
        transaction2.setId(2); //Reset the Id to allow the tearDown() to clear the data
    }

    @Test
    public void testGetTransactionById() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
        Transaction transaction = store.getTransactionById(2);
        assertNotNull(transaction);
        assertEquals(transaction2,transaction);
    }

    @Test
    public void testGetTransactionByInvalidId() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
        Transaction transaction = store.getTransactionById(222);
        assertNull(transaction);
    }
}
