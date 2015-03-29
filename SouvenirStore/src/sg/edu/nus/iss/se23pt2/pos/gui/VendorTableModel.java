/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos.gui;

import java.util.List;

import javax.swing.event.TableModelEvent;

import sg.edu.nus.iss.se23pt2.pos.Vendor;

/**
 * @author Nikhil Metrani
 *
 */
public class VendorTableModel extends AbstractStoreTableModel<Vendor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String[] columnHeaders = new String[] {
            "Vendor name", "Vendor description"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        String.class, String.class
    };
    
	public VendorTableModel(List<Vendor> vendorList) {
		super(vendorList);
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
        Vendor row = super.list.get(rowIndex);
        if(0 == columnIndex) {
            return row.getName();
        }
        else if(1 == columnIndex) {
            return row.getDescription();
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
        Vendor row = super.list.get(rowIndex);
        //We don't allow editing of Vendor name, let's just update the description 
        if(1 == columnIndex) {
            row.setDescription((String) aValue);
            TableModelEvent e = new TableModelEvent(this, rowIndex);
            fireTableChanged(e);
        }
    }

}
