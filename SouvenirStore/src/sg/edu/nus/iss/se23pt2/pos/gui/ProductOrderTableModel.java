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
public class ProductOrderTableModel extends ProductTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ProductOrderTableModel(List<Product> productList) {
        super(productList);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //For now, editing ProductId and Category is not allowed

        return !((0 == columnIndex) || (3 == columnIndex) || (5 == columnIndex));
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
            /*case 3: //Let's not allow editing of quantity
             row.setQuantity((Integer) aValue);
             break;*/
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
