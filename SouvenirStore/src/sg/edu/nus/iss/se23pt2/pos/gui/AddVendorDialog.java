package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class AddVendorDialog extends OkCancelDialog {

	private static final long serialVersionUID = 1L;
	
	private String categoryCode; 
	private Vendor vendor;
	private JTextField catCodeField;
    private JTextField vendorNameField;
    private JTextField vendorDescField;

    public AddVendorDialog (String categoryCode,Inventory inventory, JFrame parent) {
        super(parent, "Add Vendor");
        this.vendor = null;
        this.categoryCode =categoryCode;
        catCodeField.setText(this.categoryCode);
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
    }
    
    protected JPanel createFormPanel () {
        JPanel p = new JPanel ();
        p.setLayout (new GridLayout (0, 2));
        //category
        p.add (new JLabel ("Category:"));
        catCodeField=new JTextField (20);          
        catCodeField.setEditable(false);;
        p.add (catCodeField);
        
        //vendor name      
        p.add (new JLabel ("Vendor name:"));
        vendorNameField = new JTextField (20);        
        p.add (vendorNameField);
        //vendor description
        p.add (new JLabel ("Vendor description:"));
        vendorDescField = new JTextField (20);
        p.add (vendorDescField);
        return p;
    }

    protected boolean performOkAction () {
    	String name = this.vendorNameField.getText();
        String desc = this.vendorDescField.getText();
        if ((0 == name.length()) || (0 == desc.length())) {
        	JOptionPane.showMessageDialog(null,
                    "Error :: Name and description cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        	this.vendor = null;
            return false;
        }else {
	        this.vendor = new Vendor(name, desc);
	        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
	        try {
	        	dsFactory.getVendorDS(this.categoryCode).update(this.vendor);
	        	return true;
	        }
	        catch (UpdateFailedException ufe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ufe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.vendor = null;
	        	return false;
	        }
	        catch (IOException ioe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ioe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.vendor = null;
	        	return false;
	        }
    	}
    }
    
    public Vendor getAdded() {
    	return this.vendor;
    }
}