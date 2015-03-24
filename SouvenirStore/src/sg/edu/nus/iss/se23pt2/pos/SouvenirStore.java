//
//
// Generated by StarUML(tm) Java Add-In
//
// @Project : SouvenirStore
// @File Name : SouvenirStore.java
// @Date : 06/03/2015
// @Author : Jaya Vignesh
// @Author: Niu Yiming (addMember, addDiscount, updateDiscount) 
// @Author: Rushabh Shah(Transaction API)

package sg.edu.nus.iss.se23pt2.pos;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import sg.edu.nus.iss.se23pt2.pos.constant.TransactionConstant;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidTransactionException;


public class SouvenirStore{
    private Map<String, StoreKeeper> storeKeepers;
    private Map<String, Category>    categories;
    private Map<String, Product>     products;
    private Map<String, Vendor>      vendors;
    private Map<String, Discount>    discounts;
    private ArrayList<Member>        members;
    
    private Map<Date,ArrayList<Transaction>>        transactions;
    private String                   loginUserName;
    private SimpleDateFormat 		 dateFormat;
    private Inventory                inventory = null;
    private DataStoreFactory 		 dsFactory = DataStoreFactory.getInstance();
    
    public SouvenirStore(){
        this.storeKeepers = new HashMap<String, StoreKeeper>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.categories = new HashMap<String, Category>();
        this.products = new HashMap<String, Product>();
        this.discounts = new HashMap<String, Discount>();
        this.loadData();
        this.inventory = new Inventory(this.products, this.categories, this.vendors, this.discounts);
    }
    
    public Inventory getInventory() {
    	return this.inventory;
    }
    
    public void addStoreKeeper (String userName, String password) {
    }

    public void addCategory (String categoryCode, String categoryName) {
    }
    
    public void addVendor (String vendorName, String description) {
    }

    public void addProduct (String productName, String description,
            Integer availableQty, Float price, Integer reorderThresholdQty,
            Integer orderQty) {
    }
    
    public StoreKeeper getStoreKeeper(String name) {
    	if (storeKeepers.containsKey(name))
    		return storeKeepers.get(name);
    	else
    		return null;
    }
    
    // Add a member to the list of members
    public void addMember (String memName, String memId) {
    	Member mem  = new Member(memName, memId);
    	members.add(mem);
    }
    
    public void validateLogin (String userName, String password) {
    }

    public String getLoginUserName () {
        return null;
    }

    public void loadData(){
        try {
            loadSequence();
            loadStoreKeepers();
            loadCategories(); //Must be called before loadProducts() to amke list of categories available
            loadProducts();
            loadDiscounts();
            loadTransactions();
        }
        catch (DataLoadFailedException dlex) {
        	dlex.printStackTrace();
        	System.exit(0); // Exit Application if data load failed
        }
    }

