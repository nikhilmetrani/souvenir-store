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
    private java.awt.List          	categoryList;
    private JFrame					parent;
    
    public CategoryPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout());
        categoryList = new java.awt.List (5);
        categoryList.setMultipleMode (false);
        add ("North", new JLabel ("Categories"));
        add ("Center", categoryList);
        add ("East", createButtonPanel());
    }

    public void refresh () {
    	categories = inventory.getAllCategories();
        categoryList.removeAll();
        Iterator<Category> i = categories.iterator();
        while (i.hasNext()) {
        	categoryList.add (i.next().getCode());
        }
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
                d.setLocationRelativeTo(CategoryPanel.this.parent);
                d.setModal(true);
                d.pack();
                d.setVisible (true);
            }
        });
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                //inventory.removeCategory();
            }
        });
        p.add (b);
        
        b = new JButton ("Edit");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	AddEditCategoryDialog d = new AddEditCategoryDialog(CategoryPanel.this.getSelectedCategory(), CategoryPanel.this.parent);
            	d.setLocationRelativeTo(CategoryPanel.this.parent);
            	d.setModal(true);
                d.pack();
                d.setVisible (true);
            }
        });
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }

}