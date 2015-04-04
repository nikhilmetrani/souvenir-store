//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SouvenirStore
//  @ File Name : ShoppingCartPanel.java
//  @ Date : 24/03/2015
//  @ Author : Jaya Vignesh
//
//

package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import sg.edu.nus.iss.se23pt2.pos.Customer;
import sg.edu.nus.iss.se23pt2.pos.Item;
import sg.edu.nus.iss.se23pt2.pos.Product;
import sg.edu.nus.iss.se23pt2.pos.Session;
import sg.edu.nus.iss.se23pt2.pos.ShoppingCart;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;
import sg.edu.nus.iss.se23pt2.pos.Transaction;
import sg.edu.nus.iss.se23pt2.pos.exception.MemberNotFoundException;
import sg.edu.nus.iss.se23pt2.pos.util.DateUtil;

public class ShoppingCartPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private SouvenirStore souvenirStore;
    private ShoppingCart shoppingCart;
    private JFrame parent;
    private JScrollPane scrollPane;
    private JScrollPane tblScrollPane;
    private JTable table;
    private JComboBox<String> productSelectorCombo;
    private JPanel topPanel;
    private JComboBox<String> customerId;
    private JTextField customerName;
    private JTextField total;
    private JTextField discountPercent;
    private JTextField payableAmount;
    private JTextField loyaltyPoints;
    private JTextField redeemPoints;
    private JTextField amountPaid;
    private JTextField balance;
    private Vector<String> columnNames;
    private Map<String, Integer>columnIndices;
    private Vector<Vector<Object>> data;
    private JButton button;
    private JLabel msgLabel;
    private Timer timer;

    public ShoppingCartPanel (final SouvenirStore souvenirStore, JFrame parent) {
        this.souvenirStore = souvenirStore;
        this.parent = parent;

        /* Column Names */
        columnIndices = new HashMap<String, Integer>();
        columnIndices.put("id", 0);
        columnIndices.put("name", 1);
        columnIndices.put("qty", 2);
        columnIndices.put("unitPrice", 3);
        columnIndices.put("price", 4);
        columnIndices.put("button", 5);
        columnNames = new Vector<String>(Arrays.asList(new String[]{ "Product Id", "Product Name", "Qty", "Unit Price", "Price", "" }));
        data = new Vector<Vector<Object>>();

        setLayout(new BorderLayout(5, 5));

        button = new JButton("+");
        button.setSize(20, 20);

        customerId = getMemberSelectionCombo();
        customerName = new JTextField(10);
        msgLabel = new JLabel("");
        msgLabel.setVisible(false);
        total = new JTextField(2);
        discountPercent = new JTextField(2);
        payableAmount = new JTextField(2);
        loyaltyPoints = new JTextField(2);
        redeemPoints = new JTextField(2);
        amountPaid = new JTextField(2);
        balance = new JTextField(2);

        if(Session.getInstance(souvenirStore).getAttribute("ShoppingCart") != null){
        	this.shoppingCart = (ShoppingCart)Session.getInstance(souvenirStore).getAttribute("ShoppingCart");
        	
                customerId.setSelectedItem(this.shoppingCart.getCustomer().getId());
        	customerName.setText((this.shoppingCart.getMember()!=null)?this.shoppingCart.getMember().getName():"");
        	total.setText(""+this.shoppingCart.getTotalPriceBeforeDiscount());
        	discountPercent.setText((this.shoppingCart.getDiscount()!=null)?""+this.shoppingCart.getDiscount().getDiscPct():"");
        	loyaltyPoints.setText((this.shoppingCart.getMember()!=null)?""+this.shoppingCart.getMember().getLoyaltyPoints():"");
        	redeemPoints.setText((this.shoppingCart.getMember()!=null)?""+this.shoppingCart.getPoints():"");
        	payableAmount.setText(""+this.shoppingCart.getPayableAmount());
        	Iterator<Item> itr = this.shoppingCart.getItems().iterator();
        	Item item = null;
        	while(itr.hasNext()){
        		item = itr.next();
        		data.add(new Vector<Object>(Arrays.asList(new Object[]{ item.getProduct().getId(), item.getProduct().getName(), item.getQuantity(), item.getProduct().getPrice(), 
        				new BigDecimal(item.getProduct().getPrice()).multiply(new BigDecimal(item.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP), "x" })));
 
                /* Sample Data */
                /*
                data.add(new Vector<Object>(Arrays.asList(new Object[]{ "CLO/1", "Cloth 1", new Integer(5), new BigDecimal("10.90"), new BigDecimal("0.0"), "x" })));
                data.add(new Vector<Object>(Arrays.asList(new Object[]{ "MUG/2", "Mug 2", new Integer(2), new BigDecimal("4.50"), new BigDecimal("0.0"), "x" })));
                 */
        	}
        	
        	timer = new Timer(500, new ActionListener(){
        	    @Override
        	    public void actionPerformed(ActionEvent e){
        	        Thread thread = new Thread(new Runnable(){
        	            public void run(){
                	        msgLabel.setText("*Shopping Cart restored from session");
                	        msgLabel.setFont(new Font("Courier New", Font.ITALIC, 12));
                	        msgLabel.setForeground(Color.RED);
                	        msgLabel.setVisible(true);
                	        try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                	        msgLabel.setText("");
                	        msgLabel.setVisible(false);
                	        ShoppingCartPanel.this.timer.stop();
        	            }
        	        });
        	        thread.start();
        	    }
        	});
        	timer.start();
        }else{
        	this.shoppingCart = new ShoppingCart();
        	Session.getInstance(souvenirStore).setAttribute("ShoppingCart", this.shoppingCart);
        }
        /** New Record **/
        data.add(new Vector<Object>(Arrays.asList(new Object[]{"", "", "", new BigDecimal("0.0"), new BigDecimal("0.0"), "+"})));

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout( FlowLayout.LEFT, 5, 5));
        topPanel.add(new JLabel("Customer Id"));
        //Nikhil Metrani
        //Changed Focus Listener to ActionListener since we changed TextFiels to ConboBox
        customerId.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                String value = customerId.getSelectedItem().toString();
                Customer customer = null;
                if(value != null && !value.trim().isEmpty()){
                    try {
                        customer = souvenirStore.getMember(value);
                    }
                    catch (MemberNotFoundException mnfe) {
                        if(value != null && !value.trim().isEmpty() && !value.trim().equalsIgnoreCase("PUBLIC")){
                            JOptionPane.showMessageDialog(table.getParent(),"Member '" + value + "' not found. The Customer will be considered as PUBLIC", "Information", JOptionPane.OK_OPTION);
                        }
                        customer = new Customer();
                    }
                }

                shoppingCart.setCustomer(customer);
                if(shoppingCart.getMember() != null){
                    customerName.setText(shoppingCart.getMember().getName());
                    loyaltyPoints.setText(String.valueOf(shoppingCart.getMember().getLoyaltyPoints()));
                }else{
                    //Nikhil Metrani
                    //Lets reset customer name if public was selected in drop down
                    customerName.setText("");
                    loyaltyPoints.setText("");
                }

                double discount = shoppingCart.getHighestDiscount(souvenirStore.getDiscounts());
                if(shoppingCart.getDiscount()!=null){
                    discountPercent.setText(String.valueOf(shoppingCart.getDiscount().getDiscPct()));
                }else{
                	discountPercent.setText("");
                }
                
                calculatePayableAmount();
            }
        });
        topPanel.add(customerId);
        topPanel.add(new JLabel("Customer Name"));
        customerName.setEditable(false);
        topPanel.add(customerName);
        topPanel.add(msgLabel);

        table = new JTable(new MyTableModel(data, columnNames)){
            @Override
            public void editingStopped(ChangeEvent e){
                int editingRow = this.getEditingRow();
                int editingCol = this.getEditingColumn();
                super.editingStopped(e);
                if(editingCol==columnIndices.get("id") || editingCol==columnIndices.get("qty"))
                    table.setEditingRow(editingRow); 
            }
        };
        table.setPreferredScrollableViewportSize(new Dimension(450, 70));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor( String.class, new TextEditor(10));  //Editor for Text fields
        table.setDefaultEditor( Integer.class, new TextEditor(10));  //Editor for Integer fields
        table.getColumnModel().getColumn(columnIndices.get("button")).setCellRenderer(new ButtonRenderer());  //Button Renderer
        table.getColumnModel().getColumn(columnIndices.get("button")).setCellEditor(new ButtonEditor(new JCheckBox()));  //Button Editor
        table.setRowHeight(20);

        /**
         * Nikhil Metrani
         * Product selection logic to enable adding products to shopping cart
         * */
        this.productSelectorCombo = this.getProductSelectionCombo();
        TableColumn catColumn = this.table.getColumnModel().getColumn(0);
        catColumn.setCellEditor(new DefaultCellEditor(this.productSelectorCombo));
        
        /** Modify row selection on table row insert/delete **/
        table.getModel().addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged (TableModelEvent e) { 
                if(TableModelEvent.DELETE == e.getType()) {
                    table.changeSelection(table.getRowCount()-1, columnIndices.get("button"), false, false);
                }else if(TableModelEvent.INSERT == e.getType()){
                    if(table.getEditingRow()+1 < table.getRowCount())
                        table.changeSelection(table.getEditingRow()+1, columnIndices.get("button"), false, false);
                    else
                        table.changeSelection(table.getRowCount()-1, columnIndices.get("button"), false, false);
                }else if(TableModelEvent.UPDATE == e.getType()){
                    table.changeSelection(table.getEditingRow(), columnIndices.get("button"), false, false);
                    if(table.getEditingRow()+1 < table.getRowCount())
                        table.changeSelection(table.getEditingRow()+1, columnIndices.get("button"), false, false);
                }
                table.revalidate();
                table.repaint();
            }
        });

        // Create the scroll pane and add the table to it.
        tblScrollPane = new JScrollPane(table);

        /** Button action to add/remove a row **/
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent e) {
                int selectedRow = table.getEditingRow();
                int rowCount = table.getRowCount();
                MyTableModel model = (MyTableModel)table.getModel();
                if(selectedRow==rowCount-1){
                    String id = (String)((Vector)model.getDataVector().get(selectedRow)).get(columnIndices.get("id"));
                    Product product = null;
                    if(id!=null){
                        product = souvenirStore.getInventory().getProduct(id.toString());
                    }

                    if(product==null){
                        JOptionPane.showMessageDialog(table.getParent(),"Product not found. Please enter valid Product", "Error", JOptionPane.OK_OPTION);
                        table.changeSelection(selectedRow, columnIndices.get("id"), false, false);
                        return;
                    }

                    model.setValueAt(product.getName(), selectedRow, columnIndices.get("name"));
                    model.setValueAt(product.getPrice(), selectedRow, columnIndices.get("unitPrice"));
                    model.setValueAt(model.getValueAt(selectedRow, columnIndices.get("qty")), selectedRow, columnIndices.get("qty"));

                    model.fireTableRowsUpdated(selectedRow, selectedRow);

                    String qty = (String)model.getValueAt(selectedRow, columnIndices.get("qty"));
                    Float unitPrice = (Float)model.getValueAt(selectedRow, columnIndices.get("unitPrice"));
                    shoppingCart.addToCart(new Item(product, (qty==null)?0:Integer.parseInt(qty), (unitPrice==null)?0:unitPrice));
                    total.setText(String.valueOf(shoppingCart.getTotalPriceBeforeDiscount()));
                    calculatePayableAmount(); //Calculate payableAmount 

                    /** Adds a row **/
                    model.addRow(new Vector<Object>(Arrays.asList(new Object[]{"", "", "", new BigDecimal("0.0"), new BigDecimal("0.0"), "+"})));
                    model.fireTableRowsInserted(selectedRow, selectedRow+1);

                    /** Change the button text for the previous row**/
                    model.setValueAt("x", selectedRow, table.getColumnCount()-1);
                    model.fireTableCellUpdated(selectedRow, columnIndices.get("button"));

                    table.revalidate();
                    table.repaint();
                }else{
                    int retVal = JOptionPane.showConfirmDialog(table.getParent(),"Do you want to delete the item from the list?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                    if(retVal==0){
                        /** Delete a row **/
                        model.removeRow(selectedRow);
                        shoppingCart.removeFromCart(selectedRow);
                        total.setText(String.valueOf(shoppingCart.getTotalPriceBeforeDiscount()));
                        payableAmount.setText(String.valueOf(shoppingCart.calcFinalPmt())); 

                        /** To fix the button text, if the deleted row is last but one **/
                        if(selectedRow==rowCount-2){
                            model.setValueAt("+", selectedRow, table.getColumnCount()-1);
                            model.fireTableCellUpdated(selectedRow, columnIndices.get("button"));
                        }
                    }else{
                        /** To fix the button text, if the row chosen for deletion is last but one **/
                        if(selectedRow==rowCount-2){
                            model.setValueAt("x", selectedRow, table.getColumnCount()-1);
                            model.fireTableCellUpdated(selectedRow, columnIndices.get("button"));
                        }
                    }
                }
            }
        });

        redeemPoints.addFocusListener(new FocusListener() {
            @Override
            public void focusLost (FocusEvent e) {
            	String rPoints = redeemPoints.getText();
            	if(rPoints != null && !rPoints.trim().isEmpty() && !"0".equals(rPoints)){
            		try{
            			shoppingCart.setPoints(Integer.parseInt(rPoints));
            		}catch(Exception ex){
                        JOptionPane.showMessageDialog(table.getParent(),"Error validating redeem points->"+ex.getMessage(), "Error", JOptionPane.OK_OPTION);
                        redeemPoints.setText("");
                        shoppingCart.setPoints(0);
                        redeemPoints.requestFocusInWindow();
            		}
            	}else{
            		shoppingCart.setPoints(0);
            	}

            	calculatePayableAmount();
            }

            @Override
            public void focusGained(FocusEvent e){
            	
            }
        });

        amountPaid.addFocusListener(new FocusListener() {
            @Override
            public void focusLost (FocusEvent e) { 
            	calculatePayableAmount();
            }

            @Override
            public void focusGained(FocusEvent e){
            	
            }
        });

        add(topPanel, BorderLayout.PAGE_START);

        add("Center", tblScrollPane);

        scrollPane = new JScrollPane(this.createCalculationPanel());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add("East", this.scrollPane);

        add(this.createButtonPanel(), BorderLayout.SOUTH);

    }

    /** TableModel - Data container for table **/
    class MyTableModel extends DefaultTableModel {

        public MyTableModel(Vector<Vector<Object>> dataVector, Vector<String> columnNames){
            super(dataVector, columnNames);
        }

        public boolean isCellEditable (int row, int col) {
            // Note that the data/cell address is constant,
            // no matter where the cell appears onscreen.

        	/**
        	 * Nikhil Metrani
        	 * If product is already added, disable product selection
        	 * */
        	if ((col == columnIndices.get("id")) && ("x".equals(this.getValueAt(row, 5).toString()))) {
        		return false;
        	}
        			
            if(this.getDataVector().size()-1 == row){
                if (col== columnIndices.get("name") || (col > columnIndices.get("qty") && col < columnIndices.get("button"))) {
                    return false;
                } else {
                    return true;
                }
            }else{
                if(col == columnIndices.get("qty") || col == columnIndices.get("button")){
                    return true;
                }
            }
            return false;
        }

        public void setValueAt (Object aValue, int row, int col) {
            super.setValueAt(aValue, row, col);
            if(col==columnIndices.get("button")){
                button.setText(aValue.toString());
            }
            /** Multiply qty and unitPrice and set it to price on change of qty **/ 
            if((col==columnIndices.get("qty")) && aValue!=null && getValueAt(row, columnIndices.get("unitPrice"))!=null && !getValueAt(row, columnIndices.get("unitPrice")).toString().isEmpty()){
                super.setValueAt((new BigDecimal(getValueAt(row, columnIndices.get("unitPrice")).toString())).multiply(new BigDecimal(aValue.toString())), row, columnIndices.get("price"));
                if(row < table.getRowCount()-1){
                    shoppingCart.getItems().get(row).setQuantity(Integer.parseInt(aValue.toString()));
                    total.setText(String.valueOf(shoppingCart.getTotalPriceBeforeDiscount()));
                    calculatePayableAmount(); //Calculate payableAmount
                }
            }

            if(col==columnIndices.get("unitPrice") && aValue!=null && getValueAt(row, columnIndices.get("qty"))!=null && !getValueAt(row, columnIndices.get("qty")).toString().isEmpty()){
                super.setValueAt((new BigDecimal(aValue.toString())).multiply(new BigDecimal(getValueAt(row, columnIndices.get("qty")).toString())), row, columnIndices.get("price"));
                if(row < table.getRowCount()-1){
                    shoppingCart.getItems().get(row).setQuantity(Integer.parseInt(aValue.toString()));
                    total.setText(String.valueOf(shoppingCart.getTotalPriceBeforeDiscount()));
                    calculatePayableAmount(); //Calculate payableAmount
                }
            }

            if((col==columnIndices.get("id")) && aValue!=null){
                Product product = null;
                if(aValue!=null){
                    product = souvenirStore.getInventory().getProduct(aValue.toString());
                    if(product != null){
                        this.setValueAt(product.getName(), row, columnIndices.get("name"));
                        this.setValueAt(product.getPrice(), row, columnIndices.get("unitPrice"));
                        /**
                         * Nikhil Metrani
                         * While adding product to cart, let's add at least one quantity
                         * */
                        this.setValueAt("1", row, columnIndices.get("qty"));
                    }
                }
            }
        }
    }

    private JPanel createCalculationPanel() {
        JPanel p = new JPanel(new GridLayout(14, 1, 5, 5));

        p.add(new JLabel("Total"));
        total.setEditable(false);
        p.add(total);
        p.add(new JLabel("Discount %"));
        discountPercent.setEditable(false);
        p.add(discountPercent);
        p.add(new JLabel("Loyalty Points"));
        loyaltyPoints.setEditable(false);
        p.add(loyaltyPoints);
        p.add(new JLabel("Redeem Points"));
        p.add(redeemPoints);
        p.add(new JLabel("Payable Amount"));
        payableAmount.setEditable(false);
        p.add(payableAmount);
        p.add(new JLabel("Paid"));
        p.add(amountPaid);
        p.add(new JLabel("Balance"));
        balance.setEditable(false);
        p.add(balance);

        JPanel bp = new JPanel();
        bp.setLayout(new BorderLayout(5, 5));
        bp.add("North", p);
        return bp;
    }

    private JPanel createButtonPanel () {

        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton b = new JButton("Confirm");
        b.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                int retVal = JOptionPane.showConfirmDialog(table.getParent(),"Do you want to Confirm?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
                if(retVal==0){
	            	if(shoppingCart.getCustomer()==null){
	            		JOptionPane.showMessageDialog(table.getParent(),"Please select a Customer", "Error", JOptionPane.OK_OPTION);
	                    customerId.requestFocusInWindow();
	                    return;
	            	}
	            	
	            	if(shoppingCart.getItems().isEmpty()){
	            		JOptionPane.showMessageDialog(table.getParent(),"Please add an Item", "Error", JOptionPane.OK_OPTION);
	                    return;
	            	}

	            	if(calculatePayableAmount(true)){
	            		try{
	            			Transaction transaction = shoppingCart.confirmTransaction(souvenirStore);
	                		if(transaction == null){
	                			JOptionPane.showMessageDialog(table.getParent(),"Error confirming the transaction. Please contact System Administrator", "Error", JOptionPane.OK_OPTION);
	                            return;
	                		}
	                		Session.getInstance(souvenirStore).removeAttribute("ShoppingCart");
	                		
	                		//TO-DO: Print Receipt
	                		TransactionReceiptDialog trd = new TransactionReceiptDialog(transaction, shoppingCart);
	                		trd.setVisible(true);
	                		
	                		shoppingCart = null;
	                        ShoppingCartPanel.this.parent.setContentPane(new ShoppingCartPanel( souvenirStore, 
	                                ShoppingCartPanel.this.parent));
	                        ShoppingCartPanel.this.parent.repaint();
	                        ((StoreAppWindow)ShoppingCartPanel.this.parent).makeContentVisible();
	            		}catch(Exception ex){
	            			JOptionPane.showMessageDialog(table.getParent(),"Error confirming the transaction. Please contact System Administrator->"+ex.getMessage(), "Error", JOptionPane.OK_OPTION);
	            			return;
	            		}
	            	}
                }
            }
        });
        p.add(b);

        b = new JButton("Cancel");
        b.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                int retVal = JOptionPane.showConfirmDialog(table.getParent(),"Do you want to cancel the shopping cart?", "Cancel", JOptionPane.OK_CANCEL_OPTION);
                if(retVal==0){
                	Session.getInstance(souvenirStore).removeAttribute("ShoppingCart");
            		shoppingCart = null;
                    ShoppingCartPanel.this.parent.setContentPane(new ShoppingCartPanel( souvenirStore, 
                            ShoppingCartPanel.this.parent));
                    ShoppingCartPanel.this.parent.repaint();
                    ((StoreAppWindow)ShoppingCartPanel.this.parent).makeContentVisible();
                }
            }
        });
        p.add(b);

        b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                int retVal = JOptionPane.showConfirmDialog(table.getParent(),"Do you want to Close the shopping cart window?", "Close", JOptionPane.OK_CANCEL_OPTION);
                if(retVal==0){
	                ShoppingCartPanel.this.parent.setContentPane(new EmptyPanel(
	                        ShoppingCartPanel.this.parent));
	                ShoppingCartPanel.this.parent.repaint();
	                ((StoreAppWindow)ShoppingCartPanel.this.parent).makeContentVisible();
                }
            }
        });
        p.add(b);

        JPanel bp = new JPanel();
        bp.setLayout(new BorderLayout());
        bp.add("North", p);
        return bp;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setSize(20, 20);
            return this;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            if( ("x".equals(button.getText()) && table.getEditingRow() < table.getRowCount()-1) || 
                    ("+".equals(button.getText()) && table.getEditingRow() == table.getRowCount()-1)){
            }else{
                button.setText(label);                
            }
            button.setSize(20, 20);
            return button;
        }
        public Object getCellEditorValue() {
            return new String(label);
        }

        public boolean stopCellEditing(){
            if( ("x".equals(button.getText()) && table.getEditingRow() < table.getRowCount()-1) || 
                    ("+".equals(button.getText()) && table.getEditingRow() == table.getRowCount()-1)){
                return true;
            }else{
                return super.stopCellEditing();
            }
        }
    }

    private boolean calculatePayableAmount(){
    	return this.calculatePayableAmount(false);
    }

    private boolean calculatePayableAmount(boolean isConfirm){
        String strLPoints = loyaltyPoints.getText();
        String strRPoints = redeemPoints.getText();
        String strAmtPaid = amountPaid.getText();
        int lPoints = 0;
        int rPoints = 0;
        double amtPaid = 0;
        double payableAmt = 0;
        if(strRPoints != null && !strRPoints.trim().isEmpty()){
            if(shoppingCart.getMember()==null){
                //Nikhil Metrani
                //If points to redim is 0, validity checks are not required
                if (!"0".equals(redeemPoints.getText())) {
                    shoppingCart.setPoints(0);
                    JOptionPane.showMessageDialog(table.getParent(),"Non-Members cannot Redeem points", "Error", JOptionPane.OK_OPTION);
                    redeemPoints.requestFocusInWindow();
                    return false;
                }
            }
            if(strLPoints != null && !strLPoints.trim().isEmpty()){
                try{
                    lPoints = Integer.parseInt(strLPoints);
                    rPoints = Integer.parseInt(strRPoints);
                    //Nikhil Metrani
                    //If points to redim is 0, validity checks are not required
                    if ("0".equals(rPoints)) { 
                        if(lPoints < rPoints){
                            shoppingCart.setPoints(0);
                            JOptionPane.showMessageDialog(table.getParent(),"Points to be redeemed cannot be greater than available loyalty points", "Error", JOptionPane.OK_OPTION);
                            redeemPoints.requestFocusInWindow();
                            return false;
                        }else if (rPoints%100!=0){
                            shoppingCart.setPoints(0);
                            JOptionPane.showMessageDialog(table.getParent(), "Enter the redeem points in multiples of 100", "Error", JOptionPane.OK_OPTION);
                            redeemPoints.requestFocusInWindow();
                            return false;
                        }
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(table.getParent(),"Error validating redeem points->"+ex.getMessage(), "Error", JOptionPane.OK_OPTION);
                    redeemPoints.setText("");
                    shoppingCart.setPoints(0);
                    redeemPoints.requestFocusInWindow();
                    return false;
                }
            }
        }else{
        	shoppingCart.setPoints(0);
        }

        try{
            payableAmt = shoppingCart.getPayableAmount();
            payableAmount.setText(String.valueOf(payableAmt)); 

            if(strAmtPaid != null && !strAmtPaid.trim().isEmpty()){
                amtPaid = Double.parseDouble(strAmtPaid);
                if(amtPaid < payableAmt){
                    JOptionPane.showMessageDialog(table.getParent(),"Amount paid is less than the Payable amount", "Error", JOptionPane.OK_OPTION);
                    amountPaid.requestFocusInWindow();
                    return false;
                }else{
                	shoppingCart.setAmountPaid(amtPaid);
                	balance.setText(String.valueOf((new BigDecimal(amtPaid-payableAmt).setScale(2, BigDecimal.ROUND_HALF_UP))));
                }
            }else if(isConfirm){
            	JOptionPane.showMessageDialog(table.getParent(),"Please enter Amount paid", "Error", JOptionPane.OK_OPTION);
                amountPaid.requestFocusInWindow();
                return false;
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(table.getParent(),"Error validating Payable amount->"+ex.getMessage(), "Error", JOptionPane.OK_OPTION);
            amountPaid.requestFocusInWindow();
        }
        return true;
    }

    /**
     * Nikhil Metrani
     * Product selection logic to enable adding products to shopping cart
     * */
    private JComboBox<String> getProductSelectionCombo() {
    	
    	JComboBox<String> products = new JComboBox<String>();
    	
    	java.util.List<Product> prodList = this.souvenirStore.getInventory().getProducts();
    	
    	products.addItem(""); //Let's add a blank item
    	if (null != prodList) {
    		products.removeAll();
	    	Product prod = null;
	        Iterator<Product> i = prodList.iterator();
	        while (i.hasNext()) {
	        	prod = i.next();
	        	products.addItem(prod.getId());
	        }
    	}
    	return products;
    }
    
    /**
     * Nikhil Metrani
     * Customer selection logic to
     * */
    private JComboBox<String> getMemberSelectionCombo () {
    	
    	JComboBox<String> customers = new JComboBox();
    	
    	java.util.List<Customer> custList = this.souvenirStore.getMembers();
    	
    	customers.addItem("PUBLIC"); //Let's add a blank item
    	if (null != custList) {
            customers.removeAll();
            Customer cust;
            Iterator<Customer> i = custList.iterator();
            while (i.hasNext()) {
                cust = i.next();
                customers.addItem(cust.getId());
            }
    	}
    	return customers;
    }
}