/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

//import sg.edu.nus.iss.se23pt2.pos.datastore.*;
//import sg.edu.nus.iss.se23pt2.pos.exception.*;
import sg.edu.nus.iss.se23pt2.posgui.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Nikhil Metrani
 *
 */
public class StoreApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		/*
		final JFrame frame = new JFrame("JDialog Demo");
        final JButton btnLogin = new JButton("Click to login");
 
        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        if (btnLogin.getText().equals("Log off")) {
                        	session.logOff();
                        	btnLogin.setText("Click to login");
                        }
                        else {
	                    	LoginDialog loginDlg = new LoginDialog(session);
	                        loginDlg.setVisible(true);
	                        // if logon successfully
	                        if(session.isActive()){
	                            btnLogin.setText("Hi " + session.getLoggedInUser().getName() + "!");
	                            btnLogin.setText("Log off");
	                        }
                        }
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
        */
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StoreAppWindow window = new StoreAppWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
