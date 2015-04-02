package sg.edu.nus.iss.se23pt2.pos.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
	private JPanel listPanel;
	private JPanel buttonPanel;
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
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				transactionDetailLabel = new JLabel();
				this.add(transactionDetailLabel, BorderLayout.NORTH);
				transactionDetailLabel.setText("Transaction Details");
				transactionDetailLabel.setBounds(5, 8, 151, 21);
				transactionDetailLabel.setPreferredSize(new java.awt.Dimension(650, 38));
			}
			{
				listPanel = new JPanel();
				this.add(listPanel, BorderLayout.CENTER);
				listPanel.setLayout(new BorderLayout());
				{
					refresh();				
				}
			}
			
			{
				buttonPanel = new JPanel();
				this.add(buttonPanel, BorderLayout.EAST);
				buttonPanel.setPreferredSize(new java.awt.Dimension(105, 450));
				buttonPanel.setLayout(null);
				{
					back = new JButton();
					buttonPanel.add(back);
					back.setText("Back");
					back.setBounds(3, 0, 100, 22);
					back.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							parent.setContentPane(searchPanel);
							parent.getContentPane().setVisible(false);
							parent.getContentPane().setVisible(true);
						}
					});
				}
				{
					close = new JButton();
					buttonPanel.add(close);
					close.setText("Close");
					close.setBounds(3, 27, 100, 22);
					close.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							parent.setContentPane(new EmptyPanel( parent));
							parent.repaint();
						}
					});
				}
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
			FlowLayout tableLayout = new FlowLayout();
			table.setLayout(tableLayout);
		}
		listPanel.add(scrollPane);
		{
			scrollPane.setViewportView(table);
			table.setPreferredSize(new java.awt.Dimension(548, 415));
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
