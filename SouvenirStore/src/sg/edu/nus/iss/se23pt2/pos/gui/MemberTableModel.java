package sg.edu.nus.iss.se23pt2.pos.gui;

import java.util.List;
import javax.swing.event.TableModelEvent;
import sg.edu.nus.iss.se23pt2.pos.Customer;
import sg.edu.nus.iss.se23pt2.pos.Member;

/**
 * @author Nikhil Metrani
 *
 */
public class MemberTableModel extends AbstractStoreTableModel<Customer> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String[] columnHeaders = new String[]{
        "Member id", "Name", "Loyalty points"
    };

    @SuppressWarnings("rawtypes")
    private final Class[] columnClass = new Class[]{
        String.class, String.class, Integer.class
    };

    public MemberTableModel(List<Customer> memberList) {
        super(memberList);
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
        Customer row = super.list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return ((Member)row).getName();
            case 2:
                return ((Member)row).getLoyaltyPoints();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //We do not allow editing of member ID && points
        return (1 == columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Customer row = super.list.get(rowIndex);

        switch (columnIndex) {
            //We don't allow editing of Member ID, let's update other attributes
            /*case 0:
             row.setId((String) aValue);
             break;*/
            case 1:
                ((Member)row).setName((String) aValue);
                break;
            case 2:
                ((Member)row).setLoyaltyPoints((Integer) aValue);
                break;
        }
        if (0 != columnIndex) {
            TableModelEvent e = new TableModelEvent(this, rowIndex);
            fireTableChanged(e);
        }
    }
}
