package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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

import sg.edu.nus.iss.se23pt2.pos.Category;
import sg.edu.nus.iss.se23pt2.pos.ShoppingCart;
import sg.edu.nus.iss.se23pt2.pos.Vendor;

public class CategoryPrintDialog extends JDialog {

	private ArrayList<Category> category;
	private ShoppingCart shoppingCart;
	private final JPanel contentPanel = new JPanel();

	public CategoryPrintDialog(List<Category> categories) {
		this.category = (ArrayList<Category>) categories;
		
		setModal(true);
		setLocationByPlatform(true);
		setTitle("Category Listing");
		setBounds(100, 100, 340, 600);

		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		JLabel lblSouvStore = new JLabel("Souvenir Store");
		Font font = lblSouvStore.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblSouvStore.setFont(font.deriveFont(attributes));
		topPanel.add(lblSouvStore);
		lblSouvStore.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel transactionPanel = new JPanel();
		transactionPanel.setLayout(new BoxLayout(transactionPanel,
				BoxLayout.X_AXIS));
		JLabel lblPrintDateText = new JLabel("Print Request Date: ");
		JLabel lblPrintDate = new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
		lblPrintDateText.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblPrintDate.setAlignmentX(Component.RIGHT_ALIGNMENT);

		transactionPanel.add(lblPrintDateText);
		transactionPanel.add(lblPrintDate);
		topPanel.add(transactionPanel);

		contentPanel.add(topPanel, BorderLayout.NORTH);

		JPanel midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		for (Category c : this.category) {
			JPanel itemsPanel = new JPanel();
			itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));

			JPanel box = new JPanel();
			box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
			JLabel categoryName = new JLabel(c.getName());
			String vendors = "<html>Vendors:" + "<br/>";
			if (c.getAllVendors() != null) {
				for (Vendor v: c.getAllVendors()) {
					vendors += v.getName();
					vendors +=  "<br/>";
				}
				vendors += "</html>";
			}
			JLabel categoryVendor = new JLabel(vendors);
			categoryName.setFont(font.deriveFont(attributes));
			box.add(categoryName);
			box.add(categoryVendor);

			JPanel boxCode = new JPanel();
			boxCode.setLayout(new BoxLayout(boxCode, BoxLayout.Y_AXIS));
			JLabel categoryCode = new JLabel(c.getCode());
			categoryCode.setAlignmentX(Component.RIGHT_ALIGNMENT);
			boxCode.add(categoryCode);

			itemsPanel.add(box);
			itemsPanel.add(Box.createHorizontalGlue());
			itemsPanel.add(boxCode);
			midPanel.add(itemsPanel);
		}
		
		JScrollPane scroll = new JScrollPane(midPanel);
		Border blackline = BorderFactory.createLoweredBevelBorder();
		TitledBorder title = BorderFactory.createTitledBorder(blackline, "List of Categories");
		title.setTitleJustification(TitledBorder.CENTER);
		scroll.setBorder(title);
		scroll.setWheelScrollingEnabled(true);
		contentPanel.add(scroll, BorderLayout.CENTER);

