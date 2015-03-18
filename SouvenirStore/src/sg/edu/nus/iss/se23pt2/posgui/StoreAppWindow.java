package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.EventQueue;

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

	private SouvenirStore store = null;
	private Session session = null;
	
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
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnSystem = new JMenu("System");
		menuBar.add(mnSystem);
		
		JMenuItem mnLogin = new JMenuItem("Login");
		JMenuItem mnLogoff = new JMenuItem("Logoff");
		mnLogin.setEnabled(true);
		mnLogoff.setEnabled(false);
		
		mnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mnLogin.isEnabled()) {
					LoginDialog loginDlg = new LoginDialog(session);
					loginDlg.setLocationRelativeTo(StoreAppWindow.this);
	                loginDlg.setVisible(true);
	                // if logon successfully
	                if(session.isActive()){
	                    mnLogin.setEnabled(false);
	                    mnLogoff.setEnabled(true);
	                    
	                }
	                updateUserInTitle();
				}
			}
		});
		mnSystem.add(mnLogin);
		
		mnLogoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mnLogoff.isEnabled()) {
					if (session.isActive()) {
						session.logOff();
						mnLogin.setEnabled(true);
						mnLogoff.setEnabled(false);
						JOptionPane.showMessageDialog(StoreAppWindow.this,
	                            "You have been successfully logged off.",
	                            "Login",
	                            JOptionPane.INFORMATION_MESSAGE);
						updateUserInTitle();
					}
				}
			}
		});
		mnSystem.add(mnLogoff);
	}
	
	public void updateUserInTitle () {
		if (session.isActive()) {
			this.setTitle(title + ": " + session.getLoggedInUser().getName());
		}
		else {
			this.setTitle(title);
		}
	}
}
