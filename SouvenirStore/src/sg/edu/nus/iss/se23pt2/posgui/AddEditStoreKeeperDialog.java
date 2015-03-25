package sg.edu.nus.iss.se23pt2.posgui;

import javax.swing.*;
import sg.edu.nus.iss.se23pt2.pos.*;
import java.awt.GridLayout;

public class AddEditStoreKeeperDialog extends OkCancelDialog {

	private static final long serialVersionUID = 1L;
	
	private SouvenirStore souvenirStore;
	private StoreKeeper storeKeeper; 
    private JTextField storeKeeperPasswordField;
    private JTextField storeKeeperNameField;
    private boolean editMode = false;   
    
	public AddEditStoreKeeperDialog(SouvenirStore souvenirStore, JFrame parent) {
		super(parent, "Add StoreKeeper");
        this.souvenirStore = souvenirStore;
        this.storeKeeper = null;
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
	}	
	
	public AddEditStoreKeeperDialog(StoreKeeper storeKeeper, JFrame parent) {
    	super(parent, "Edit StoreKeeper");
    	this.editMode = true;
        this.souvenirStore = null;
        this.storeKeeper = storeKeeper;
        storeKeeperPasswordField.setText("");
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
        p.add (new JLabel ("StoreKeeper password:"));
        storeKeeperPasswordField = new JTextField (20);
        p.add (storeKeeperPasswordField);
        return p;
	}

	@Override
	protected boolean performOkAction() {
		if (this.editMode) {
    		String password = this.storeKeeperPasswordField.getText().toUpperCase();
	        String name = this.storeKeeperNameField.getText();
	        if ((0 == password.length()) || (0 == name.length())) {
	            return false;
	        }
	        this.storeKeeper.setName(name);
	        this.storeKeeper.setPassword("", password, "");	        
    	}
    	else {
	        String password = this.storeKeeperPasswordField.getText().toUpperCase();
	        String name = this.storeKeeperNameField.getText();
	        if ((0 == password.length()) || (0 == name.length())) {
	            return false;
	        }
	        this.storeKeeper = new StoreKeeper(storeKeeperNameField.getText(), storeKeeperPasswordField.getText());
	        this.souvenirStore.addStoreKeeper(this.storeKeeper);
    	}
        return true;
	}
	
	public StoreKeeper getStoreKeeper() {
    	return this.storeKeeper;
    }
}
