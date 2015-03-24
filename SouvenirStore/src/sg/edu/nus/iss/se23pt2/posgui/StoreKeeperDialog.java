package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class StoreKeeperDialog extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public StoreKeeperDialog() {
		setTitle("Manage StoreKeepers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEditStoreKeepers frame = new AddEditStoreKeepers();
				frame.setVisible(true);
			}
		});
		btnAdd.setBounds(378, 66, 97, 25);
		contentPane.add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEditStoreKeepers frame = new AddEditStoreKeepers();
				frame.setVisible(true);
			}
		});
		btnEdit.setBounds(378, 104, 97, 25);
		contentPane.add(btnEdit);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(26, 67, 324, 358);
		contentPane.add(textArea);
		
		JLabel lblStoreKeepers = new JLabel("Store Keepers");
		lblStoreKeepers.setBounds(26, 38, 140, 16);
		contentPane.add(lblStoreKeepers);
	}
}
