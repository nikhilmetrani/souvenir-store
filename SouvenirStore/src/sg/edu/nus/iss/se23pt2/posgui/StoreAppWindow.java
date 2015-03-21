package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Nikhil Metrani
 *
 */

public class StoreAppWindow extends JFrame {

	//private JFrame storeAppWindow;

	private SouvenirStore	store = null;
	private Session 		session = null;
	
	//Menu structure
	
	// System	|	Manage			|	Reports
	// Login		Inventory			Transactions
	// Logoff		--	Categories		Products below threshold
	//				--	Vendors
	//				--	Products
	//				Members
	//				Discounts
	
	JMenuBar 				menuBar = null;
	JMenu 					mnSystem = null;
	JMenuItem 				mnLogin = null;
	JMenuItem 				mnLogoff = null;
	
	JMenu 					mnManage = null;
	JMenu 					mnInventory = null;
	JMenuItem 				mntmCategories = null;
	JMenuItem 				mntmVendors = null;
	JMenuItem 				mntmProducts = null;
	JMenuItem 				mntmDiscounts = null;
	JMenuItem 				mntmMembers = null;
	JMenu 					mnReports = null;
	JMenuItem 				mntmTransactions = null;
	JMenuItem 				mntmProductBelowThreshold = null;
	
	private String title = "Souvenir Store - SE23PT2";
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the application.
	 */
	public StoreAppWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.store = new SouvenirStore();
		store.loadData();
		
		this.session = Session.getInstance(store);
		
		//storeAppWindow = new JFrame();
		this.setTitle(title);
		this.setBounds(100, 100, 651, 453);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		mnSystem = new JMenu("System");
		menuBar.add(mnSystem);
		
		mnLogin = new JMenuItem("Login");
		mnLogoff = new JMenuItem("Logoff");
		mnLogin.setEnabled(true);
		mnLogoff.setEnabled(false);
		
		mnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mnLogin.isEnabled()) {
					LoginDialog loginDlg = new LoginDialog(session);
					loginDlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					loginDlg.setLocationRelativeTo(StoreAppWindow.this);
	                loginDlg.setVisible(true);
	                // if logon successfully
	                if(session.isActive()){
	                    mnLogin.setEnabled(false);
	                    mnLogoff.setEnabled(true);
	                }
	                activateSession();
				}
			}
		});
		mnSystem.add(mnLogin);
		
		mnLogoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mnLogoff.isEnabled()) {
					if (session.isActive()) {
						Object[] options = { "OK", "CANCEL" };
						int n = JOptionPane.showOptionDialog(StoreAppWindow.this, "Are you sure you want to log off?\n\nClick OK to continue.", "Confirm",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
							null, options, options[0]);
						if (0 == n)
						{
							session.logOff();
							mnLogin.setEnabled(true);
							mnLogoff.setEnabled(false);
							JOptionPane.showMessageDialog(StoreAppWindow.this,
		                            "You have been successfully logged off.",
		                            "Login",
		                            JOptionPane.INFORMATION_MESSAGE);
							deactivateSession();
						}
					}
				}
			}
		});
		mnSystem.add(mnLogoff);
		
		mnManage = new JMenu("Manage");
		
		mnInventory = new JMenu("Inventory");
		mnManage.add(mnInventory);
		
		mntmCategories = new JMenuItem("Categories");
		mntmCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CategoryDialog cd = new CategoryDialog(StoreAppWindow.this.store.getInventory());
				cd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				cd.setLocationRelativeTo(StoreAppWindow.this);
				cd.setVisible(true);
			}
		});
		mnInventory.add(mntmCategories);
		
		mntmVendors = new JMenuItem("Vendors");
		mnInventory.add(mntmVendors);
		mntmProducts = new JMenuItem("Products");
		mnInventory.add(mntmProducts);
		
		mntmDiscounts = new JMenuItem("Discounts");
		mnManage.add(mntmDiscounts);
		mntmMembers = new JMenuItem("Members");
		mnManage.add(mntmMembers);
		
		menuBar.add(mnManage);
		
		mnReports = new JMenu("Reports");
		mntmTransactions = new JMenuItem("Transactions");
		mnReports.add(mntmTransactions);
		mntmProductBelowThreshold = new JMenuItem("Products below threshold quantity");
		mnReports.add(mntmProductBelowThreshold);
		
		menuBar.add(mnReports);
		
		this.deactivateSession();
	}
	
	public void updateUserInTitle () {
		if (session.isActive()) {
			this.setTitle(title + ": " + session.getLoggedInUser().getName());
		}
		else {
			this.setTitle(title);
		}
	}
	
	private void activateSession() {
		mnLogin.setEnabled(false);
        mnLogoff.setEnabled(true);
        mntmCategories.setEnabled(true);
        mntmDiscounts.setEnabled(true);
        mntmMembers.setEnabled(true);
        mntmProductBelowThreshold.setEnabled(true);
        mntmProducts.setEnabled(true);
        mntmTransactions.setEnabled(true);
        mntmVendors.setEnabled(true);
        this.updateUserInTitle ();
	}
	
	private void deactivateSession() {
		mnLogin.setEnabled(true);
        mnLogoff.setEnabled(false);
        mntmCategories.setEnabled(false);
        mntmDiscounts.setEnabled(false);
        mntmMembers.setEnabled(false);
        mntmProductBelowThreshold.setEnabled(false);
        mntmProducts.setEnabled(false);
        mntmTransactions.setEnabled(false);
        mntmVendors.setEnabled(false);
        this.updateUserInTitle ();
	}
}
