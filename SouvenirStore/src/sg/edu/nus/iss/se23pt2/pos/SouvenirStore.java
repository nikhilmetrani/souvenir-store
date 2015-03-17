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

import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;


public class SouvenirStore{
    private Map<String, StoreKeeper> storeKeepers;
    private Map<String, Category>    categories;
    private Map<String, Vendor>      vendors;
    private ArrayList<Member>        members;
    private ArrayList<Discount>      discounts;
    private Map<Date,ArrayList<Transaction>>        transactions;
    private String                   loginUserName;
    private SimpleDateFormat 		 dateFormat;

    public SouvenirStore(){
        storeKeepers = new HashMap<String, StoreKeeper>();
        transactions = new HashMap<Date, ArrayList<Transaction>>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.loadData();
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
    
    // Add a member to the list of members
    public void addMember (String memName, String memId) {
    	Member mem  = new Member(memName, memId);
    	members.add(mem);
    }

    // Add a new discount to the list of discounts
    public void addDiscount (String discCode, String description, String startDate
    		, String periodInDays, double discPct, String applicableTo) {
    	Discount disc = new Discount(discCode, description, startDate, periodInDays, discPct, applicableTo);
    	discounts.add(disc);
    }
    
    // Update discount percentage for a certain code
    public void updateDiscount (String discCode, Integer discPct) {
    	for (int i = 1; i <= discounts.size(); i++) {
    		if (discounts.get(i).getDiscountCode().equals(discCode)) {
    			discounts.get(i).setDiscPct(discPct);
    			break;
    		}
    	}
    }
    
    public void validateLogin (String userName, String password) {
    }

    public String getLoginUserName () {
        return null;
    }

    public void loadData(){
        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
        try{
            ArrayList<StoreKeeper> list = dsFactory.getStoreKeeperDS().load(this);
            Iterator<StoreKeeper> iterator = list.iterator();
            StoreKeeper storeKeeper = null;
            while(iterator.hasNext()){
                storeKeeper = iterator.next();
                this.storeKeepers.put( storeKeeper.getName(), storeKeeper);
            }            
            loadTransactions(dsFactory);      
        }catch(Exception e){
            e.printStackTrace();
        }
    }

	private void loadTransactions(DataStoreFactory dsFactory)
			throws DataLoadFailedException, AccessDeniedException, IOException,
			ParseException {
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
	}

	public Map<Date, ArrayList<Transaction>> getTransactions() {
		return transactions;
	}

	public void setTransactions(Map<Date, ArrayList<Transaction>> transactions) {
		this.transactions = transactions;
	}

	public ArrayList<Transaction> getTransactionsBetweenDates(Date startDate,Date endDate) {
		if(startDate==null || endDate ==null){
			return null;
		}
		ArrayList<Transaction> filterTransactions = new ArrayList<Transaction>();
		Set<Date> dateSet = transactions.keySet();		
		for(Date date : dateSet){
			if((date.compareTo(startDate)>=0)  &&  (date.compareTo(endDate)<=0)){
				filterTransactions.addAll(transactions.get(date));
			}
		}		
		return filterTransactions;
	}
	
	

	
	
    
    
}
