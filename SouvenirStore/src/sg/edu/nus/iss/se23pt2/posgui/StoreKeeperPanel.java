package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.BorderLayout;
import javax.swing.*;
import sg.edu.nus.iss.se23pt2.pos.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Iterator;

public class StoreKeeperPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private java.awt.List storeKeeperList;	
	private java.util.List<StoreKeeper> storeKeepers;
	private SouvenirStore souvenirStore;
	private JFrame parent;
	
    public StoreKeeperPanel(SouvenirStore souvenirStore, JFrame parent) {
    	
    	this.souvenirStore = souvenirStore;
        this.parent = parent;
    	
		setLayout(new BorderLayout(5, 5));
		this.storeKeeperList = new java.awt.List (5);		
		storeKeeperList.setMultipleMode (false);        
        add ("North", new JLabel ("Store Keepers"));
        add ("Center", this.storeKeeperList);
        add ("East", this.createButtonPanel());
	}
    
    public void refresh () {
    	storeKeepers = souvenirStore.getAllStoreKeepers();
    	storeKeeperList.removeAll();
        StoreKeeper storeKeeper = null;        
        Iterator<StoreKeeper> i = storeKeepers.iterator();
        while (i.hasNext()) {
        	storeKeeper = i.next();
        	storeKeeperList.add(storeKeeper.getName());
        }        
    }
    
    public StoreKeeper getSelectedStoreKeeper () {
        int idx = storeKeeperList.getSelectedIndex();
        return (idx == -1) ? null : storeKeepers.get(idx);
    }
	
	private JPanel createButtonPanel () {
		
		JPanel p = new JPanel (new GridLayout (0, 1, 5, 5));
		JButton b = new JButton ("Add");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEditStoreKeeperDialog d = new AddEditStoreKeeperDialog(StoreKeeperPanel.this.souvenirStore, StoreKeeperPanel.this.parent);
                d.setVisible (true);
                StoreKeeperPanel.this.refresh();                
			}
		});
		p.add (b);
				
				
		b = new JButton("Edit");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (null != StoreKeeperPanel.this.getSelectedStoreKeeper()) {
	            	AddEditStoreKeeperDialog d = new AddEditStoreKeeperDialog(StoreKeeperPanel.this.getSelectedStoreKeeper(), StoreKeeperPanel.this.parent);
	            	d.setVisible (true);
	            	StoreKeeperPanel.this.refresh();	            	
            	}
			}
		});
        p.add(b);
        
        
        b = new JButton("Close");
        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		StoreKeeperPanel.this.parent.setContentPane(new EmptyPanel(StoreKeeperPanel.this.parent));
        		StoreKeeperPanel.this.parent.repaint();
        	}
        });
        p.add(b);
        
		
		JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);       
        return bp;
	}
}
