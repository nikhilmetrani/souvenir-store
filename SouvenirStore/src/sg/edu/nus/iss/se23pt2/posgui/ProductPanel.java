package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Inventory        		inventory;
    private java.util.List<Product> 	products;
    private java.awt.List          	productList;
    private JFrame					parent;
    
    public ProductPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout());
        productList = new java.awt.List (5);
        productList.setMultipleMode (false);
        add ("North", new JLabel ("Products"));
        add ("Center", productList);
        add ("East", createButtonPanel());
    }

    public void refresh () {
    	products = inventory.getAllProducts();
    	if (null != products) {
	    	productList.removeAll();
	    	Product prod = null;
	        Iterator<Product> i = products.iterator();
	        while (i.hasNext()) {
	        	prod = i.next();
	        	productList.add (prod.getId() + " - " + prod.getName());
	        }
    	}
    }

    public Product getSelectedProduct() {
        int idx = productList.getSelectedIndex();
        return (idx == -1) ? null : products.get(idx);
    }

    private JPanel createButtonPanel () {

        JPanel p = new JPanel (new GridLayout (0, 1));

        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	AddEditProductDialog d = new AddEditProductDialog(ProductPanel.this.inventory, ProductPanel.this.parent);
                d.setLocationRelativeTo(ProductPanel.this.parent);
                d.setModal(true);
                d.pack();
                d.setVisible (true);
                ProductPanel.this.refresh();
            }
        });
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                inventory.removeProduct(ProductPanel.this.getSelectedProduct().getId());
                ProductPanel.this.refresh();
            }
        });
        p.add (b);
        
        b = new JButton ("Edit");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	AddEditProductDialog d = new AddEditProductDialog(ProductPanel.this.getSelectedProduct(),
            											ProductPanel.this.inventory,
            											ProductPanel.this.parent);
            	d.setLocationRelativeTo(ProductPanel.this.parent);
            	d.setModal(true);
                d.pack();
                d.setVisible (true);
                ProductPanel.this.refresh();
            }
        });
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }

}