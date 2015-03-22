package sg.edu.nus.iss.se23pt2.posgui;


import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JDialog;
import javax.swing.JFrame;
//import javax.swing.JLabel;

import sg.edu.nus.iss.se23pt2.pos.*;

public class ProductDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Inventory inventory = null;
	//private JFrame parent = null;
	private ProductPanel     prodPanel;
	
	/**
	 * Create the dialog.
	 */
	public ProductDialog(Inventory inventory) {
		
		//this.inventory = inventory;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		//setModal(true);
		//setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Manage products");
		setBounds(100, 100, 287, 310);
		getContentPane().setLayout(null);
		
		prodPanel = new ProductPanel(inventory, this);
        prodPanel.refresh();
		Panel p = new Panel ();
        p.setLayout (new GridLayout(0, 1));
        p.add (prodPanel);
		
        p.setBounds(10, 10, 267, 260);
		getContentPane().add(p);

	}
	
	private void UpdateJList(){
		/*
	    ListModel<String> model = new ListModel<String>();
	    ArrayList<Category> catList = this.inventory.getAllCategories();
	    for(Category c : catList){
	        model.addElement(c.toString());
	    }
	    .setModel(model);
	    clientJList.setSelectedIndex(0);*/
	}
}
