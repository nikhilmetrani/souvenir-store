package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import sg.edu.nus.iss.se23pt2.pos.Item;
import sg.edu.nus.iss.se23pt2.pos.Transaction;

public class PrintTransactionDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private ArrayList<Transaction> transactions;
	private final JPanel contentPanel = new JPanel();

	public PrintTransactionDialog(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		setModal(true);
		setLocationByPlatform(true);
		setTitle("Transaction Report");
		setBounds(100, 100, 540, 600);
		String blanks = "  ";

		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		JPanel midPanel = new JPanel();
		//midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		midPanel.setLayout(new FlowLayout());
		JPanel transactionPanel = new JPanel();
		transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
		JLabel transactionIdText = new JLabel("Transaction Id: ");
		transactionIdText.setAlignmentX(Component.LEFT_ALIGNMENT);
		transactionPanel.add(transactionIdText, BorderLayout.AFTER_LAST_LINE);

		JPanel transactionPanel1 = new JPanel();
		transactionPanel1.setLayout(new BoxLayout(transactionPanel1, BoxLayout.Y_AXIS));
		JLabel memberIdText = new JLabel("Member Id: ");
		memberIdText.setAlignmentX(Component.LEFT_ALIGNMENT);
		transactionPanel1.add(memberIdText, BorderLayout.AFTER_LAST_LINE);

		JPanel transactionPanel2 = new JPanel();
		transactionPanel2.setLayout(new BoxLayout(transactionPanel2, BoxLayout.Y_AXIS));
		JLabel transactionDateText = new JLabel("Transaction Date: ");
		transactionDateText.setAlignmentX(Component.LEFT_ALIGNMENT);
		transactionPanel2.add(transactionDateText, BorderLayout.AFTER_LAST_LINE);

		JPanel productPanel = new JPanel();
		productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
		JLabel productIdText = new JLabel("Product Id: ");
		productPanel.add(productIdText, BorderLayout.AFTER_LAST_LINE);

		JPanel productPanel1 = new JPanel();
		productPanel1.setLayout(new BoxLayout(productPanel1, BoxLayout.Y_AXIS));
		JLabel productNameText = new JLabel("Product Name: ");
		productPanel1.add(productNameText, BorderLayout.AFTER_LAST_LINE);

		JPanel productPanel2 = new JPanel();
		productPanel2.setLayout(new BoxLayout(productPanel2, BoxLayout.Y_AXIS));
		JLabel productDescText = new JLabel("Product Description: ");
		productPanel2.add(productDescText, BorderLayout.AFTER_LAST_LINE);

		JPanel productPanel3 = new JPanel();
		productPanel3.setLayout(new BoxLayout(productPanel3, BoxLayout.Y_AXIS));
		JLabel quantityText = new JLabel("Quantity: ");
		productPanel3.add(quantityText, BorderLayout.AFTER_LAST_LINE);

		midPanel.add(transactionPanel);
		midPanel.add(transactionPanel2);
		midPanel.add(transactionPanel1);
		midPanel.add(productPanel);
		midPanel.add(productPanel1);
		midPanel.add(productPanel2);
		midPanel.add(productPanel3);

		for(Transaction transaction : this.transactions){
			ArrayList<Item> itemsPaid = transaction.getItems();
			for (Item i : itemsPaid) {
				JLabel transactionId = new JLabel(transaction.getId()+blanks);
				transactionId.setAlignmentX(Component.LEFT_ALIGNMENT);
				transactionPanel.add(transactionId, BorderLayout.AFTER_LAST_LINE);
				
				JLabel memberId = null;
				if (transaction.getCustomer() != null) {
					memberId = new JLabel(transaction.getCustomer().getId() + blanks);
				} else {
					memberId = new JLabel("PUBLIC" + blanks);
				}
				memberId.setAlignmentX(Component.LEFT_ALIGNMENT);
				transactionPanel1.add(memberId, BorderLayout.AFTER_LAST_LINE);
					

				JLabel transactionDate = new JLabel(transaction.getDate() + blanks);
				transactionDate.setAlignmentX(Component.LEFT_ALIGNMENT);
				transactionPanel2.add(transactionDate, BorderLayout.AFTER_LAST_LINE);

				
				JLabel productId = new JLabel(i.getProduct().getId() + blanks);
				productId.setAlignmentX(Component.LEFT_ALIGNMENT);
				productPanel.add(productId, BorderLayout.AFTER_LAST_LINE);
				
				JLabel productName = new JLabel(
						i.getProduct().getName() + blanks);
				productName.setAlignmentX(Component.LEFT_ALIGNMENT);
				productPanel1.add(productName, BorderLayout.AFTER_LAST_LINE);
				
				JLabel productDesc = new JLabel(i.getProduct().getName() + blanks);
				productDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
				productPanel2.add(productDesc, BorderLayout.AFTER_LAST_LINE);
						
				JLabel quantity = new JLabel(blanks + 
						i.getProduct().getQuantity());
				quantity.setAlignmentX(Component.RIGHT_ALIGNMENT);
				productPanel3.add(quantity, BorderLayout.AFTER_LAST_LINE);

			}
		}
		JScrollPane scroll = new JScrollPane(midPanel);
		Border blackline = BorderFactory.createLoweredBevelBorder();
		TitledBorder title = BorderFactory.createTitledBorder(blackline, "Transaction Details");
		title.setTitleJustification(TitledBorder.CENTER);
		scroll.setBorder(title);
		scroll.setWheelScrollingEnabled(true);
		contentPanel.add(scroll, BorderLayout.CENTER);
			
		
	}

	
}
