package sg.edu.nus.iss.se23pt2.posgui;


import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sg.edu.nus.iss.se23pt2.pos.*;

public class MemberDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.util.List<Member> members = null;
	private JFrame parent = null;
	private MemberPanel     memPanel;
	
	/**
	 * Create the dialog.
	 */
	public MemberDialog(ArrayList<Member> members) {
		
		this.members = members;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Manage members");
		setBounds(100, 100, 287, 310);
		getContentPane().setLayout(null);
		
		memPanel = new MemberPanel(members, this);
        memPanel.refresh();
		Panel p = new Panel ();
        p.setLayout (new GridLayout(0, 1));
        p.add (memPanel);
		
        p.setBounds(5, 10, 270, 260);
		getContentPane().add(p);

	}
}