//		JPanel botPanel = new JPanel();
//		botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.Y_AXIS));
//
//		JLabel lblBotDivider = new JLabel(
//				"-----------------------------Payment-----------------------------");
//		lblBotDivider.setAlignmentX(Component.CENTER_ALIGNMENT);
//		botPanel.add(lblBotDivider);
//
//		JPanel grossPanel = new JPanel();
//		grossPanel.setLayout(new BoxLayout(grossPanel, BoxLayout.X_AXIS));
//		JLabel grossText = new JLabel("Gross Total: ");
//		JLabel grossTotal = new JLabel(
//				shoppingCart.getTotalPriceBeforeDiscount() + "");
//		grossPanel.add(grossText);
//		grossPanel.add(Box.createHorizontalGlue());
//		grossPanel.add(grossTotal);
//
//		JPanel discountPanel = new JPanel();
//		discountPanel.setLayout(new BoxLayout(discountPanel, BoxLayout.X_AXIS));
//		JLabel discountText = new JLabel("Discount: ");
//		JLabel discountAmount;
//		if (shoppingCart.getDiscount() != null) {
//			discountAmount = new JLabel(shoppingCart.getDiscount().getDiscPct()
//					+ "%");
//		} else {
//			discountAmount = new JLabel("0%");
//		}
//		discountPanel.add(discountText);
//		discountPanel.add(Box.createHorizontalGlue());
//		discountPanel.add(discountAmount);
//		
//		double payable = 0;
//		payable = shoppingCart.getPayableAmount();
//		
//		JPanel nettPanel = new JPanel();
//		nettPanel.setLayout(new BoxLayout(nettPanel, BoxLayout.X_AXIS));
//		JLabel nettText = new JLabel("Nett Total: ");
//		JLabel nettTotal = new JLabel(payable + "");
//		nettPanel.add(nettText);
//		nettPanel.add(Box.createHorizontalGlue());
//		nettPanel.add(nettTotal);
//
//		JPanel paid = new JPanel();
//		paid.setLayout(new BoxLayout(paid, BoxLayout.X_AXIS));
//		JLabel paidText = new JLabel("Amount Paid: ");
//		JLabel paidTotal = new JLabel(shoppingCart.getAmountPaid() + "");
//		paid.add(paidText);
//		paid.add(Box.createHorizontalGlue());
//		paid.add(paidTotal);
//
//		JPanel balance = new JPanel();
//		balance.setLayout(new BoxLayout(balance, BoxLayout.X_AXIS));
//		JLabel balanceText = new JLabel("Change: ");
//		JLabel balanceTotal = new JLabel(String.valueOf((new BigDecimal(
//				shoppingCart.getAmountPaid() - payable)
//				.setScale(2, BigDecimal.ROUND_HALF_UP))));
//		balance.add(balanceText);
//		balance.add(Box.createHorizontalGlue());
//		balance.add(balanceTotal);
//
//		JPanel redeemed = new JPanel();
//		redeemed.setLayout(new BoxLayout(redeemed, BoxLayout.X_AXIS));
//		JLabel redeemedText = new JLabel("Points Redeemed: ");
//		JLabel redeemedPoints;
//		if (shoppingCart.getMember() != null) {
//			redeemedPoints = new JLabel(shoppingCart.getPoints() + "");
//		} else {
//			redeemedPoints = new JLabel("N/A");
//		}
//		redeemed.add(redeemedText);
//		redeemed.add(Box.createHorizontalGlue());
//		redeemed.add(redeemedPoints);
//
//		JPanel pointsLeft = new JPanel();
//		pointsLeft.setLayout(new BoxLayout(pointsLeft, BoxLayout.X_AXIS));
//		JLabel leftText = new JLabel("Points Remaining: ");
//		JLabel points;
//		if (shoppingCart.getMember() != null) {
//			points = new JLabel(shoppingCart.getMember().getLoyaltyPoints()
//					+ "");
//		} else {
//			points = new JLabel("N/A");
//		}
//		pointsLeft.add(leftText);
//		pointsLeft.add(Box.createHorizontalGlue());
//		pointsLeft.add(points);
//
//		JPanel member = new JPanel();
//		member.setLayout(new BoxLayout(member, BoxLayout.X_AXIS));
//		JLabel memberText = new JLabel("Member ID: ");
//		JLabel id;
//		if (shoppingCart.getMember() != null) {
//			id = new JLabel(shoppingCart.getMember().getId());
//		} else {
//			id = new JLabel("N/A");
//		}
//		member.add(memberText);
//		member.add(Box.createHorizontalGlue());
//		member.add(id);
//		
//		botPanel.add(grossPanel);
//		botPanel.add(discountPanel);
//		botPanel.add(nettPanel);
//		botPanel.add(paid);
//		botPanel.add(balance);
//		botPanel.add(new JLabel("\n"));
//		if (shoppingCart.getMember() != null) {
//			botPanel.add(member);
//			botPanel.add(redeemed);
//			botPanel.add(pointsLeft);
//		}
//		botPanel.add(new JLabel("\n"));
//		botPanel.add(new JLabel("\n"));
//		JLabel thankYou = new JLabel(
//				"Thank you for shopping at The Souvenir Store!");
//		thankYou.setAlignmentX(Component.CENTER_ALIGNMENT);
//		botPanel.add(thankYou);
//
//		contentPanel.add(botPanel, BorderLayout.SOUTH);

	}

}
