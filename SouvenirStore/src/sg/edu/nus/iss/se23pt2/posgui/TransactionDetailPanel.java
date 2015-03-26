package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import sg.edu.nus.iss.se23pt2.pos.Item;
import sg.edu.nus.iss.se23pt2.pos.Transaction;

/**
 * 
 * @author Rushabh Shah 
 *
 */
public class TransactionDetailPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel transactionDetailLabel;
	private JPanel searchPanel;
	private JList<TransactionDetailModel> transactionDetailList;
	private JButton close;
	private JButton back;
	private JFrame parent;
	private Transaction transaction;
	
	public TransactionDetailPanel(JFrame parent, TransactionPanel searchPanel) {
		super();
		this.searchPanel=searchPanel;
		this.parent = parent;
		transactionDetailList = new JList<TransactionDetailModel>();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(650, 453));
			this.setLayout(null);
			{
				transactionDetailLabel = new JLabel();
				this.add(transactionDetailLabel);
				transactionDetailLabel.setText("Transaction Details");
				transactionDetailLabel.setBounds(5, 8, 151, 21);
			}
			{
				refresh();
				transactionDetailList.setBounds(6, 33, 542, 359);
				transactionDetailList.setBorder(BorderFactory
						.createEtchedBorder(BevelBorder.LOWERED));
			}
			{
				back = new JButton();
				this.add(back);
				back.setText("Back");
				back.setBounds(548, 35, 85, 28);
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						parent.setContentPane(searchPanel);
					}
				});
			}
			{
				close = new JButton();
				this.add(close);
				close.setText("Close");
				close.setBounds(548, 68, 85, 28);
				close.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						parent.setContentPane(new EmptyPanel( parent));
		                parent.repaint();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void refresh() {
		this.add(transactionDetailList);
		transactionDetailList.removeAll();
		DefaultListModel<TransactionDetailModel> listModel = new DefaultListModel<TransactionDetailModel>();
		if (transaction != null) {				
				for(Item item:transaction.getItems()){
					TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
					transactionDetailModel.setTransactionId(transaction.getId());
					transactionDetailModel.setMemberId(transaction.getCustomer().getId());
					transactionDetailModel.setDate(transaction.getDate());
					transactionDetailModel.setProductId(item.getProduct().getId());
					transactionDetailModel.setQuantity(item.getQuantity());
					listModel.addElement(transactionDetailModel);
				}
		}
		transactionDetailList.setModel(listModel);			
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		refresh();
	}
	
	

}
