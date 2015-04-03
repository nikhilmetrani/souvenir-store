package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import sg.edu.nus.iss.se23pt2.pos.Item;
import sg.edu.nus.iss.se23pt2.pos.Member;
import sg.edu.nus.iss.se23pt2.pos.ShoppingCart;
import sg.edu.nus.iss.se23pt2.pos.Transaction;


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
public class PrintTransactionDialog extends JDialog {

	private ArrayList<Transaction> transactions;
	private final JPanel contentPanel = new JPanel();

	public PrintTransactionDialog(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		setModal(true);
		setLocationByPlatform(true);
		setTitle("Transaction Receipt");
		setBounds(100, 100, 340, 600);

		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		for(Transaction transaction : transactions){
			ArrayList<Item> itemsPaid = transaction.getItems();
			for (Item i : itemsPaid) {
				JPanel itemsPanel = new JPanel();
				itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));
				
				JPanel transactionPanel = new JPanel();
				transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.X_AXIS));
				JLabel transactionIdText = new JLabel("Transaction Id: ");
				JLabel transactionId = new JLabel(transaction.getId()+"");
				transactionId.setAlignmentX(Component.RIGHT_ALIGNMENT);
				transactionPanel.add(transactionIdText);
				transactionPanel.add(Box.createHorizontalGlue());
				transactionPanel.add(transactionId);
				
				JPanel transactionPanel1 = new JPanel();
				transactionPanel1.setLayout(new BoxLayout(transactionPanel1, BoxLayout.X_AXIS));
				JLabel memberIdText = new JLabel("Member Id: ");
				JLabel memberId = null;
				if (transaction.getCustomer() != null && (transaction.getCustomer() instanceof Member)) {
					memberId = new JLabel(transaction.getCustomer().getId());
				} else {
					memberId = new JLabel("N/A");
				}
				memberId.setAlignmentX(Component.RIGHT_ALIGNMENT);
				transactionPanel1.add(memberIdText);
				transactionPanel1.add(Box.createHorizontalGlue());
				transactionPanel1.add(memberId);
					
				JPanel transactionPanel2 = new JPanel();
				transactionPanel2.setLayout(new BoxLayout(transactionPanel2, BoxLayout.X_AXIS));
				JLabel transactionDateText = new JLabel("Transaction Date: ");
				JLabel transactionDate = new JLabel(transaction.getDate());
				transactionDate.setAlignmentX(Component.RIGHT_ALIGNMENT);
				transactionPanel2.add(transactionDateText);
				transactionPanel2.add(Box.createHorizontalGlue());
				transactionPanel2.add(transactionDate);
				
				JPanel productPanel = new JPanel();
				productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.X_AXIS));
				JLabel productIdText = new JLabel("Product Id: ");
				JLabel productId = new JLabel(
						i.getProduct().getId());
				productId.setAlignmentX(Component.RIGHT_ALIGNMENT);
				productPanel.add(productIdText);
				productPanel.add(Box.createHorizontalGlue());
				productPanel.add(productId);
				
				JPanel productPanel1 = new JPanel();
				productPanel1.setLayout(new BoxLayout(productPanel1, BoxLayout.X_AXIS));
				JLabel productNameText = new JLabel("Product Name: ");
				JLabel productName = new JLabel(
						i.getProduct().getName()+"");
				productName.setAlignmentX(Component.RIGHT_ALIGNMENT);
				productPanel1.add(productNameText);
				productPanel1.add(Box.createHorizontalGlue());
				productPanel1.add(productName);
				
				JPanel productPanel2 = new JPanel();
				productPanel2.setLayout(new BoxLayout(productPanel2, BoxLayout.X_AXIS));
				JLabel productDescText = new JLabel("Product Description: ");
				JLabel productDesc = new JLabel(i.getProduct().getName());
				productDesc.setAlignmentX(Component.RIGHT_ALIGNMENT);
				productPanel2.add(productDescText);
				productPanel2.add(Box.createHorizontalGlue());
				productPanel2.add(productDesc);
						

				JPanel productPanel3 = new JPanel();
				productPanel3.setLayout(new BoxLayout(productPanel3, BoxLayout.X_AXIS));
				JLabel quantityText = new JLabel("Quantity: ");
				JLabel quantity = new JLabel(
						i.getProduct().getQuantity()+"");
				quantity.setAlignmentX(Component.RIGHT_ALIGNMENT);
				productPanel3.add(quantityText);
				productPanel3.add(Box.createHorizontalGlue());
				productPanel3.add(quantity);
				
			    itemsPanel.add(transactionPanel);
				itemsPanel.add(transactionPanel2);
				itemsPanel.add(transactionPanel1);
				itemsPanel.add(productPanel);
				itemsPanel.add(productPanel1);
				itemsPanel.add(productPanel2);
				itemsPanel.add(productPanel3);
				itemsPanel.add(new JLabel("\n"));
				itemsPanel.add(Box.createHorizontalGlue());
				midPanel.add(itemsPanel);
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
