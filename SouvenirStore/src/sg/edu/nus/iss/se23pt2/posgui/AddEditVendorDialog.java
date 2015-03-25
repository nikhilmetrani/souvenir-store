package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sg.edu.nus.iss.se23pt2.pos.*;

public class AddEditVendorDialog extends OkCancelDialog {

	private static final long serialVersionUID = 1L;
	
	private Inventory inventory;
	private String categoryCode; 
	private Vendor vendor;
	private JTextField catCodeField;
    private JTextField vendorNameField;
    private JTextField vendorDescField;
    private boolean editMode = false;

    public AddEditVendorDialog (String categoryCode,Inventory inventory, JFrame parent) {
        super(parent, "Add Vendor");
        this.categoryCode =categoryCode;
        this.inventory = inventory;     
        catCodeField.setText(this.categoryCode);
    }
    
    public AddEditVendorDialog (String categoryCode,Vendor vendor, JFrame parent) {
    	super(parent, "Edit Vendor");    	
    	this.categoryCode =categoryCode;
    	this.vendor=vendor;
    	this.editMode = true;
        this.inventory = null;    
        catCodeField.setText(this.categoryCode);
        vendorNameField.setText(this.vendor.getName());
        vendorDescField.setText(this.vendor.getDescription());
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
            return false;
        }
    	if (this.editMode) {    		
	        this.vendor.setName(name);
	        this.vendor.setDescription(desc);
    	}
    	else {
	      
	        Vendor vendor = new Vendor(name, desc);
	        this.inventory.addVendor(categoryCode,vendor);
    	}
        return true;
    }
}