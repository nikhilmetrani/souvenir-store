package sg.edu.nus.iss.se23pt2.pos;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : Item.java
//  @ Date : 3/8/2015
//  @ Author : Rushabh Shah
//
//



/** */
public class Item {
    /** */
    private Product product;
    
    /** */
    private Integer quantity;
    
    /** */
    private Float price;
    
    public Item() {
    	this(null, 0, Float.valueOf(0));
    }
    
    public Item(Product product, Integer quantity, Float price) {
    	this.product = product;
    	this.quantity = quantity;
    	this.price = price;
    }       
    
    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(Integer quantity) {
    	this.quantity = quantity;
    }
    
    public Integer getQuantity() {
    	return this.quantity;
    }
    
    public void setPrice(Float price) {
    	this.price = price;
    }
    
    public Float getPrice() {
    	return this.price;
    }

	@Override
	public String toString() {
		if(product!=null){
		return "Item [productId=" + product.getId() + ", quantity=" + quantity
				+ ", price=" + price + "]";
		}else{
			return "Item [quantity=" + quantity
					+ ", price=" + price + "]";
		}
	}
    
    
}
