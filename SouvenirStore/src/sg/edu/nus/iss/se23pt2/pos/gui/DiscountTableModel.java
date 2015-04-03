/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;

import sg.edu.nus.iss.se23pt2.pos.Discount;

/**
 * @author Nikhil Metrani
 *
 */
public class DiscountTableModel extends AbstractStoreTableModel<Discount> {
	
	private static final long serialVersionUID = 1L;
	private JFrame parent;
	
	private final String[] columnHeaders = new String[] {
            "Discount code", "Discount description", "Start date", "Period in days", "Percentage", "Applicable to"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        String.class, String.class, String.class, String.class, Double.class, String.class
    };
    
	public DiscountTableModel(List<Discount> discountList) {
		super(discountList);
	}
	
	public DiscountTableModel(List<Discount> discountList, JFrame parent) {
		super(discountList);
		this.parent = parent;
	}
	
	@Override
    public String getColumnName(int column) {
        return columnHeaders[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Discount row = super.list.get(rowIndex);
    	
    	switch (columnIndex) {
    		case 0:
    			return row.getDiscCode();
    		case 1:
    			return row.getDiscDesc();
    		case 2:
    			return row.getStartDate();
    		case 3:
    			return row.getPeriodInDays();
    		case 4:
    			return row.getDiscPct();
    		case 5:
    			return row.getAppTo();
    	}
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if (0 == columnIndex)
    		return false;
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Discount row = super.list.get(rowIndex);
        //We don't allow editing of Discount code, let's update any other fields
        switch (columnIndex) {
			case 0:
				row.setDiscCode((String) aValue);
				break;
			case 1:
				row.setDiscDesc((String) aValue);
				break;
			case 2:
				String origStartDate = row.getStartDate();
				CalendarPanel cp = new CalendarPanel(parent);
				cp.setVisible(true);
				if(cp.getDatePicked() == true) {
					row.setStartDate(cp.getSelDate());	//If date picked from calendar, then update the same in discounts list
				} else {
					//System.out.println("origStartDate = "+origStartDate);
					row.setStartDate(origStartDate);	//If not date picked, then retain the original start date.
				}
				break;
			case 3:
				String pid = (String)aValue;
				if((pid.toString().toUpperCase().equals("ALWAYS"))) {
					row.setPeriodInDays("ALWAYS");
				} else if(pid.matches("^\\d+$")) {	//If numeric value entered
					row.setPeriodInDays((String)aValue);
				} else {
					JOptionPane.showMessageDialog(parent, "Invalid value!");
				}
				break;
			case 4:
				row.setDiscPct(Double.parseDouble(aValue.toString()));
				break;
			case 5:
				row.setAppTo((String) aValue);
				break;
		}
        //Lets trigger TableChanged event if column is not description
        if (columnIndex > 0) {
        	TableModelEvent e = new TableModelEvent(this, rowIndex);
            fireTableChanged(e);
        }
    }
}
