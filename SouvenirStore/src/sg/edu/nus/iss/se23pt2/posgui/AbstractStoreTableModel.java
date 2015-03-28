/**
 * 
 */
package sg.edu.nus.iss.se23pt2.posgui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.se23pt2.pos.Product;

/**
 * @author Nick
 *
 */
public abstract class AbstractStoreTableModel<T> extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final List<T> list;
    
	public AbstractStoreTableModel(List<T> list)
    {
        this.list = list;
    }

    @Override
    public int getRowCount()
    {
        return list.size();
    }
    
    public boolean add(T item) {
    	return this.list.add(item);
    }
    
    public boolean remove(T item){
    	return this.list.remove(item);
    }
    
    public T remove(int index){
    	if ((0 > index) || (index >= this.list.size()))
    		return null;
    	return this.list.remove(index);
    }
    
    public T get(int index) {
    	if ((0 > index) || (index >= this.list.size()))
    		return null;
    	return this.list.get(index);
    }
    
    public int size() {
    	return this.list.size();
    }
}
