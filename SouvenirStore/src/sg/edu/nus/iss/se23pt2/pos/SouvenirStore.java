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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;


public class SouvenirStore {
    private Map<String, StoreKeeper> storeKeepers;
    private Map<String, Category>    categories;
    private Map<String, Vendor>      vendors;
    private ArrayList<Member>        members;
    private ArrayList<Discount>      discounts;
    private Map<String,ArrayList<Transaction>>        transactions;
    private String                   loginUserName;

    public SouvenirStore(){
        storeKeepers = new HashMap<String, StoreKeeper>();
        transactions = new HashMap<String, ArrayList<Transaction>>();
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
    
    public void addMember (String memberName) {
    	Member mem  = new Member(memberName);
    	members.add(mem);
    }

    // Add a new discount to the list of discounts.
    public void addDiscount (String discCode, String description, String startDate
    		, String periodInDays, double discPct, String applicableTo) {
    	Discount disc = new Discount(discCode, description, startDate, periodInDays, discPct, applicableTo);
    	discounts.add(disc);
    }
    
    // Update discount percentage for a certain code.
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
            
            ArrayList<Transaction> transactionList = dsFactory.getTransactionDS().load(this);
            ArrayList<Transaction> tempTransactionList;
            String date;
            for(Transaction transaction:transactionList){
            	date = transaction.getDate();
            	if(transactions!=null && transactions.containsKey(date)){
            		tempTransactionList = transactions.get(date);            		
            	}else{
            		tempTransactionList = new ArrayList<>();
            	}
            	tempTransactionList.add(transaction);
            	transactions.put(date, tempTransactionList);
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

	public Map<String, ArrayList<Transaction>> getTransactions() {
		return transactions;
	}

	public void setTransactions(Map<String, ArrayList<Transaction>> transactions) {
		this.transactions = transactions;
	}
	
	
    
    
}
