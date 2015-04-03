/**
 *
 */
package sg.edu.nus.iss.se23pt2.pos;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sg.edu.nus.iss.se23pt2.pos.gui.LoginDialog;

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
    private Map<String, Object> attributes = null;

    private Session(SouvenirStore store) {
        // TODO Auto-generated constructor stub
        this.store = store;
        this.attributes = new HashMap();
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

    /**
     * Returns access to store if a valid user is logged in
     * @return 
     */
    public SouvenirStore getStore() {
        if (null != this.loggedInUser) {
            return this.store;
        } else {
            return null;
        }
    }

    public boolean authenticate() {
        LoginDialog loginDlg;
        String userName = null;
        while (true) {
            loginDlg = new LoginDialog(userName);
            loginDlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            loginDlg.setLocationRelativeTo(null);
            loginDlg.setVisible(true);
            if (loginDlg.okPerformed()) {
                userName = loginDlg.getUsername();
                if (this.authenticate(loginDlg.getUsername(), loginDlg.getPassword())) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error :: Invalid username or password!",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                loginDlg.dispose();
                loginDlg = null;
                return false;
            }
        }

    }

    /**
     * Authenticates user login information
     * @param userName
     * @param password
     * @return 
     */
    public boolean authenticate(String userName, String password) {

        if ((null == userName) || (null == password)) {
            return false; //cannot login with null parameters
        }
        StoreKeeper sk = store.getStoreKeeper(userName.toLowerCase());

        if (null == sk) {
            return false;
        } else {
            if (sk.validatePassword(password)) {
                this.loggedInUser = sk;
                this.isActive = true;
                return true; //login successful
            } else {
                return false; //login fail
            }
        }
    }

    public boolean logOff() {
        if (null != this.loggedInUser) {
            Object[] options = {"OK", "CANCEL"};
            int n = JOptionPane.showOptionDialog(null, "Are you sure you want to log off?\n\nClick OK to continue.", "Confirm",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (0 == n) {
                this.loggedInUser = null;
                this.isActive = false;
                return true; //logoff successful
            }
        }
        return false; //logoff fail
    }

    /**
     * JV - To add/remove/get Attributes *
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object removeAttribute(String key) {
        return this.attributes.remove(key);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }
}
