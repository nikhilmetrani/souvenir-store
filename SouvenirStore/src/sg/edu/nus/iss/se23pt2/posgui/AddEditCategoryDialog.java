package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;
import java.awt.*;
import javax.swing.*;

public class AddEditCategoryDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		
		private Inventory inventory;
		private Category category; 
	    private JTextField categoryCodeField;
	    private JTextField categoryNameField;
	    private boolean editMode = false;

	    public AddEditCategoryDialog (Inventory inventory, JFrame parent) {
	        super(parent, "Add Category");
	        this.inventory = inventory;
	        this.category = null;
	    }
	    
	    public AddEditCategoryDialog (Category category, JFrame parent) {
	    	super(parent, "Edit Category");
	    	this.editMode = true;
	        this.inventory = null;
	        this.category = category;
			categoryCodeField.setText(this.category.getCode());
			categoryNameField.setText(this.category.getName());
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
	    	if (this.editMode) {
	    		String code = this.categoryCodeField.getText().toUpperCase();
		        String name = this.categoryNameField.getText();
		        if ((0 == code.length()) || (0 == name.length())) {
		            return false;
		        }
		        this.category.setCode(code);
		        this.category.setName(name);
	    	}
	    	else {
		        String code = this.categoryCodeField.getText().toUpperCase();
		        String name = this.categoryNameField.getText();
		        if ((0 == code.length()) || (0 == name.length())) {
		            return false;
		        }
		        Category cat = new Category(categoryCodeField.getText(), categoryNameField.getText());
		        this.inventory.addCategory(cat);
	    	}
	        return true;
	    }

	}