package sg.edu.nus.iss.se23pt2.pos;
import java.util.ArrayList;

/**
 * 
 * @author Rushabh Shah
 * Date    12/3/2015
 * 
 */

public class Transaction {
    /** */
    private int id;
    
    /** */
    private ArrayList<Item> items;
    
    /** */
    private Customer customer;
    
    /** */
    private String date;
    
    /** */
    private Discount discount;
    
    /** */
    public Transaction() {
		this(0, null, null, null);
    }
	
    /** */
    public Transaction(int id, ArrayList<Item> items, Customer c, Discount d) {
    	this.id = id;
    	this.items = items;
    	this.customer = c;
    	this.discount = d;
    }
    
	/** */
    public void setId(int id) {
		this.id = id;
    }
	
    /** */
    public int getId() {
		return this.id;
    }
    
	/**
	 * Sorting the items according to ProductId
	 *  */
    public void setItems(ArrayList<Item> items) {
		this.items = items;
		//this.items.sort(new ItemSort());
    }
	
    /** */
    public ArrayList<Item> getItems() {
		return this.items;
    }

    /** */
    public Customer getCustomer() {
		return this.customer;
    }
    
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/** */
    public void setDate(String date) {
		this.date = date;
    }
	
    /** */
    public String getDate() {
		return this.date;
    }
    
	/** */
    public void setDiscount(Discount d) {
		this.discount = d;
    }
	
    /** */
    public Discount getDiscount() {
		return this.discount;
    }   
    
	
	public String toString() {		
		StringBuilder stb = new StringBuilder();
			for(Item item:items){
				stb = stb.append(this.id)
						 .append(",")
						 .append(item.getProduct().getId())
						 .append(",")
						 .append(this.getCustomer().getId())
						 .append(",")
						 .append(item.getQuantity())
						 .append(",")
						 .append(this.date)
						 .append(System.getProperty("line.separator"));			
			}
		
		return stb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
