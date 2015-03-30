package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.*;

public class AddProductDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		
		private Inventory inventory;
		private Product product;
		private JComboBox<Category> productCategoryCombo;
	    private JTextField productNameField;
	    private JTextField productDescriptionField;
	    private JTextField productIdField;
	    private JTextField productQuantityField;
	    private JTextField productOrderQuantityField;
	    private JTextField productReorderThresholdQuantityField;
	    private JTextField productPriceField;
	    private JTextField productBarcodeField;
	    
	    public AddProductDialog (Inventory inventory, JFrame parent) {
	        super(parent, "Add Product");
	        this.inventory = inventory;
	        this.product = null;
	        loadCategories();
	        this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
	    }

	    private void loadCategories() {
	    	java.util.List<Category> catList = inventory.getCategories();
	    	
	    	if (null != catList) {
	    		this.productCategoryCombo.removeAll();
		    	Category cat = null;
		        Iterator<Category> i = catList.iterator();
		        while (i.hasNext()) {
		        	cat = i.next();
		        	this.productCategoryCombo.addItem(cat);
		        }
		        if (null != this.product) {
		        	if (null != this.product.getCategory()) {
		        		this.productCategoryCombo.setSelectedItem(this.product.category);
		        	}
		        }
	    	}
	    }
	    
	    private boolean validateProductDetails() {
	    	//Let's validate number fields
	    	try {
	    		Integer.parseInt(this.productQuantityField.getText());
	    		Integer.parseInt(this.productOrderQuantityField.getText());
	    		Integer.parseInt(this.productReorderThresholdQuantityField.getText());
	    		Float.parseFloat(this.productPriceField.getText());
	    	}
	    	catch (NumberFormatException nfex) {
	    		JOptionPane.showMessageDialog(null,
                        "Error :: Invalid input - Quantity, Price, Reorder Quantity, Order Quantity",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	    		return false;
	    	}
	    	
	    	//Let's validate text fields
	    	//we only need to ensure that name and barcode are not empty
	    	if ((0 == this.productNameField.getText().length()) ||
	    		(0 == this.productBarcodeField.getText().length())) {
	    		JOptionPane.showMessageDialog(null,
                        "Error :: Name and Barcode cannot be empty!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	    		return false;
	    	}
	    	return true;
	    }
	    
	    private Product createNewProduct() {
	    	String id = this.productIdField.getText();
	        String name = this.productNameField.getText();
	    	
	    	if (validateProductDetails()) {
	    		this.product = new Product(id, name);
		    	this.product.setQuantity(Integer.parseInt(this.productQuantityField.getText()));
		    	this.product.setReorderThresholdQuantity(Integer.parseInt(this.productReorderThresholdQuantityField.getText()));
		    	this.product.setOrderQuantity(Integer.parseInt(this.productOrderQuantityField.getText()));
		    	this.product.setPrice(Float.parseFloat(this.productPriceField.getText()));
		    	
		    	//Update string fields
		    	this.product.setName(this.productNameField.getText());
		    	this.product.setDescription(this.productDescriptionField.getText());
		    	this.product.setCategory((Category)this.productCategoryCombo.getSelectedItem());
		    	this.product.setBarcode(this.productBarcodeField.getText());
		    	
		    	//No need to update Id since, category change will take care of it.
		    	//this.product.setId(this.productIdField.getText());
		    	return this.product;
	    	}
	    	//Let's return null if we hit this code (Validation failed)
	    	return null;
	    }
	    
	    protected JPanel createFormPanel () {
	        JPanel p = new JPanel ();
	        p.setLayout (new GridLayout (0, 2));
	        p.add (new JLabel ("Category:"));
	        productCategoryCombo = new JComboBox<Category>();
	        productCategoryCombo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					AddProductDialog.this.productIdField.setText(SequenceGenerator.getInstance().getNextSequence(((Category)AddProductDialog.this.productCategoryCombo.getSelectedItem()).getCode()));
				}
			});
	        p.add(productCategoryCombo);
	        
	        p.add (new JLabel ("Product ID:"));
	        productIdField = new JTextField (20);
	        productIdField.setEnabled(false); //To be disabled after sequence generator is ready
	        p.add(productIdField);
	        
	        p.add (new JLabel ("Product name:"));
	        productNameField = new JTextField (20);
	        p.add(productNameField);
	        
	        p.add (new JLabel ("Description:"));
		    productDescriptionField = new JTextField ();
		    p.add(productDescriptionField);

		    p.add (new JLabel ("Quantity:"));
		    productQuantityField = new JTextField ();
		    p.add(productQuantityField);

		    p.add (new JLabel ("Price:"));
		    productPriceField = new JTextField (20);
		    p.add(productPriceField);

		    p.add (new JLabel ("Barcode:"));
	        productBarcodeField = new JTextField (20);
	        p.add (productBarcodeField);

		    p.add (new JLabel ("Threshold quantity:"));
		    productReorderThresholdQuantityField = new JTextField (20);
		    p.add(productReorderThresholdQuantityField);
	        
		    p.add (new JLabel ("Order quantity:"));
		    productOrderQuantityField = new JTextField (20);
		    p.add(productOrderQuantityField);

	        return p;
	    }

                @Override
	    protected boolean performOkAction() {
    		
	        if (null != createNewProduct()) {
		        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
		        try {
		        	dsFactory.getProductDS().update(this.product);
		        	return true;
		        }
		        catch (UpdateFailedException | IOException ufe) {
		        	JOptionPane.showMessageDialog(null,
	                        "Error :: " + ufe.getMessage(),
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
		        	this.product = null;
		        	return false;
		        }
	        }
	        return false;
	    }

	    public Product getAdded() {
	    	return this.product;
	    }
	}