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
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidCategoryCodeException;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidVendorException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class AddVendorDialog extends OkCancelDialog {

    private static final long serialVersionUID = 1L;

    private final String categoryCode;
    private Vendor vendor;
    private JTextField catCodeField;
    private JTextField vendorNameField;
    private JTextField vendorDescField;
    private Inventory inventory;

    public AddVendorDialog(String categoryCode, Inventory inventory, JFrame parent) {
        super(parent, "Add Vendor");
        this.vendor = null;
        this.categoryCode = categoryCode;
        this.inventory = inventory;
        catCodeField.setText(this.categoryCode);
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
    }

    @Override
    protected JPanel createFormPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(0, 2));
        //category
        p.add(new JLabel("Category:"));
        this.catCodeField = new JTextField(20);
        this.catCodeField.setEditable(false);
        p.add(catCodeField);

        //vendor name      
        p.add(new JLabel("Vendor name:"));
        this.vendorNameField = new JTextField(20);
        p.add(vendorNameField);
        //vendor description
        p.add(new JLabel("Vendor description:"));
        this.vendorDescField = new JTextField(20);
        p.add(vendorDescField);
        return p;
    }

    @Override
    protected boolean performOkAction() {
        String name = this.vendorNameField.getText();
        String desc = this.vendorDescField.getText();
        if ((0 == name.length()) || (0 == desc.length())) {
            JOptionPane.showMessageDialog(null,
                    "Error :: Name and description cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            this.vendor = null;
            return false;
        } else {
            this.vendor = new Vendor(name, desc);
            Category cat;
            try {
                cat = this.inventory.getCategory(this.categoryCode);
                try { 
                    cat.getVendor(this.vendor.getName());
                    JOptionPane.showMessageDialog(null,
                                "Error :: The vendor " + this.vendor.getName() + " already exists",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    this.vendor = null;
                    return false;
                }
                catch (InvalidVendorException e){
                    //this is good, let's try to add vendor.
                    DataStoreFactory dsFactory = DataStoreFactory.getInstance();
                    try {
                        dsFactory.getVendorDS(this.categoryCode).update(this.vendor);
                        return true;
                    } catch (UpdateFailedException | IOException ufe) {
                        JOptionPane.showMessageDialog(null,
                                "Error :: " + ufe.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        this.vendor = null;
                        return false;
                    }
                }
            }
            catch (InvalidCategoryCodeException e) {
                JOptionPane.showMessageDialog(null,
                                "Error :: " + e.getMessage(),
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
