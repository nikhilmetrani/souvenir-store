/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos.gui;

import java.util.List;

import javax.swing.event.TableModelEvent;
import sg.edu.nus.iss.se23pt2.pos.*;

/**
 * @author Nikhil Metrani
 *
 */
public class CategoryTableModel extends AbstractStoreTableModel<Category> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private final String[] columnHeaders = new String[] {
            "Category code", "Category name"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        String.class, String.class
    };

    public CategoryTableModel(List<Category> categoryList) {
        super(categoryList);
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
        Category row = super.list.get(rowIndex);
        if(0 == columnIndex) {
            return row.getCode();
        }
        else if(1 == columnIndex) {
            return row.getName();
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
        Category row = super.list.get(rowIndex);
        //We don't allow editing of Category code, let's just update the name 
        if(1 == columnIndex) {
            row.setName((String) aValue);
            TableModelEvent e = new TableModelEvent(this, rowIndex);
            fireTableChanged(e);
        }
    }
}
