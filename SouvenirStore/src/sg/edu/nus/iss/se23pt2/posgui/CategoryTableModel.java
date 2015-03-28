/**
 * 
 */
package sg.edu.nus.iss.se23pt2.posgui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.se23pt2.pos.*;

/**
 * @author Nikhil Metrani
 *
 */
public class CategoryTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<Category> categoryList;
    
    private final String[] columnHeaders = new String[] {
            "Category code", "Category name"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        String.class, String.class
    };

    public CategoryTableModel(List<Category> categoryList)
    {
        this.categoryList = categoryList;
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnHeaders[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount()
    {
        return columnHeaders.length;
    }

    @Override
    public int getRowCount()
    {
        return categoryList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Category row = categoryList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getCode();
        }
        else if(1 == columnIndex) {
            return row.getName();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
    	if (0 == columnIndex)
    		return false;
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Category row = categoryList.get(rowIndex);
        if(0 == columnIndex) {
            row.setCode((String)aValue);
        }
        else if(1 == columnIndex) {
            row.setName((String) aValue);
        }
    }
    
    public boolean add(Category category) {
    	return this.categoryList.add(category);
    }
    
    public boolean remove(Category category){
    	return this.categoryList.remove(category);
    }
    
    public Category remove(int index){
    	if ((0 > index) || (index >= this.categoryList.size()))
    		return null;
    	return this.categoryList.remove(index);
    }
    
    public Category get(int index) {
    	if ((0 > index) || (index >= this.categoryList.size()))
    		return null;
    	return this.categoryList.get(index);
    }
    
    public int size() {
    	return this.categoryList.size();
    }
}
