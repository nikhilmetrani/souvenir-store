package sg.edu.nus.iss.se23pt2.pos.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Transaction;
import sg.edu.nus.iss.se23pt2.pos.TransactionSort;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidTransactionException;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * 
 * @author Rushabh Shah 
 *
 */
public class TransactionPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel searchPanel;
	private JSeparator searchSeparator;
	private JButton search;
	private JLabel transactionsLabel;
	private JPanel searchListPanel;
	private JButton close;
	private JButton view;
	private JPanel buttonPanel;
	private JTextArea endDateText;
	private JLabel endDateLabel;
	private JLabel transactionSearchLabel;
	private JTextArea startDateText;
	private JLabel startDateLabel;
	private JFrame parent;
	private JButton clear;
	private JButton jButton1;
	private JLabel formatLabel;
	private JTable table;
	private JScrollPane scrollPane;
	private SouvenirStore store;
	private SimpleDateFormat dateFormat;
	private ArrayList<Transaction> transactions;
	private TransactionDetailPanel detailPanel;
	
	public TransactionPanel(JFrame parent, SouvenirStore store) {	
		this.parent = parent;
		this.store=store;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(650, 453));
			this.setLayout(null);
			this.setSize(650, 453);
			{
				searchPanel = new JPanel();
				this.add(searchPanel);
				searchPanel.setBounds(0, 0, 533, 87);
				searchPanel.setLayout(null);
				searchPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				{
					startDateLabel = new JLabel();
					searchPanel.add(startDateLabel);
					startDateLabel.setText("Start Date ");
					startDateLabel.setBounds(6, 45, 78, 21);
				}
				{
					startDateText = new JTextArea();
					searchPanel.add(startDateText);
					startDateText.setBounds(86, 49, 115, 15);
				}
				{
					transactionSearchLabel = new JLabel();
					searchPanel.add(transactionSearchLabel);
					transactionSearchLabel.setText("Transaction Search Criteria");
					transactionSearchLabel.setBounds(6, 4, 214, 21);
				}
				{
					endDateLabel = new JLabel();
					searchPanel.add(endDateLabel);
					endDateLabel.setText("End Date");
					endDateLabel.setBounds(228, 46, 61, 21);
				}
				{
					endDateText = new JTextArea();
					searchPanel.add(endDateText);
					endDateText.setBounds(301, 48, 115, 16);
				}
				{
					searchSeparator = new JSeparator();
					searchPanel.add(searchSeparator);
					searchSeparator.setBounds(2, 31, 529, 10);
				}
				{
					formatLabel = new JLabel();
					searchPanel.add(formatLabel);
					formatLabel.setText("*Enter date in format \"yyyy-MM-dd\"");
					formatLabel.setBounds(390, 67, 141, 21);
					formatLabel.setFont(new java.awt.Font("Segoe UI",2,9));
				}
			}
			{
				buttonPanel = new JPanel();
				this.add(buttonPanel);
				buttonPanel.setBounds(533, 111, 105, 141);
				buttonPanel.setLayout(null);
				{
					search = new JButton();
					buttonPanel.add(search);
					search.setText("Search");
					search.setPreferredSize(new java.awt.Dimension(100, 22));
					search.setBounds(3, 5, 100, 22);
					search.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent arg0) {
							Date startDate = null, endDate = null;
							try {
									String startDateStr = startDateText.getText();
									String endDateStr = endDateText.getText();
									if (startDateStr != null
											&& startDateStr.length() > 0){
										startDate = dateFormat.parse(startDateStr);
									}									
									if (endDateStr != null
											&& endDateStr.length() > 0){
										endDate = dateFormat.parse(endDateStr);
									}
									transactions = store.getTransactions(startDate,endDate);
									transactions.sort(new TransactionSort());
									refresh();
							} catch (ParseException e) {
								errorPane(e);
							} catch (InvalidTransactionException e) {
								errorPane(e);
							} catch (AccessDeniedException e) {
								errorPane(e);
							} catch (DataLoadFailedException e) {
								errorPane(e);
							} catch (IOException e) {
								errorPane(e);
							}
						}

						private void errorPane(Exception e) {
							JOptionPane.showMessageDialog(null,
									 e.getMessage(), "Transaction Search Error",
									JOptionPane.ERROR_MESSAGE);
						}
					});
				}
				{
					view = new JButton();
					buttonPanel.add(view);
					view.setText("View");
					view.setBounds(3, 57, 100, 22);
					view.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (detailPanel == null){
								detailPanel = new TransactionDetailPanel(TransactionPanel.this.parent, TransactionPanel.this);
							}
							detailPanel.setTransaction(TransactionPanel.this.getSelectedTransaction());
							TransactionPanel.this.parent.setContentPane(detailPanel);
							TransactionPanel.this.parent.setSize(650, 453);
						}
					});
				}
				{
					close = new JButton();
					buttonPanel.add(close);
					close.setText("Close");
					close.setBounds(2, 84, 100, 22);
					close.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							parent.setContentPane(new EmptyPanel(parent));
							parent.repaint();
						}
					});
				}
				{
					clear = new JButton();
					buttonPanel.add(clear);
					clear.setText("Clear");
					clear.setBounds(3, 31, 100, 22);
					clear.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							startDateText.setText(null);
							endDateText.setText(null);
						}
					});
				}
			}
			{
				searchListPanel = new JPanel();
				this.add(searchListPanel);
				searchListPanel.setBounds(0, 83, 533, 360);
				searchListPanel.setLayout(null);
				{
					transactionsLabel = new JLabel();
					searchListPanel.add(transactionsLabel);
					transactionsLabel.setText("Transactions");
					transactionsLabel.setBounds(6, 6, 82, 21);
				}
				{					
					refresh();
				}
			}
			{
				jButton1 = new JButton();
				this.add(jButton1);
				jButton1.setText("Close");
				jButton1.setPreferredSize(new java.awt.Dimension(100,22));
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						parent.setContentPane(new EmptyPanel(parent));
						parent.repaint();
					}
				});
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
			searchListPanel.add(scrollPane);
			scrollPane.setBounds(6, 33, 527, 277);
			{
				scrollPane.setViewportView(table);
				table.setModel(new TransactionModel(transactions));
				table.setPreferredSize(new java.awt.Dimension(509, 256));
			}			
	}
	
	 public Transaction getSelectedTransaction() {
	        int id = table.getSelectedRow();
	        return (id == -1) ? null : transactions.get(id);
	    }

}
