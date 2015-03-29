package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
	private JButton close;
	private JButton back;
	private JFrame parent;
	private JTable table;
	private JScrollPane scrollPane;
	private Transaction transaction;
	
	public TransactionDetailPanel(JFrame parent, TransactionPanel searchPanel) {
		super();
		this.searchPanel=searchPanel;
		this.parent = parent;
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
			{
				refresh();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void refresh() {		
		if(scrollPane==null){
			scrollPane = new JScrollPane();
		}
		if(table==null){
			table = new JTable();
		}
		this.add(scrollPane);
		scrollPane.setBounds(5, 35, 537, 327);
		{
			scrollPane.setViewportView(table);
			table.setPreferredSize(new java.awt.Dimension(534, 351));
			table.setModel(new TransactionDetailModel(transaction));
		}
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
		refresh();
	}
	
	

}
