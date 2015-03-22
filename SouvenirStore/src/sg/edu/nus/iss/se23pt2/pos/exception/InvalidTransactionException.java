package sg.edu.nus.iss.se23pt2.pos.exception;

public class InvalidTransactionException extends Exception {
	/**
	 * 
	 * @author Rushabh Shah
	 * Date    22/3/2015
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTransactionException(String msg) {
		super(msg);
	}

}
