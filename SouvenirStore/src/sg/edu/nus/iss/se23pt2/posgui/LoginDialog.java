package sg.edu.nus.iss.se23pt2.posgui;
import sg.edu.nus.iss.se23pt2.pos.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Nikhil Metrani
 *
 */

public class LoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textUserName;
	private JPasswordField textPassword;

	/**
	 * Create the dialog.
	 */
	public LoginDialog(Session session) {
		setModal(true);
		setLocationByPlatform(true);
		setTitle("Souvenir store login");
		setResizable(false);
		setBounds(100, 100, 340, 146);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUsername = new JLabel("Username:");
			lblUsername.setBounds(10, 11, 104, 14);
			contentPanel.add(lblUsername);
			lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		textUserName = new JTextField();
		textUserName.setBounds(124, 8, 200, 20);
		contentPanel.add(textUserName);
		textUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 36, 104, 14);
		contentPanel.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(124, 33, 200, 20);
		contentPanel.add(textPassword);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Login");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (session.authenticate(getUsername(), getPassword())) {
		                    JOptionPane.showMessageDialog(LoginDialog.this,
		                            getUsername() + "! You have successfully logged in.",
		                            "Login",
		                            JOptionPane.INFORMATION_MESSAGE);
		                    dispose();
		                } else {
		                    JOptionPane.showMessageDialog(LoginDialog.this,
		                            "Error :: Invalid username or password!",
		                            "Login",
		                            JOptionPane.ERROR_MESSAGE);
		                    // reset password
		                    textPassword.setText("");

		                }
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public String getUsername() {
        return textUserName.getText().trim();
    }

    public String getPassword() {
        return new String(textPassword.getPassword());
    }

}
