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
public class ProductTableModel extends AbstractStoreTableModel<Product> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String[] columnHeaders = new String[]{
        "Id", "Name", "Description", "Quantity", "Price", "Barcode", "Threshold", "Reorder quantity"
    };

    @SuppressWarnings("rawtypes")
    private final Class[] columnClass = new Class[]{
        String.class, String.class, String.class, Integer.class, Float.class, String.class, Integer.class, Integer.class
    };

    public ProductTableModel(List<Product> productList) {
        super(productList);
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
        Product row = super.list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getName();
            case 2:
                return row.getDescription();
            case 3:
                return row.getQuantity();
            case 4:
                return row.getPrice();
            case 5:
                return row.getBarcode();
            case 6:
                return row.getReorderThresholdQuantity();
            case 7:
                return row.getOrderQuantity();
            /*case 8:
             return row.getCategory();*/
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //For now, editing ProductId and Category is not allowed

        return !((0 == columnIndex) || (5 == columnIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Product row = super.list.get(rowIndex);

        switch (columnIndex) {
            //case 0:
            // Editing not allowed
            case 1:
                row.setName((String) aValue);
                break;
            case 2:
                row.setDescription((String) aValue);
                break;
            case 3:
                row.setQuantity((Integer) aValue);
                break;
            case 4:
                row.setPrice((Float) aValue);
                break;
            /*case 5: //We are not allowing barcode editing
             row.setBarcode((String) aValue);
             break;*/
            case 6:
                row.setReorderThresholdQuantity((Integer) aValue);
                break;
            case 7:
                row.setOrderQuantity((Integer) aValue);
                break;
            /*case 8:
             row.setCategory((Category)aValue);
             break;*/
        }

        //Lets trigger TableChanged event if column is not Id
        if (columnIndex > 0) {
            TableModelEvent e = new TableModelEvent(this, rowIndex);
            fireTableChanged(e);
        }
    }

}
