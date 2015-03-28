package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class CategoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Inventory        			inventory;
    //private java.util.List<Category> 	categories;
    //private JList<Category>          	categoryList;
    private JFrame						parent;
    private JScrollPane 				scrollPane;
    private JTable 						categoryTable;
    private CategoryTableModel 					model;
    
    public CategoryPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout(5,5));
        
        this.scrollPane = new JScrollPane();
        this.model = new CategoryTableModel(this.inventory.getAllCategories());
        this.categoryTable = new JTable(model);
        this.scrollPane.setViewportView(this.categoryTable);
        
        add ("North", new JLabel ("Categories"));
        add ("Center", this.scrollPane);
        add ("East", this.createButtonPanel());
    }

    public void refresh () {
    	this.categoryTable.setVisible(false);
    	this.categoryTable.setVisible(true);
    }
    
    public void select(int index) {
    	ListSelectionModel selectionModel = this.categoryTable.getSelectionModel();
    	if ((index >= 0) && (this.model.size() > index))
    		selectionModel.setSelectionInterval(index, index);
    }

    public Category getSelectedCategory () {
        int idx = this.categoryTable.getSelectedRow();
        return (idx == -1) ? null : this.model.get(idx);
    }

    private JPanel createButtonPanel () {

        JPanel p = new JPanel (new GridLayout (0, 1, 5, 5));

        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                AddCategoryDialog d = new AddCategoryDialog(CategoryPanel.this.parent);
                d.setVisible (true);
                if (null != d.getAddedCategory()) {
                	CategoryPanel.this.inventory.addCategory(d.getAddedCategory());
                	CategoryPanel.this.model.add(d.getAddedCategory());
                	CategoryPanel.this.refresh();
                	CategoryPanel.this.select(CategoryPanel.this.model.size()-1);
                }
            }
        });
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	if (null != CategoryPanel.this.getSelectedCategory()) {
            		int index = CategoryPanel.this.categoryTable.getSelectedRow();
            		
            		DataStoreFactory dsFactory = DataStoreFactory.getInstance();
            		try {
        	        	dsFactory.getCategoryDS().remove(CategoryPanel.this.getSelectedCategory());
        	        	CategoryPanel.this.inventory.removeCategory(CategoryPanel.this.getSelectedCategory().getCode());
                		CategoryPanel.this.model.remove(CategoryPanel.this.getSelectedCategory());
    	            	CategoryPanel.this.refresh();
    	            	if (1 <= index) {
    	            		index -= 1;
    	            		CategoryPanel.this.select(index);
    	            	}
    	            	else {
    	            		if (CategoryPanel.this.model.size() >= 1)
    	            			CategoryPanel.this.select(0);
    	            	}
        	        }
        	        catch (RemoveFailedException rfe) {
        	        	JOptionPane.showMessageDialog(null,
                                "Error :: " + rfe.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
        	        }
        	        catch (IOException ioe) {
        	        	JOptionPane.showMessageDialog(null,
                                "Error :: " + ioe.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
        	        }
            	}
            }
        });
        p.add (b);

        b = new JButton ("Close");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                CategoryPanel.this.parent.setContentPane(new EmptyPanel(CategoryPanel.this.parent));
                CategoryPanel.this.parent.repaint();
            }
        });
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }

}