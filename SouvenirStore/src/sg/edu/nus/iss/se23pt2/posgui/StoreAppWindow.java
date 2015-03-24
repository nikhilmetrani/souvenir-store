package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		
		mnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mnLogin.isEnabled()) {
					StoreAppWindow.this.login();
				}
			}
		});
		mnSystem.add(mnLogin);
		
		mnLogoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mnLogoff.isEnabled()) {
					if (session.isActive()) {
						StoreAppWindow.this.logoff();
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
			    /*
				CategoryDialog cd = new CategoryDialog(StoreAppWindow.this.store.getInventory());
				cd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				cd.setLocationRelativeTo(StoreAppWindow.this);
				cd.setVisible(true);
				*/
			    JPanel panel = new CategoryPanel(StoreAppWindow.this.store.getInventory(), StoreAppWindow.this);
			    StoreAppWindow.this.setContentPane(panel);
                //StoreAppWindow.this.repaint();
                StoreAppWindow.this.setSize(650, 453);
                StoreAppWindow.this.setSize(651, 453);
			}
		});
		mnInventory.add(mntmCategories);
		
		mntmVendors = new JMenuItem("Vendors");
		mnInventory.add(mntmVendors);
		mntmProducts = new JMenuItem("Products");
		mntmProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProductDialog pd = new ProductDialog(StoreAppWindow.this.store.getInventory());
				pd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				pd.setLocationRelativeTo(StoreAppWindow.this);
				pd.setVisible(true);
			}
		});
		mnInventory.add(mntmProducts);
		
		mntmDiscounts = new JMenuItem("Discounts");
		mnManage.add(mntmDiscounts);
		mntmDiscounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DiscountDialog dd = new DiscountDialog(StoreAppWindow.this.store.getInventory());
				dd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dd.setLocationRelativeTo(StoreAppWindow.this);
				dd.setVisible(true);
			}
		});
		mnInventory.add(mntmDiscounts);
		
		mntmMembers = new JMenuItem("Members");
		mnManage.add(mntmMembers);
		menuBar.add(mnManage);
		
		mnReports = new JMenu("Reports");
		mntmTransactions = new JMenuItem("Transactions");
		mnReports.add(mntmTransactions);
		mntmProductBelowThreshold = new JMenuItem("Products below threshold quantity");
		mnReports.add(mntmProductBelowThreshold);
		
		menuBar.add(mnReports);
		
		this.login();
		if (this.session.isActive())
			this.activateSession();
		else
			this.deactivateSession();
		
		this.setContentPane(new EmptyPanel(this));
	}
	
	public void updateUserInTitle () {
		if (session.isActive()) {
			this.setTitle(title + ": " + session.getLoggedInUser().getName());
		}
		else {
			this.setTitle(title);
		}
	}
	
	public boolean isSessionActive() {
		return this.session.isActive();
	}
	
	private void login() {
		session.authenticate();
        // if logon successfully
        if(session.isActive()){
        	activateSession();	
        }
	}
	
	private void logoff() {
		if (session.logOff())
			deactivateSession();
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
