package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

public class AddEditProductDialog extends OkCancelDialog {

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
	    
	    private boolean editMode = false;

	    public AddEditProductDialog (Inventory inventory, JFrame parent) {
	        super(parent, "Add Product");
	        this.inventory = inventory;
	        this.product = null;
	        loadCategories();
	        this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
	    }
	    
	    public AddEditProductDialog (Product product, Inventory inventory, JFrame parent) {
	    	super(parent, "Edit Product");
	    	this.editMode = true;
	        this.inventory = inventory;
	        this.product = product;
	        loadCategories();
			loadProductDetails();
			this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
	    }

	    private void loadCategories() {
	    	java.util.List<Category> catList = inventory.getAllCategories();
	    	
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
	    
	    private void loadProductDetails() {
	    	this.productNameField.setText(this.product.getName());
	    	this.productDescriptionField.setText(this.product.getDescription());
	    	this.productIdField.setText(this.product.getId());
	    	this.productQuantityField.setText(this.product.getQuantity().toString());
	    	this.productOrderQuantityField.setText(this.product.getOrderQuantity().toString());
	    	this.productReorderThresholdQuantityField.setText(this.product.getReorderThresholdQuantity().toString());
	    	this.productPriceField.setText(this.product.getPrice().toString());
	    	this.productBarcodeField.setText(this.product.getBarcode());
	    }
	    
	    private boolean updateProductDetails() {
	    	
	    	//Validate and update
	    	Integer quantity = 0;
	    	Integer oQuantity = 0;
	    	Integer rtQuantity = 0;
	    	Float price = Float.parseFloat("0");
	    	try {
	    		quantity = Integer.parseInt(this.productQuantityField.getText());
	    		oQuantity = Integer.parseInt(this.productOrderQuantityField.getText());
	    		rtQuantity = Integer.parseInt(this.productReorderThresholdQuantityField.getText());
	    		price = Float.parseFloat(this.productPriceField.getText());
	    	}
	    	catch (NumberFormatException nfex) {
	    		JOptionPane.showMessageDialog(null,
                        nfex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	    		nfex.printStackTrace();
	    		return false;
	    	}
	    	this.product.setQuantity(quantity);
	    	this.product.setReorderThresholdQty(rtQuantity);
	    	this.product.setOrderQuantity(oQuantity);
	    	this.product.setPrice(price);
	    	
	    	//Update string fields
	    	this.product.setName(this.productNameField.getText());
	    	this.product.setDescription(this.productDescriptionField.getText());
	    	this.product.setCategory((Category)this.productCategoryCombo.getSelectedItem());
	    	this.product.setBarcode(this.productBarcodeField.getText());
	    	
	    	//No need to update Id since, category change will take care of it.
	    	//this.product.setId(this.productIdField.getText());
	    	
	    	return true;
	    }
	    
	    protected JPanel createFormPanel () {
	        JPanel p = new JPanel ();
	        p.setLayout (new GridLayout (0, 2));
	        p.add (new JLabel ("Category:"));
	        productCategoryCombo = new JComboBox<Category>();
	        p.add(productCategoryCombo);
	        
	        p.add (new JLabel ("Product ID:"));
	        productIdField = new JTextField (20);
	        //productIdField.setEnabled(false); //To be disabled after sequence generator is ready
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
	    	if (this.editMode) {
	    		String name = this.productNameField.getText();
		    	String description = this.productDescriptionField.getText();
		    	if ((0 == name.length()) || (0 == description.length())) {
		            return false;
		        }
		    	return updateProductDetails();
	    	}
	    	else {
	    		String id = this.productIdField.getText();
		        String name = this.productNameField.getText();
		    	String description = this.productDescriptionField.getText();
		    	if ((0 == id.length()) || (0 == name.length()) || (0 == description.length())) {
		            return false;
		        }
		        this.product = new Product(id, name);
		        updateProductDetails();
		        this.inventory.addProduct(this.product);
	    	}
	        return true;
	    }

	}