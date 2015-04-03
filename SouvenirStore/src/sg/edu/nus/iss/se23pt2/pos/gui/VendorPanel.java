package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * @author jing dong
 *
 */
public class VendorPanel extends JPanel {

    private static final long serialVersionUID = 1L;
	
    private final Inventory               inventory;
    private final JComboBox<String>       categoryCombo; 
    private JTable                        table;
    private VendorTableModel              model;
    private final JFrame                  parent;
    private final JScrollPane             scrollPane;
   
    
    public VendorPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        //Initialize Choice for user to select category
        this.categoryCombo = new JComboBox(); 
        this.loadCategories();
        
        //Initialize List for showing vendors for selected category
        this.model = new VendorTableModel(this.inventory.getVendors(this.getSelectedCategory()));
        this.table = new JTable(this.model);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Add the listener for categoryCombo
        this.categoryCombo.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
                    VendorPanel.this.showVendorsForSelectedCategory();
            }
        });
        
        this.table.getModel().addTableModelListener(new TableModelListener() {
			
            @Override
            public void tableChanged(TableModelEvent e) {
                if (TableModelEvent.UPDATE == e.getType()) {
                    DataStoreFactory dsFactory = DataStoreFactory.getInstance();

                    try {
                            dsFactory.getVendorDS(VendorPanel.this.getSelectedCategory()).update(VendorPanel.this.getSelected());
                    }
                    catch (UpdateFailedException | IOException ufe) {
                            JOptionPane.showMessageDialog(null,
                            "Error :: " + ufe.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.table);
        
        //JPanel for category
        JPanel c = new JPanel ();
        c.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.setLayout (new FlowLayout(FlowLayout.LEADING));
        c.add (new JLabel ("Please choose category:"));
        c.add (categoryCombo);         
       
        //JPanel for Vendors
        JPanel v = new JPanel ();
        v.setLayout (new BorderLayout());
        v.add ("North",new JLabel ("Vendors:"));
        v.add ("Center", this.scrollPane);
        v.add ("East", createButtonPanel());        
        
        add ("North", c);        
        add ("Center", v);      
    }

    private void loadCategories() {
    	java.util.List<Category> catList = inventory.getCategories();
    	
    	if (null != catList) {
    		this.categoryCombo.removeAll();
	    	Category cat = null;
	        Iterator<Category> i = catList.iterator();
	        while (i.hasNext()) {
	        	cat = i.next();
	        	this.categoryCombo.addItem(cat.getCode());
	        }
	        if (0 < this.categoryCombo.getItemCount())
	        	this.categoryCombo.setSelectedIndex(0);
    	}
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
    
    public void showVendorsForSelectedCategory(){
        //Nikhil Metrani
        // null check required
        ArrayList<Vendor> vendors = this.inventory.getVendors(this.getSelectedCategory());
        if (null == vendors) vendors = new ArrayList();
    	this.model = new VendorTableModel(vendors);
    	this.table = new JTable(this.model);
    	this.scrollPane.setViewportView(this.table);
    	this.refresh();         
    }
    
    private Category getCategoryFromCode(String code)
    {
    	java.util.List<Category> catList = inventory.getCategories();
    	
    	if (null != catList) {
    		
	    	Category cat = null;
	        Iterator<Category> i = catList.iterator();
	        while (i.hasNext()) {
	        	cat = i.next();
	        	if(cat.getCode().equals(code)){
	        		return cat;
	        	}
	        }
    	}
    	return null;
    }
    
    public String getSelectedCategory() {
        int idx = categoryCombo.getSelectedIndex();        
        return (idx == -1) ? null : categoryCombo.getSelectedItem().toString();
    }
    
    public Vendor getSelected() {
        int idx = this.table.getSelectedRow();
        return (idx == -1) ? null : this.model.get(idx);
    }
    
    private void MoveUpSelectedVendor()
    {
    	int idx = this.table.getSelectedRow();
    	if(idx == -1 || idx== 0 )return; 
         ArrayList<Vendor> vendorsForCategory=this.inventory.getVendors(this.getSelectedCategory());     
         Collections.swap(vendorsForCategory,idx,idx-1);          
         DataStoreFactory dsFactory = DataStoreFactory.getInstance(); 
         try{ 
        	 dsFactory.getVendorDS(getSelectedCategory()).swap(this.model.get(idx), this.model.get(idx-1));            
         }catch (IOException ufe) {
             JOptionPane.showMessageDialog(null,
                     "Error :: " + ufe.getMessage(),
                     "Error",
                     JOptionPane.ERROR_MESSAGE);             
         }        
         showVendorsForSelectedCategory();
    }
    
    private void MoveDownSelectedVendor()
    {
    	int idx = this.table.getSelectedRow();
    	 if(idx == -1 )return;    	     	 
         ArrayList<Vendor> vendorsForCategory=this.inventory.getVendors(this.getSelectedCategory());  
         int listSize= vendorsForCategory.size()-1;
         if(idx == listSize )return;    
         Collections.swap(vendorsForCategory,idx,idx+1);  
         DataStoreFactory dsFactory = DataStoreFactory.getInstance(); 
         try{ 
        	 dsFactory.getVendorDS(getSelectedCategory()).swap(this.model.get(idx), this.model.get(idx+1));            
         }catch (IOException ufe) {
             JOptionPane.showMessageDialog(null,
                     "Error :: " + ufe.getMessage(),
                     "Error",
                     JOptionPane.ERROR_MESSAGE);             
         }      
         showVendorsForSelectedCategory();
    }
    private JPanel createButtonPanel () {

    	JPanel p = new JPanel (new GridLayout (0, 1, 5, 5));
    	p.setBorder(new EmptyBorder(5, 5, 5, 5));
    	
        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
            	String selectedCat = VendorPanel.this.categoryCombo.getSelectedItem().toString();
            	
            	AddVendorDialog d = new AddVendorDialog(selectedCat,VendorPanel.this.inventory, VendorPanel.this.parent);
                d.setVisible (true);
                if (null != d.getAdded()) {
                	//VendorPanel.this.inventory.addVendor(selectedCat, d.getAdded());
                	VendorPanel.this.model.add(d.getAdded());
                	VendorPanel.this.refresh();
                	VendorPanel.this.select(VendorPanel.this.model.size()-1);
                }
            }
        });      
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
            	if (null != VendorPanel.this.getSelected()) {
            		if (0 == StoreAppWindow.confirm("Are you sure you want to remove selected vendor?\nClick Ok to continue.")) {
                            int index = VendorPanel.this.table.getSelectedRow();

                            DataStoreFactory dsFactory = DataStoreFactory.getInstance();
                            try {
                                    Vendor vendorSelected=VendorPanel.this.getSelected();
                                    dsFactory.getVendorDS(VendorPanel.this.getSelectedCategory()).remove(vendorSelected);
                                    VendorPanel.this.inventory.removeVendor(VendorPanel.this.getSelectedCategory(), vendorSelected);
                                    VendorPanel.this.model.remove(vendorSelected);
                                    VendorPanel.this.refresh();
                            if (1 <= index) {
                                    index -= 1;
                                    VendorPanel.this.select(index);
                            }
                            else {
                                    if (VendorPanel.this.model.size() >= 1)
                                            VendorPanel.this.select(0);
                            }
                            }
                            catch (RemoveFailedException | IOException rfe) {
                                    JOptionPane.showMessageDialog(null,
                                    "Error :: " + rfe.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            }
            		}
            	}
            }
        });        
        p.add (b);

		b = new JButton ("Move Up");    
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	MoveUpSelectedVendor();
            }
        });        
        p.add (b);
        
        b = new JButton ("Move Down"); 
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	MoveDownSelectedVendor();
            }
        });        
        p.add (b);
        
        b = new JButton ("Close");    
        b.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
            	VendorPanel.this.parent.setContentPane(new EmptyPanel(VendorPanel.this.parent));
                VendorPanel.this.parent.repaint();
            }
        });        
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }
    
}