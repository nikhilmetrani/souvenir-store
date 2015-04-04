package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class AddDiscountDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		
		private Discount discount;
		private JTextField discCodeField;
	    private JTextField discDescField;
	    private JTextField startDateField;
	    private JTextField periodInDaysField;
	    private JTextField discPctField;
	    private static final String appToLabels[] = {"M", "A"};
	    private JComboBox<String> appToField = new JComboBox<String>(appToLabels);
	    
	    public AddDiscountDialog (Inventory inventory, JFrame parent) {
	        super(parent, "Add Discount");
	        this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
	        this.discount = null;
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
	        appToField = new JComboBox<String>(appToLabels);
	        p.add(appToField);
	        
	        return p;
	    }

	    protected boolean performOkAction () {
	    	String discCode = this.discCodeField.getText().toUpperCase();
	        String discDesc = this.discDescField.getText();
	        String startDate = this.startDateField.getText().toUpperCase();
	        String periodInDays = this.periodInDaysField.getText();
	        Double discPct = null;
	        
	        try {
	        	discPct = Double.parseDouble(this.discPctField.getText());
	        }
	        catch (NumberFormatException nfe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + nfe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	return false;
	        }
	        String appTo = (String) this.appToField.getSelectedItem();
	        
	    	//If entered invalid value, then return "invalid message" alert
	        if ((discCode.length() == 0) || (discDesc.length() == 0) ||
        		(startDate.length() == 0) || (periodInDays.length() == 0) ||
        		(appTo.length() == 0) || !startDate.matches("\\d{4}-\\d{2}-\\d{2}")
        		|| ((!periodInDays.toUpperCase().equals("ALWAYS")) && !periodInDays.matches("^\\d+$"))
        	) {
	        	JOptionPane.showMessageDialog(null, "Invalid Input! Please enter again!");
	        	return false;
	        } else if(discPct > 100 || discPct < 0) {
                    JOptionPane.showMessageDialog(null, "Discount percentage must range between 0 to 100!");
                    return false;
                }
            
	        
                if ("m".equals(appTo.toLowerCase()))
                    this.discount = new MemberDiscount(discCode, discDesc, startDate, periodInDays, discPct, appTo);
                else
                    this.discount = new Discount(discCode, discDesc, startDate, periodInDays, discPct, appTo);
	        
	        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
	        try {
	        	dsFactory.getDiscountDS().update(this.discount);
	        	return true;
	        }
	        catch (UpdateFailedException ufe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ufe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.discount = null;
	        	return false;
	        }
	        catch (IOException ioe) {
	        	JOptionPane.showMessageDialog(null,
                        "Error :: " + ioe.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
	        	this.discount = null;
	        	return false;
	        }
	    }
	    
	    public Discount getAdded() {
	    	return this.discount;
	    }
	}