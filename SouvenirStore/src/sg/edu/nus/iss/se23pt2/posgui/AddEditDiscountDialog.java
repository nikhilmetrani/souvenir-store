package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.*;

import javax.swing.*;

public class AddEditDiscountDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		private boolean editMode = false;
		
		private Inventory inventory;
		private Discount disc; 
	    private JTextField discCodeField;
	    private JTextField discDescField;
	    private JTextField startDateField;
	    private JTextField periodInDaysField;
	    private JTextField discPctField;
	    private static final String appToLabels[] = {"M", "A"};
	    private JComboBox<String> appToField = new JComboBox<String>(appToLabels);
	    
	    public AddEditDiscountDialog (Inventory inventory, JFrame parent) {
	        super(parent, "Add Discount");
	        this.inventory = inventory;
	        this.disc = null;
	    }
	    
	    public AddEditDiscountDialog (Discount disc, JFrame parent) {
	    	super(parent, "Edit Discount");
	    	this.editMode = true;
	        this.inventory = null;
	        this.disc = disc;
	        discCodeField.setText(this.disc.getDiscCode());
	        discDescField.setText(this.disc.getDiscDesc());
	        startDateField.setText(this.disc.getStartDate());
	        periodInDaysField.setText(this.disc.getPeriodInDays());
	        discPctField.setText(this.disc.getDiscPct()+"");
	        appToField.setSelectedItem(this.disc.getAppTo());
	    }

	    protected JPanel createFormPanel () {
	        JPanel p = new JPanel ();
	        p.setLayout(new GridLayout(6, 2, 0, 0));
	        
	        // Discount Code
	        p.add (new JLabel ("Discount code:"));
	        discCodeField = new JTextField (20);
	        p.add (discCodeField);
	        
	        // Discount Description
	        p.add (new JLabel ("Description:"));
	        discDescField = new JTextField (20);
	        p.add (discDescField);
	       
	        // Discount Start Date
	        p.add (new JLabel ("StartDate:"));
	        startDateField = new JTextField (20);
	        p.add (startDateField);
	        
	        // Period-in Days
	        p.add (new JLabel ("Period In Days:"));
	        periodInDaysField = new JTextField (20);
	        p.add (periodInDaysField);
	        
	        // Discount Percentage
	        p.add (new JLabel ("Percentage:"));
	        discPctField = new JTextField (20);
	        p.add (discPctField);
	        
	        // Discount Applicability
	        p.add (new JLabel ("Applicable To:"));
	        appToField = new JComboBox<String>();
	        p.add(appToField);
	        
	        return p;
	    }

	    protected boolean performOkAction () {
	    	String discCode = this.discCodeField.getText().toUpperCase();
	        String discDesc = this.discDescField.getText();
	        String startDate = this.startDateField.getText().toUpperCase();
	        String periodInDays = this.periodInDaysField.getText().toUpperCase();
	        double discPct = Double.parseDouble(this.discPctField.getText());
	        String appTo = (String) this.appToField.getSelectedItem();
	        
	    	if (this.editMode) {
		        if ((discCode.length() == 0) || (discDesc.length() == 0)) {
		            return false;
		        }
		        this.disc.setDiscCode(discCode);
		        this.disc.setDiscDesc(discDesc);
	    	}
	    	else {
		        if ((discCode.length() == 0) || (discDesc.length() == 0)) {
		            return false;
		        }
		        Discount disc = new Discount(discCode, discDesc, startDate, periodInDays, discPct, appTo);
		        this.inventory.addDisc(disc);
	    	}
	        return true;
	    }

	}