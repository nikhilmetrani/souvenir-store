package sg.edu.nus.iss.se23pt2.pos;
import java.util.ArrayList;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : PurchaseOrder.java
//  @ Date : 3/8/2015
//  @ Author : Nikhil Metrani
//
//



/** */
public class PurchaseOrder {
    /** */
    private Integer id;
    
    /** */
    private ArrayList<Item> items;
    
    /** */
    private Vendor vendor;
    
    /** */
    private String date;
	
	public PurchaseOrder() {
		this.id = 0;
		this.items = new ArrayList<Item>();
		this.vendor = null;
		//this.date = 
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Item addProduct(Product p, Integer quantity) {
		Item item = new Item();
		//TO-DO
		return item;
	}
	
	public Item removeProduct(Product p, Integer quantity) {
		Item item = new Item();
		//TO-DO
		return item;
	}
	
	public ArrayList<Item> getItems() {
		//To-DO
		return this.items;
	}
	
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	public Vendor getVendor() {
		return this.vendor;
	}
	
	public String getDate() {
		return this.date;
	}
}
