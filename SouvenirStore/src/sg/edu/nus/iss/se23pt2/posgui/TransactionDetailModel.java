package sg.edu.nus.iss.se23pt2.posgui;

/**
 * 
 * @author Rushabh Shah 
 *
 */
public class TransactionDetailModel extends TransactionModel {
	
	private String productId;
	
	private int quantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Id= "
				+ getTransactionId() + ", MemberId= " + getMemberId()
				+ ", Date= " + getDate() + ", ProductId= " + productId
				+ ", Qty= " + quantity;
	}

	
	
	
	

}
