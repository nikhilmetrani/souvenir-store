package sg.edu.nus.iss.se23pt2.pos.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Transaction;
import sg.edu.nus.iss.se23pt2.pos.TransactionSort;
import sg.edu.nus.iss.se23pt2.pos.exception.DataLoadFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidTransactionException;


/**
 * 
 * @author Rushabh Shah 
 *
 */
public class TransactionPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel searchPanel;
	private JButton search;
	private JLabel transactionsLabel;
	private JPanel searchListPanel;
	private JButton close;
	private JButton view;
	private JPanel buttonPanel;
	private JTextField endDateText;
	private JLabel endDateLabel;
	private JLabel transactionSearchLabel;
	private JTextField startDateText;
	private JLabel startDateLabel;
	private JFrame parent;
	private JButton clear;
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
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				searchListPanel = new JPanel();
				this.add(searchListPanel, BorderLayout.CENTER);
				searchListPanel.setLayout(new BorderLayout());
				{
					transactionsLabel = new JLabel();
					searchListPanel.add(transactionsLabel);
					transactionsLabel.setText("Transaction List");
					searchListPanel.add(transactionsLabel,BorderLayout.NORTH);
					transactionsLabel.setPreferredSize(new java.awt.Dimension(545, 41));
				}
				{					
					refresh();
				}
			}
			{
				searchPanel = new JPanel();
				this.add(searchPanel, BorderLayout.NORTH);
				searchPanel.setBounds(0, 0, 533, 87);
				searchPanel.setLayout(null);
				searchPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				searchPanel.setPreferredSize(new java.awt.Dimension(650, 87));
				{
					startDateLabel = new JLabel();
					searchPanel.add(startDateLabel);
					startDateLabel.setText("Start Date ");
					startDateLabel.setBounds(6, 42, 78, 21);
				}
				{
					startDateText = new JTextField(10);
					searchPanel.add(startDateText);
					startDateText.setBounds(86, 44, 115, 18);
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
					endDateLabel.setBounds(228, 42, 61, 21);
				}
				{
					endDateText = new JTextField(10);
					searchPanel.add(endDateText);
					endDateText.setBounds(301, 44, 115, 18);
				}
				{
					formatLabel = new JLabel();
					searchPanel.add(formatLabel);
					formatLabel.setText("*Enter date in format \"yyyy-MM-dd\"");
					formatLabel.setBounds(425, 64, 141, 21);
					formatLabel.setFont(new java.awt.Font("Segoe UI",2,9));
				}
			}
			{
				buttonPanel = new JPanel();
				this.add(buttonPanel, BorderLayout.EAST);
				buttonPanel.setBounds(533, 111, 105, 141);
				buttonPanel.setLayout(null);
				buttonPanel.setPreferredSize(new java.awt.Dimension(105, 124));
				{
					search = new JButton();
					buttonPanel.add(search);
					search.setText("Search");
					search.setBounds(5, 44, 100, 21);
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
								if(view!=null){
									view.setEnabled(false);
								}
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
					view.setBounds(5, 97, 100, 22);
					view.setEnabled(false);
					view.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (detailPanel == null){
								detailPanel = new TransactionDetailPanel(TransactionPanel.this.parent, TransactionPanel.this);
							}
							detailPanel.setTransaction(TransactionPanel.this.getSelectedTransaction());
							TransactionPanel.this.parent.setContentPane(detailPanel);
							TransactionPanel.this.parent.getContentPane().setVisible(false);
							TransactionPanel.this.parent.getContentPane().setVisible(true);
						}
					});
				}
				{
					close = new JButton();
					buttonPanel.add(close);
					close.setText("Close");
					close.setBounds(5, 124, 100, 22);
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
					clear.setBounds(5, 70, 100, 22);
					clear.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							startDateText.setText(null);
							endDateText.setText(null);
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
			this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			searchListPanel.add(scrollPane);
			{
				scrollPane.setViewportView(table);
				table.setModel(new TransactionModel(transactions));
				table.setPreferredSize(new java.awt.Dimension(542, 401));
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			        public void valueChanged(ListSelectionEvent event) {
			        	if(view!=null){
							view.setEnabled(true);
						}
			        }
			    });
			}			
	}
	
	 public Transaction getSelectedTransaction() {
	        int id = table.getSelectedRow();
	        return (id == -1) ? null : transactions.get(id);
	    }

}
