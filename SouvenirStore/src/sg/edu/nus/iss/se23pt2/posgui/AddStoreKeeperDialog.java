package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.GridLayout;
import javax.swing.*;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;

public class AddStoreKeeperDialog extends OkCancelDialog {

	private static final long serialVersionUID = 1L;
	
	private SouvenirStore souvenirStore;
	private StoreKeeper storeKeeper; 
    private JTextField storeKeeperPasswordField;
    private JTextField storeKeeperConfirmPasswordField;
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
        storeKeeperPasswordField = new JTextField (20);
        p.add (storeKeeperPasswordField);
        p.add (new JLabel ("StoreKeeper confirm password:"));
        storeKeeperConfirmPasswordField = new JTextField (20);
        p.add (storeKeeperConfirmPasswordField);
        return p;
	}

	@Override
	protected boolean performOkAction() {		
	        String password = this.storeKeeperPasswordField.getText().toUpperCase();
	        String confirmPassword = this.storeKeeperConfirmPasswordField.getText().toUpperCase();
	        String name = this.storeKeeperNameField.getText();
	        if ((0 == password.length()) || (0 == name.length())) {
	            return false;
	        }
	        if(!password.equals(confirmPassword)){
	        	return false;
	        }
	        this.storeKeeper = new StoreKeeper(storeKeeperNameField.getText(), storeKeeperPasswordField.getText());
	        this.souvenirStore.addStoreKeeper(this.storeKeeper);    	
        return true;
	}
	
	public StoreKeeper getStoreKeeper() {
    	return this.storeKeeper;
    }
}