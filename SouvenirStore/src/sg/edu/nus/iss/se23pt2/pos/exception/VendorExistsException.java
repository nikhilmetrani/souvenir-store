/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos.exception;

/**
 * @author Nikhil Metrani
 *
 */
public class VendorExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VendorExistsException(String msg){
        super(msg);
    }
}
