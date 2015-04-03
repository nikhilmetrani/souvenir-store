package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.*;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class AddStoreKeeperDialog extends OkCancelDialog {

	private static final long serialVersionUID = 1L;
	
	private SouvenirStore souvenirStore;
	private StoreKeeper storeKeeper; 
    private JPasswordField storeKeeperPasswordField;
    private JPasswordField storeKeeperConfirmPasswordField;
    private JTextField storeKeeperNameField;
    
    public AddStoreKeeperDialog(SouvenirStore souvenirStore, JFrame parent) {
		super(parent, "Add StoreKeeper");
        this.souvenirStore = souvenirStore;
        this.storeKeeper = null;
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
	}	
	
	@Override
	protected JPanel createFormPanel() {
		JPanel p = new JPanel ();
        p.setLayout (new GridLayout (0, 2));        
        p.add (new JLabel ("StoreKeeper name:"));
        storeKeeperNameField = new JTextField (20);
        p.add (storeKeeperNameField);
        p.add (new JLabel ("StoreKeeper password:"));
        storeKeeperPasswordField = new JPasswordField (20);
        p.add (storeKeeperPasswordField);
        p.add (new JLabel ("StoreKeeper confirm password:"));
        storeKeeperConfirmPasswordField = new JPasswordField (20);
        p.add (storeKeeperConfirmPasswordField);
        return p;
	}

	@Override
	protected boolean performOkAction() {		
		char[] passwordArray = this.storeKeeperPasswordField.getPassword();			
		char[] confirmPasswordArray = this.storeKeeperConfirmPasswordField.getPassword();
		String password = new String(passwordArray);
		String confirmPassword = new String(confirmPasswordArray);
	    String name = this.storeKeeperNameField.getText();
	        if ((0 == password.length()) || (0 == name.length()) || (0 == confirmPassword.length())) {
	        	JOptionPane.showMessageDialog(null,
                        "Fields are be empty!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	        if(!password.equals(confirmPassword)){
	        	JOptionPane.showMessageDialog(null,
                        "Password and confirm password are different!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);	
	        	return false;
	        }
	        this.storeKeeper = new StoreKeeper(name, password);
	        if(this.souvenirStore.isStoreKeeperExist(this.storeKeeper)){
	        	JOptionPane.showMessageDialog(null,
                        "Duplicate store keeper!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);	
	        	return false;
	        }
	        this.souvenirStore.addStoreKeeper(this.storeKeeper);
	        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
	        try {
	        	dsFactory.getStoreKeeperDS().update(this.storeKeeper);
	        	return true;
	        }
	        catch (UpdateFailedException ufe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ufe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.storeKeeper = null;
	        	return false;
	        }
	        catch (IOException ioe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ioe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.storeKeeper = null;
	        	return false;
	        }       
	}
	
	public StoreKeeper getStoreKeeper() {
    	return this.storeKeeper;
    }
}