package sg.edu.nus.iss.se23pt2.pos.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.se23pt2.pos.Transaction;

/**
 * 
 * @author Rushabh Shah
 *
 */
public class TransactionModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<Transaction> transactions;
	
	private String[] columnNames;
	
	private String[][] transModel;	
	
	
	public TransactionModel(List<Transaction> transactions){
	this.transactions = transactions;
	columnNames = new String[] { "Transaction Id", "Member Id","Date" };
	transModel=new String[0][0];
		if (transactions != null) {
			transModel = new String[getRowCount()][getColumnCount()];
			for (int i = 0; i < transactions.size(); i++) {
			int j = 0;
				transModel[i][j++] = String.valueOf(transactions.get(i).getId());
				transModel[i][j++] = transactions.get(i).getCustomer().getId();
				transModel[i][j++] = transactions.get(i).getDate();
			}
		}
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		if(transactions!=null){
			return transactions.size();
		}
		else
			return 0;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return transModel[row][col];
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }

}
