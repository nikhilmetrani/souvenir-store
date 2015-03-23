package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CategoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Inventory        		inventory;
    private java.util.List<Category> 	categories;
    private JList<Category>          	categoryList;
    private JFrame					parent;
    
    public CategoryPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout());
        categoryList = new JList<Category>(); //java.awt.List (5);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //.setMultipleMode (false);
        add ("North", new JLabel ("Categories"));
        add ("Center", categoryList);
        add ("East", createButtonPanel());
    }

    public void refresh () {
    	categories = inventory.getAllCategories();
        categoryList.removeAll();
        Category cat = null;
        DefaultListModel<Category> listModel = new DefaultListModel<Category>();
        Iterator<Category> i = categories.iterator();
        while (i.hasNext()) {
        	cat = i.next();
        	listModel.addElement(cat);
        	//categoryList.;.add(cat);// (cat.getCode() + " - " + cat.getName());
        }
        categoryList.setModel(listModel);
    }

    public Category getSelectedCategory () {
        int idx = categoryList.getSelectedIndex();
        return (idx == -1) ? null : categories.get(idx);
    }

    private JPanel createButtonPanel () {

        JPanel p = new JPanel (new GridLayout (0, 1));

        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                AddEditCategoryDialog d = new AddEditCategoryDialog(CategoryPanel.this.inventory, CategoryPanel.this.parent);
                d.setVisible (true);
                if (null != d.getCategory()) {
                	CategoryPanel.this.refresh();
                	CategoryPanel.this.categoryList.setSelectedValue(d.getCategory(), true);
                }
            }
        });
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	if (null != CategoryPanel.this.getSelectedCategory()) {
            		int index = CategoryPanel.this.categoryList.getSelectedIndex();
	                inventory.removeCategory(CategoryPanel.this.getSelectedCategory().getCode());
	            	CategoryPanel.this.refresh();
	            	if (1 <= index) {
	            		index -= 1;
	            		CategoryPanel.this.categoryList.setSelectedIndex(index);
	            	}
	            	else {
	            		if ( CategoryPanel.this.categoryList.getModel().getSize() >= 1)
	            			CategoryPanel.this.categoryList.setSelectedIndex(0);
	            	}
            	}
            }
        });
        p.add (b);
        
        b = new JButton ("Edit");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	if (null != CategoryPanel.this.getSelectedCategory()) {
	            	AddEditCategoryDialog d = new AddEditCategoryDialog(CategoryPanel.this.getSelectedCategory(), CategoryPanel.this.parent);
	            	d.setVisible (true);
	            	if (null != d.getCategory()) {
	                	CategoryPanel.this.refresh();
	                	CategoryPanel.this.categoryList.setSelectedValue(d.getCategory(), true);
	                }
            	}
            }
        });
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }

}