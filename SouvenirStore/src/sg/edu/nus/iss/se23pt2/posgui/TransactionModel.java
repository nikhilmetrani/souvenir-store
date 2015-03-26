package sg.edu.nus.iss.se23pt2.posgui;
/**
 * 
 * @author Rushabh Shah
 *
 */
public class TransactionModel {
	
	private int transactionId;
	
	private String date;
	
	private String memberId;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "TransactionId = " + transactionId + ",     Date="
				+ date + ",     MemberId=" + memberId;
	}

}
