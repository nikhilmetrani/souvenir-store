package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.GridLayout;
import javax.swing.*;
import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;

public class EditStoreKeeperDialog extends OkCancelDialog {

	private static final long serialVersionUID = 1L;	
	
	private StoreKeeper storeKeeper; 
    private JTextField storeKeeperCurrentPasswordField;
    private JTextField storeKeeperNewPasswordField;
    private JTextField storeKeeperConfirmPasswordField;
    private JTextField storeKeeperNameField;    
	
	public EditStoreKeeperDialog(StoreKeeper storeKeeper, JFrame parent) {
    	super(parent, "Edit StoreKeeper");    	        
        this.storeKeeper = storeKeeper;
        storeKeeperCurrentPasswordField.setText("");
        storeKeeperNewPasswordField.setText("");
        storeKeeperConfirmPasswordField.setText("");
		storeKeeperNameField.setText(this.storeKeeper.getName());
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
        p.add (new JLabel ("StoreKeeper current password:"));
        storeKeeperCurrentPasswordField = new JTextField (20);
        p.add (storeKeeperCurrentPasswordField);
        p.add (new JLabel ("StoreKeeper new password:"));
        storeKeeperNewPasswordField = new JTextField (20);
        p.add (storeKeeperNewPasswordField);
        p.add (new JLabel ("StoreKeeper confirm password:"));
        storeKeeperConfirmPasswordField = new JTextField (20);
        p.add (storeKeeperConfirmPasswordField);
        return p;
	}

	@Override
	protected boolean performOkAction() {
			String currentPassword = this.storeKeeperCurrentPasswordField.getText();
    		String newPassword = this.storeKeeperNewPasswordField.getText();    		
    		String confirmPassword = this.storeKeeperConfirmPasswordField.getText();
	        String name = this.storeKeeperNameField.getText();
	        if ((0 == newPassword.length()) || (0 == name.length())) {
	            return false;
	        }
	        
	        if (this.storeKeeper.isPasswordValid(currentPassword, newPassword, confirmPassword)){	        	
	        	this.storeKeeper.setName(name);
		        this.storeKeeper.setPassword("", newPassword, "");
		        return true;
	        }           	
    	
        return false;
	}
	
	public StoreKeeper getStoreKeeper() {
    	return this.storeKeeper;
    }
}