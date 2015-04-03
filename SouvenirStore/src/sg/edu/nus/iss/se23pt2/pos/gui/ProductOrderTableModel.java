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
        //All only editing of Price and order quantity
        return ((4 == columnIndex) || (7 == columnIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Product row = super.list.get(rowIndex);

        switch (columnIndex) {
            //Let's not allow editing of critical fields
            case 4: //Price of product might change durgin re-order
                row.setPrice((Float) aValue);
                break;
            case 7: // We might want to adjust order quantity
                row.setOrderQuantity((Integer) aValue);
                break;
        }

        //Lets trigger TableChanged event if column is not Id
        if (columnIndex > 0) {
            TableModelEvent e = new TableModelEvent(this, rowIndex);
            fireTableChanged(e);
        }
    }

}
