package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

public class AddEditProductDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		
		private Inventory inventory;
		private Product product;
		private JComboBox<String> productCategoryCombo;
	    private JTextField productNameField;
	    private JTextField productDescriptionField;
	    private JTextField productIdField;
	    private JTextField productQuantityField;
	    private JTextField productOrderQuantityField;
	    private JTextField productReorderThresholdQuantityField;
	    private JTextField productPriceField;
	    private JTextField productBarcodeField;
	    
	    private boolean editMode = false;

	    public AddEditProductDialog (Inventory inventory, JFrame parent) {
	        super(parent, "Add Product");
	        this.inventory = inventory;
	        this.product = null;
	        loadCategories();
	    }
	    
	    public AddEditProductDialog (Product product, Inventory inventory, JFrame parent) {
	    	super(parent, "Edit Product");
	    	this.editMode = true;
	        this.inventory = inventory;
	        this.product = product;
	        loadCategories();
			loadProductDetails();
	    }

	    private void loadCategories() {
	    	java.util.List<Category> catList = inventory.getAllCategories();
	    	
	    	if (null != catList) {
	    		productCategoryCombo.removeAll();
		    	Category cat = null;
		        Iterator<Category> i = catList.iterator();
		        while (i.hasNext()) {
		        	cat = i.next();
		        	productCategoryCombo.addItem(cat.getCode() + " - " + cat.getName());
		        }
	    	}
	    }
	    private void loadProductDetails() {
	    	
	    }
	    
	    protected JPanel createFormPanel () {
	        JPanel p = new JPanel ();
	        p.setLayout (new GridLayout (0, 2));
	        p.add (new JLabel ("Category:"));
	        productCategoryCombo = new JComboBox<String>();
	        p.add(productCategoryCombo);
	        
	        p.add (new JLabel ("Product ID:"));
	        productIdField = new JTextField (20);
	        productIdField.setEnabled(false);
	        p.add(productIdField);
	        
	        p.add (new JLabel ("Product name:"));
	        productNameField = new JTextField (20);
	        p.add(productNameField);
	        
	        p.add (new JLabel ("Description:"));
		    productDescriptionField = new JTextField ();
		    p.add(productDescriptionField);

		    p.add (new JLabel ("Price:"));
		    productPriceField = new JTextField (20);
		    p.add(productPriceField);

		    p.add (new JLabel ("Barcode:"));
	        productBarcodeField = new JTextField (20);
	        p.add (productBarcodeField);
	        
		    p.add (new JLabel ("Quantity:"));
		    productQuantityField = new JTextField ();
		    p.add(productQuantityField);

		    p.add (new JLabel ("Order quantity:"));
		    productOrderQuantityField = new JTextField (20);
		    p.add(productOrderQuantityField);

		    p.add (new JLabel ("Threshold quantity:"));
		    productReorderThresholdQuantityField = new JTextField (20);
		    p.add(productReorderThresholdQuantityField);
	        
	        return p;
	    }

	    protected boolean performOkAction () {
	    	if (this.editMode) {/*
	    		String code = this.categoryCodeField.getText().toUpperCase();
		        String name = this.categoryNameField.getText();
		        if ((0 == code.length()) || (0 == name.length())) {
		            return false;
		        }
		        this.category.setCode(code);
		        this.category.setName(name);*/
	    	}
	    	else {/*
		        String code = this.categoryCodeField.getText().toUpperCase();
		        String name = this.categoryNameField.getText();
		        if ((0 == code.length()) || (0 == name.length())) {
		            return false;
		        }
		        Category cat = new Category(categoryCodeField.getText(), categoryNameField.getText());
		        this.inventory.addCategory(cat);*/
	    	}
	        return true;
	    }

	}