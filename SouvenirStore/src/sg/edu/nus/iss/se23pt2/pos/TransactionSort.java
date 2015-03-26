package sg.edu.nus.iss.se23pt2.pos;

import java.util.Comparator;

/**
 * 
 * @author Rushabh Shah
 *
 */

public class TransactionSort implements Comparator<Transaction> {

	@Override
	public int compare(Transaction transaction1, Transaction transaction2) {
		if (transaction1.getId() > transaction2.getId()) {
			return 1;
		} else if (transaction1.getId() < transaction2.getId()) {
			return -1;
		} else {
			return 0;
		}
	}

}
