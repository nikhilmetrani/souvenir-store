package sg.edu.nus.iss.se23pt2.pos;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : Customer.java
//  @ Date : 3/8/2015
//  @ Author : Nikhil Metrani
//

public class Customer {
    /** */
    private String id;
    
    public Customer() {
    	this.id = "PUBLIC";
    }
    
    /** */
    public void setId(String id) {
    	this.id = id;
    }
    
    /** */
    public String getId() {
    	return this.id;
    }
    
    @Override
    public String toString() {
    	return this.id;
    }
    
    public boolean isFirstPurchase(){
        return false;
    }
}
