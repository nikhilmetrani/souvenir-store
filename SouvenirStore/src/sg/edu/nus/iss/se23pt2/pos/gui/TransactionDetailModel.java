package sg.edu.nus.iss.se23pt2.pos.gui;

import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.se23pt2.pos.Transaction;

/**
 * 
 * @author Rushabh Shah 
 *
 */
public class TransactionDetailModel extends AbstractTableModel  {

	private static final long serialVersionUID = 1L;

	private Transaction transaction;
	
	private String[] columnNames;

	private String[][] transDetailModel;
	
	
	public TransactionDetailModel(Transaction transaction){
		this.transaction = transaction;
		columnNames = new String[] { "Transaction Id", "Member Id","Date","Product Id","Product Name","Product Description","Quantity" };
		transDetailModel=new String[0][0];
			if (this.transaction != null  && transaction.getItems()!=null) {
				transDetailModel = new String[getRowCount()][getColumnCount()];
				for (int i = 0; i < this.transaction.getItems().size(); i++) {
				int j = 0;
					transDetailModel[i][j++] = String.valueOf(this.transaction.getId());
					transDetailModel[i][j++] = this.transaction.getCustomer().getId();
					transDetailModel[i][j++] = this.transaction.getDate();
					transDetailModel[i][j++] = this.transaction.getItems().get(i).getProduct().getId();
					transDetailModel[i][j++] = this.transaction.getItems().get(i).getProduct().getName();
					transDetailModel[i][j++] = this.transaction.getItems().get(i).getProduct().getDescription();
					transDetailModel[i][j++] = String.valueOf(this.transaction.getItems().get(i).getQuantity());
				}
			}
		
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		if(transaction!=null && transaction.getItems()!=null){
			return transaction.getItems().size();
		}
		else
			return 0;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return transDetailModel[row][col];
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	

}
