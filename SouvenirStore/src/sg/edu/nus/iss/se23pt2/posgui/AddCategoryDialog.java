package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
/**
 * Author: Nikhil Metrani
 * Class: AddCategoryDialog
 * 
 */
public class AddCategoryDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		
		private Category category = null;
		private JTextField categoryCodeField;
	    private JTextField categoryNameField;

	    public AddCategoryDialog (JFrame parent) {
	        super(parent, "Add Category");
	        this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
	    }

	    protected JPanel createFormPanel () {
	        JPanel p = new JPanel ();
	        p.setLayout (new GridLayout (0, 2));
	        p.add (new JLabel ("Category code:"));
	        categoryCodeField = new JTextField (20);
	        p.add (categoryCodeField);
	        p.add (new JLabel ("Category name:"));
	        categoryNameField = new JTextField (20);
	        p.add (categoryNameField);
	        return p;
	    }

	    protected boolean performOkAction () {
	    	String code = this.categoryCodeField.getText().toUpperCase();
	        String name = this.categoryNameField.getText();
	        if ((0 == code.length()) || (0 == name.length())) {
	            return false;
	        }
	        this.category = new Category(code, name);
	        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
	        try {
	        	dsFactory.getCategoryDS().update(this.category);
	        	return true;
	        }
	        catch (UpdateFailedException ufe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ufe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.category = null;
	        	return false;
	        }
	        catch (IOException ioe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ioe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.category = null;
	        	return false;
	        }
	    }

	    public Category getAdded() {
	    	return this.category;
	    }
	}