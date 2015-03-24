package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sg.edu.nus.iss.se23pt2.pos.StoreKeeper;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStore;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.CreationFailedException;

public class AddEditStoreKeepers extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	
	DataStoreFactory dsFactory = null;
    DataStore ds = null;    
    StoreKeeper storekeeper = null;
    private JTextField txtPassword;	

	/**
	 * Create the frame.
	 */
	public AddEditStoreKeepers() {
		setTitle("Add StoreKeeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 231);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(26, 34, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(26, 63, 56, 16);
		contentPane.add(lblPassword);
		
		txtName = new JTextField();
		txtName.setBounds(113, 31, 264, 22);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtName.getText() != "" && txtPassword.getText() != ""){
		        try {
		        	dsFactory = DataStoreFactory.getInstance();
					ds = dsFactory.getStoreKeeperDS();
					storekeeper = new StoreKeeper(txtName.getText(), txtPassword.getText());					
					ds.create(storekeeper);
					txtName.setText("");
					txtPassword.setText("");
		        }catch (CreationFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();					
				} catch (AccessDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
		btnSubmit.setBounds(115, 109, 97, 25);
		contentPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(280, 109, 97, 25);
		contentPane.add(btnCancel);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(113, 60, 258, 22);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
	}
}
