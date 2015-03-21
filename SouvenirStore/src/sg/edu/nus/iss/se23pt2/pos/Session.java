/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

/**
 * @author Nikhil Metrani
 *
 */

public class Session {

	/**
	 * Authenticates user login
	 */
	
	private static Session session = null;
	private SouvenirStore store = null;
	private StoreKeeper loggedInUser = null;
	private boolean isActive = false;
	
	private Session(SouvenirStore store) {
		// TODO Auto-generated constructor stub
		this.store = store;
	}
	
	public static Session getInstance(SouvenirStore store) {
		if (null == session) {
			session = new Session(store);
		}
		return session;
	}
	
	public StoreKeeper getLoggedInUser() {
		return this.loggedInUser;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	/** Returns access to store if a valid user is logged in */
	public SouvenirStore getStore() {
		if (null != this.loggedInUser)
			return this.store;
		else
			return null;
	}
	
	/** Authenticates user login information */
	public boolean authenticate(String userName, String password) {
		StoreKeeper sk = store.getStoreKeeper(userName.toLowerCase());
		
		if (null == sk)
			return false;
		else {
			if (sk.validatePassword(password)) {
				this.loggedInUser = sk;
				this.isActive = true;
				return true; //login successful
			}
			else {
				return false; //login fail
			}
		}
	}
	
	public boolean logOff() {
		if (null != this.loggedInUser) {
			this.loggedInUser = null;
			this.isActive = false;
			return true; //log off successful
		}
		return false; //log off fail
	}
}