    private void loadSequence() throws DataLoadFailedException{
        try{
            ArrayList<Sequence> list = dsFactory.getSequenceDS().load(this);
            Iterator<Sequence> iterator = list.iterator();
            while(iterator.hasNext()){
                SequenceGenerator.getInstance().addSequence(iterator.next());
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new DataLoadFailedException(e.getMessage());
        }
    }

    private void loadStoreKeepers() throws DataLoadFailedException{
    	try{
            ArrayList<StoreKeeper> list = dsFactory.getStoreKeeperDS().load(this);
            Iterator<StoreKeeper> iterator = list.iterator();
            StoreKeeper storeKeeper = null;
            while(iterator.hasNext()){
                storeKeeper = iterator.next();
                this.storeKeepers.put( storeKeeper.getName().toLowerCase(), storeKeeper);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new DataLoadFailedException(e.getMessage());
        }
    }
    
    private void loadProducts() throws DataLoadFailedException{
   	 try{
            ArrayList<Product> list = dsFactory.getProductDS().load(this);
            Iterator<Product> iterator = list.iterator();
            Product product = null;
            while(iterator.hasNext()){
            	product = iterator.next();
            	// DS does not assign category to product, so lets try to assign valid category
            	if (null != product.getCategoryCode())
            		product.setCategory(this.categories.get(product.getCategoryCode()));
                this.products.put( product.getId(), product);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new DataLoadFailedException(e.getMessage());
        }
    }
    
    private void loadCategories() throws DataLoadFailedException{
    	 try{
             ArrayList<Category> list = dsFactory.getCategoryDS().load(this);
             Iterator<Category> iterator = list.iterator();
             Category category = null;
             while(iterator.hasNext()){
            	 category = iterator.next();
                 this.categories.put( category.getCode(), category);
             }
         }catch(Exception e){
             e.printStackTrace();
             throw new DataLoadFailedException(e.getMessage());
         }
    }
    
    private void loadDiscounts() throws DataLoadFailedException{
   	 try{
            ArrayList<Discount> list = dsFactory.getDiscountDS().load(this);
            Iterator<Discount> iterator = list.iterator();
            Discount disc = null;
            while(iterator.hasNext()){
            	disc = iterator.next();
            	this.discounts.put(disc.getDiscCode(), disc);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new DataLoadFailedException(e.getMessage());
        }
    }
    
	private void loadTransactions()
			throws DataLoadFailedException {
	    try{
    		transactions = new HashMap<Date, ArrayList<Transaction>>();
    		ArrayList<Transaction> transactionList = dsFactory.getTransactionDS().load(this);
    		ArrayList<Transaction> tempTransactionList;
    		Date date;
    		for(Transaction transaction:transactionList){
    			date = dateFormat.parse(transaction.getDate());
    			if(transactions!=null && transactions.containsKey(date)){
    				tempTransactionList = transactions.get(date);            		
    			}else{
    				tempTransactionList = new ArrayList<Transaction>();
    			}
    			tempTransactionList.add(transaction);
    			if(transactions!=null){
    			  transactions.put(date,tempTransactionList);
    			}
    		}
	    }catch(Exception e){
	        e.printStackTrace();
	        throw new DataLoadFailedException(e.getMessage());
	    }
	}

	public Map<Date, ArrayList<Transaction>> getTransactions() throws AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		loadTransactions();
		return transactions;
	}

	public void setTransactions(Map<Date, ArrayList<Transaction>> transactions) {
		this.transactions = transactions;
	}
	
	public Transaction getTransactionById(int id) throws AccessDeniedException, DataLoadFailedException, IOException{
		ArrayList<Transaction> transactionList = dsFactory.getTransactionDS().load(this);
		for(Transaction transaction:transactionList){
			if(id==transaction.getId()){
				return transaction;
			}
		}
		return null;
	}
	
	public void setTransaction(Transaction transaction) throws InvalidTransactionException, AccessDeniedException, CreationFailedException, IOException{
		validateTransaction(transaction);
		dsFactory.getTransactionDS().create(transaction);		
	}
	
	public ArrayList<Transaction> getTransactions(Date startDate,Date endDate) throws InvalidTransactionException, AccessDeniedException, DataLoadFailedException, IOException, ParseException {
		
		loadTransactions();		
		validateTransactionDate(startDate, endDate);		
		ArrayList<Transaction> filterTransactions = new ArrayList<Transaction>();
		Set<Date> dateSet = transactions.keySet();
		if(startDate==null && endDate==null){
			 return dsFactory.getTransactionDS().load(this);
		}else if(endDate==null){
			for(Date date : dateSet){
				if(date.compareTo(startDate)>=0)
					filterTransactions.addAll(transactions.get(date));
			}			
		}else if(startDate==null){
			for(Date date : dateSet){
				if(date.compareTo(endDate)<=0)
					filterTransactions.addAll(transactions.get(date));				
			}			
		}else{
			for(Date date : dateSet){
				if((date.compareTo(startDate)>=0)  &&  (date.compareTo(endDate)<=0))
					filterTransactions.addAll(transactions.get(date));				
			}		
		}	
		return filterTransactions;
	}

	private void validateTransactionDate(Date startDate, Date endDate)
			throws InvalidTransactionException {
		if(startDate!=null && endDate!=null && endDate.compareTo(startDate) < 0){
			throw new InvalidTransactionException(TransactionConstant.INVALID_DATE_ORDER);
		}
	}
	
	private void validateTransaction(Transaction transaction)
			throws InvalidTransactionException {
		
		if(transaction==null){
			throw new InvalidTransactionException(TransactionConstant.TRANSACTION_NULL);
		}
		if(transaction.getId()==0){
			throw new InvalidTransactionException(TransactionConstant.TRANSACTION_ID_NULL);
		}
		if(transaction.getDate()==null){
			throw new InvalidTransactionException(TransactionConstant.TRANSACTION_DATE_NULL);
		}
		if(transaction.getCustomer()==null){
			throw new InvalidTransactionException(TransactionConstant.TRANSACTION_CUST_NULL);
		}
		if(transaction.getCustomer().getId()==null){
			throw new InvalidTransactionException(TransactionConstant.TRANSACTION_CUST_ID_NULL);
		}
		if(transaction.getItems()==null || transaction.getItems().size()==0){
			throw new InvalidTransactionException(TransactionConstant.ITEM_LIST_EMPTY);
		}
		ArrayList<Item> items = transaction.getItems();
		for(Item item : items){
			if(item.getProduct()==null){
				throw new InvalidTransactionException(TransactionConstant.PRODUCT_ITEM_NULL);
			}
			if(item.getProduct().getId()==null){
				throw new InvalidTransactionException(TransactionConstant.PRODUCT_ID_NULL);
			}
			if(item.getPrice()==null || item.getPrice()==0.0f ){
				throw new InvalidTransactionException(TransactionConstant.ITEM_PRICE_NULL);
			}			
			if(item.getQuantity()==null || item.getQuantity()==0){
				throw new InvalidTransactionException(TransactionConstant.ITEM_QUANTITY_NULL);
			}
		}
	}    
    
}
