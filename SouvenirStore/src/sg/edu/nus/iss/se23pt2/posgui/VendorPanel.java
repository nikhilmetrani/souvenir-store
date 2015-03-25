package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class VendorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Inventory        		inventory;
    private java.util.List<Category> 	categories;
    private java.awt.Choice			categoryChoice; 
    private java.awt.List          	vendorList;
    
    private JFrame					parent;
   
    
    public VendorPanel (Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout (new BorderLayout());
        
        //Initialize Choice for user to select category
        categoryChoice=new java.awt.Choice(); 
        //Add the listener for categoryChoice
        categoryChoice.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie)
            {
            	showVendorsForSelectedCategory();
            }
        });
        
        //Initialize List for showing vendors for selected category
        vendorList = new java.awt.List(5);
        vendorList.setMultipleMode (false);
        
        //JPanel for category
        JPanel c = new JPanel ();
        c.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.setLayout (new FlowLayout(FlowLayout.LEADING));
        c.add (new JLabel ("Please choose category:"));
        c.add (categoryChoice);         
       
        //JPanel for Vendors
        JPanel v = new JPanel ();
        v.setLayout (new BorderLayout());
        v.add ("North",new JLabel ("Vendors:"));
        v.add ("Center", vendorList);
        v.add ("East", createButtonPanel());        
        
        add ("North", c);        
        add ("Center", v);      
    }

    public void refresh () {
    	
    	categories = inventory.getAllCategories();
    	categoryChoice.removeAll();    	
        Category cat = null;
        Iterator<Category> i = categories.iterator();
        while (i.hasNext()) {        	
        	cat = i.next();
        	categoryChoice.add (cat.getCode());        
        }
        showVendorsForSelectedCategory();     
    }
    
    private void showVendorsForSelectedCategory(){
    	vendorList.removeAll();    	
    	String selectedCat=categoryChoice.getSelectedItem();
        if(selectedCat!="")
        {
        	ArrayList<Vendor> vendorsForCategory=inventory.getVendorsForCategory(selectedCat);   
        	if(vendorsForCategory!=null){
        		Iterator<Vendor> iterator = vendorsForCategory.iterator();
                while (iterator.hasNext()) {
                	Vendor vendor = iterator.next();
                	vendorList.add(vendor.getName() + " - " + vendor.getDescription());
                }
        	}        	 
        }         
    }
    
    public Category getSelectedCategory () {
        int idx = categoryChoice.getSelectedIndex();
        return (idx == -1) ? null : categories.get(idx);
    }
    
    public Vendor getSelectedVendor () {
        int idx = vendorList.getSelectedIndex();
        String selectedCat=categoryChoice.getSelectedItem();
        ArrayList<Vendor> vendorsForCategory=inventory.getVendorsForCategory(selectedCat);           
        return (idx == -1) ? null : vendorsForCategory.get(idx);
    }
    
    private void MoveUpSelectedVendor()
    {
    	 int idx = vendorList.getSelectedIndex();
    	 if(idx <1)return;    	 
         String selectedCat=categoryChoice.getSelectedItem();
         ArrayList<Vendor> vendorsForCategory=inventory.getVendorsForCategory(selectedCat);  
         Collections.swap(vendorsForCategory,idx,idx-1);          
         showVendorsForSelectedCategory();
    }
    
    private void MoveDownSelectedVendor()
    {
    	 int idx = vendorList.getSelectedIndex();
    	 if(idx == -1 )return;    	 
         String selectedCat=categoryChoice.getSelectedItem();
         ArrayList<Vendor> vendorsForCategory=inventory.getVendorsForCategory(selectedCat);  
         int listSize= vendorsForCategory.size()-1;
         if(idx == listSize )return;    
         Collections.swap(vendorsForCategory,idx,idx+1);    
         showVendorsForSelectedCategory();
    }
    
    private JPanel createButtonPanel () {

        JPanel p = new JPanel (new GridLayout (0, 1));

        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	String selectedCat=categoryChoice.getSelectedItem();
                AddEditVendorDialog d = new AddEditVendorDialog(selectedCat,VendorPanel.this.inventory, VendorPanel.this.parent);
                d.setLocationRelativeTo(VendorPanel.this.parent);
                d.setModal(true);
                d.pack();
                d.setVisible (true);
                VendorPanel.this.showVendorsForSelectedCategory();
            }
        });      
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                inventory.removeVendor(categoryChoice.getSelectedItem(), getSelectedVendor());
            	VendorPanel.this.showVendorsForSelectedCategory();
            }
        });        
        p.add (b);
        
        b = new JButton ("Edit");    
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	String selectedCat=categoryChoice.getSelectedItem();
            	AddEditVendorDialog d = new AddEditVendorDialog(selectedCat,getSelectedVendor(), VendorPanel.this.parent);
            	d.setLocationRelativeTo(VendorPanel.this.parent);
            	d.setModal(true);
                d.pack();
                d.setVisible (true);
                VendorPanel.this.showVendorsForSelectedCategory();
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

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }
}