package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

//
//Generated by StarUML(tm) Java Add-In
//
//@ Project : SouvenirStore
//@ File Name : DiscountPanel.java
//@ Date : 23/03/2015
//@ Author : Niu Yiming
//

public class DiscountPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Inventory        		inventory;
    private JFrame					parent;
    private JScrollPane 			scrollPane;
    private JTable 					table;
    private DiscountTableModel 		model;
    
    public DiscountPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        this.scrollPane = new JScrollPane();
        this.model = new DiscountTableModel(this.inventory.getDiscounts(), parent);
        this.table = new JTable(model);
        this.table.getModel().addTableModelListener(new TableModelListener() {
		
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
						DataStoreFactory dsFactory = DataStoreFactory.getInstance();
						
				        try {
				        	dsFactory.getDiscountDS().update(DiscountPanel.this.getSelected());
				        }
				        catch (UpdateFailedException ufe) {
				        	JOptionPane.showMessageDialog(null,
			                        "Error :: " + ufe.getMessage(),
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
        
        TableColumn catColumn = this.table.getColumnModel().getColumn(5);
        catColumn.setCellEditor(new DefaultCellEditor(this.getDiscountTypeCombo()));
        this.scrollPane.setViewportView(this.table);
        
        add ("North", new JLabel ("Discounts"));
        add ("Center", this.scrollPane);
        add ("East", createButtonPanel());
    }

    private JComboBox<String> getDiscountTypeCombo(){
    	String[] discountTypes = {"M", "A"};
    	return new JComboBox<String>(discountTypes);
    }
    
    public void refresh () {
    	this.table.setVisible(false);
    	this.table.setVisible(true);
    }

    public void select(int index) {
    	ListSelectionModel selectionModel = this.table.getSelectionModel();
    	if ((index >= 0) && (this.model.size() > index))
    		selectionModel.setSelectionInterval(index, index);
    }
    
    public Discount getSelected() {
    	int idx = this.table.getSelectedRow();
        return (idx == -1) ? null : this.model.get(idx);
    }

    private JPanel createButtonPanel () {

    	JPanel p = new JPanel (new GridLayout (0, 1, 5, 5));
    	p.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                AddDiscountDialog d = new AddDiscountDialog(DiscountPanel.this.inventory, DiscountPanel.this.parent);
                d.setVisible (true);
                if (null != d.getAdded()) {
                	DiscountPanel.this.inventory.addDisc(d.getAdded());
                	DiscountPanel.this.model.add(d.getAdded());
                	DiscountPanel.this.refresh();
                	DiscountPanel.this.select(DiscountPanel.this.model.size()-1);
                }
            }
        });
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	if (null != DiscountPanel.this.getSelected()) {
            		if (0 == StoreAppWindow.confirm("Are you sure you want to remove selected discount?\nClick Ok to continue.")) {
	            		int index = DiscountPanel.this.table.getSelectedRow();
	            		
	            		DataStoreFactory dsFactory = DataStoreFactory.getInstance();
	            		try {
	        	        	dsFactory.getDiscountDS().remove(DiscountPanel.this.getSelected());
	        	        	DiscountPanel.this.inventory.removeDisc(DiscountPanel.this.getSelected().getDiscCode());
	                		DiscountPanel.this.model.remove(DiscountPanel.this.getSelected());
	    	            	DiscountPanel.this.refresh();
	    	            	if (1 <= index) {
	    	            		index -= 1;
	    	            		DiscountPanel.this.select(index);
	    	            	}
	    	            	else {
	    	            		if (DiscountPanel.this.model.size() >= 1)
	    	            			DiscountPanel.this.select(0);
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
            }
        });
        p.add (b);
        
        b = new JButton ("Close");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            		DiscountPanel.this.parent.setContentPane(new EmptyPanel(DiscountPanel.this.parent));
                    DiscountPanel.this.parent.repaint();
            }
        });
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }

}