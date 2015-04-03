package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import sg.edu.nus.iss.se23pt2.pos.Session;
import sg.edu.nus.iss.se23pt2.pos.SouvenirStore;

/**
 * @author Nikhil Metrani
 *
 */
public class StoreAppWindow extends JFrame {

	//private JFrame storeAppWindow;
    private SouvenirStore store = null;
    private Session session = null;

	//Menu structure
	// System	|	Manage			|	Reports
    // Login		Inventory			Transactions
    // Logoff		--	Categories		Products below threshold
    //				--	Vendors
    //				--	Products
    //				Members
    //				Discounts
    JMenuBar menuBar = null;
    JMenu mnSystem = null;
    JMenuItem mnLogin = null;
    JMenuItem mnLogoff = null;

    JMenu mnManage = null;
    JMenu mnInventory = null;
    JMenuItem mntmCategories = null;
    JMenuItem mntmVendors = null;
    JMenuItem mntmProducts = null;
    JMenuItem mntmDiscounts = null;
    JMenuItem mntmMembers = null;
    JMenu mnReports = null;
    JMenuItem mntmTransactions = null;
    JMenuItem mntmProductBelowThreshold = null;
    JMenu mnTransaction = null;
    JMenuItem mntmBill = null;
    JMenuItem mntmStoreKeepers = null;

    private final String title = "Souvenir Store - SE23PT2";

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
        this.setBounds(100, 100, 700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        mnSystem = new JMenu("User");
        menuBar.add(mnSystem);

        mnLogin = new JMenuItem("Login");
        mnLogoff = new JMenuItem("Logoff");

        mnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (mnLogin.isEnabled()) {
                    StoreAppWindow.this.login();
                }
            }
        });
        mnSystem.add(mnLogin);

        mnLogoff.addActionListener(new ActionListener() {
            @Override
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
            @Override
            public void actionPerformed(ActionEvent arg0) {
                CategoryPanel panel = new CategoryPanel(StoreAppWindow.this.store.getInventory(), StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnInventory.add(mntmCategories);

        mntmVendors = new JMenuItem("Vendors");
        mntmVendors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                VendorPanel panel = new VendorPanel(StoreAppWindow.this.store.getInventory(), StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnInventory.add(mntmVendors);

        mntmProducts = new JMenuItem("Products");
        mntmProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ProductPanel panel = new ProductPanel(StoreAppWindow.this.store.getInventory(), StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnInventory.add(mntmProducts);

        mntmDiscounts = new JMenuItem("Discounts");
        mnManage.add(mntmDiscounts);
        mntmDiscounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DiscountPanel panel = new DiscountPanel(StoreAppWindow.this.store.getInventory(), StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnManage.add(mntmDiscounts);

        mntmMembers = new JMenuItem("Members");
        mntmMembers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberPanel panel = new MemberPanel(StoreAppWindow.this.store, StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnManage.add(mntmMembers);

        mnTransaction = new JMenu("Transaction");
        menuBar.add(mnTransaction);
        mntmBill = new JMenuItem("Billing");
        mntmBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ShoppingCartPanel panel = new ShoppingCartPanel(
                        StoreAppWindow.this.store, StoreAppWindow.this);
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnTransaction.add(mntmBill);

        mntmStoreKeepers = new JMenuItem("StoreKeepers");
        mntmStoreKeepers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                StoreKeeperPanel panel = new StoreKeeperPanel(StoreAppWindow.this.store, StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnManage.add(mntmStoreKeepers);

        menuBar.add(mnManage);

        mnReports = new JMenu("Reports");
        mntmTransactions = new JMenuItem("Transactions");
        mntmTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                TransactionPanel panel = new TransactionPanel(StoreAppWindow.this, StoreAppWindow.this.store);
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnReports.add(mntmTransactions);
        mntmProductBelowThreshold = new JMenuItem("Products below threshold quantity");
        mntmProductBelowThreshold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ProductOrderPanel panel = new ProductOrderPanel(StoreAppWindow.this.store.getInventory(), StoreAppWindow.this);
                panel.refresh();
                StoreAppWindow.this.setContentPane(panel);
                StoreAppWindow.this.makeContentVisible();
            }
        });
        mnReports.add(mntmProductBelowThreshold);

        menuBar.add(mnReports);

        this.login();
        if (this.session.isActive()) {
            this.activateSession();
        } else {
            this.deactivateSession();
        }

        this.setContentPane(new EmptyPanel(this));
    }

    public void updateUserInTitle() {
        if (session.isActive()) {
            this.setTitle(title + ": " + session.getLoggedInUser().getName());
        } else {
            this.setTitle(title);
        }
    }

    public boolean isSessionActive() {
        return this.session.isActive();
    }

    private void login() {
        session.authenticate();
        // if logon successfully
        if (session.isActive()) {
            activateSession();
        }
    }

    private void logoff() {
        if (session.logOff()) {
            deactivateSession();
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
        mntmStoreKeepers.setEnabled(true);
        mntmBill.setEnabled(true);
        this.updateUserInTitle();
    }

    private void deactivateSession() {
        this.setContentPane(new EmptyPanel(this));
        this.repaint();
        this.makeContentVisible();
        mnLogin.setEnabled(true);
        mnLogoff.setEnabled(false);
        mntmCategories.setEnabled(false);
        mntmDiscounts.setEnabled(false);
        mntmMembers.setEnabled(false);
        mntmProductBelowThreshold.setEnabled(false);
        mntmProducts.setEnabled(false);
        mntmTransactions.setEnabled(false);
        mntmVendors.setEnabled(false);
        mntmStoreKeepers.setEnabled(false);
        mntmBill.setEnabled(false);
        this.updateUserInTitle();
    }

    public void makeContentVisible() {
        this.getContentPane().setVisible(false);
        this.getContentPane().setVisible(true);
    }

    public static int confirm(String message) {
        Object[] options = {"OK", "CANCEL"};
        return JOptionPane.showOptionDialog(null, message, "Confirm",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }
}
