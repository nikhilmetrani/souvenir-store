package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import sg.edu.nus.iss.se23pt2.pos.Member;

public class AddEditMemberDialog extends OkCancelDialog {

		private static final long serialVersionUID = 1L;
		
		private java.util.List<Member> members;
		private Member member; 
	    private JTextField memberIdField;
	    private JTextField memberNameField;
	    private JTextField loyaltyPointsField;
	    private boolean editMode = false;

	    public AddEditMemberDialog (java.util.List<Member> members, JFrame parent) {
	        super(parent, "Add Member");
	        this.members = members;
	        this.member = null;
	        this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
        	loyaltyPointsField.setEditable(false);
	    }
	    
	    public AddEditMemberDialog (Member member, JFrame parent) {
	    	super(parent, "Edit Member");
	    	this.editMode = true;
	        this.members = null;
	        this.member = member;
	        memberIdField.setText(this.member.getId());
			memberNameField.setText(this.member.getName());
			if(this.member.getLoyaltyPoints() == -1) loyaltyPointsField.setText("0");
			else loyaltyPointsField.setText(this.member.getLoyaltyPoints() + "");
			this.setLocationRelativeTo(parent);
	        this.setModal(true);
	        this.pack();
	    }

	    protected JPanel createFormPanel () {
	        JPanel p = new JPanel ();
	        p.setLayout (new GridLayout (0, 2));
	        p.add (new JLabel ("Member name:"));
	        memberNameField = new JTextField (20);
	        p.add (memberNameField);
	        p.add (new JLabel ("Member id:"));
	        memberIdField = new JTextField (20);
	        p.add (memberIdField);
	        p.add (new JLabel ("Loyalty points:"));
	        loyaltyPointsField = new JTextField (20);
	        p.add (loyaltyPointsField);
	        ((AbstractDocument)loyaltyPointsField.getDocument()).setDocumentFilter(
	                new LoyaltyPointsFilter()); 
	        return p;
	    }

	    protected boolean performOkAction () {
	    	if (this.editMode) {
	    		String id = this.memberIdField.getText();
		        String name = this.memberNameField.getText();
		        String loyaltyPoints = this.loyaltyPointsField.getText();
		        if ((0 == id.length()) || (0 == name.length()) || (0 == loyaltyPoints.length())) {
		            return false;
		        }
		        this.member.setId(id);
		        this.member.setName(name);
		        this.member.deductLoyaltyPoints(this.member.getLoyaltyPoints());
		        this.member.addLoyaltyPoints(Integer.valueOf(loyaltyPoints));
	    	}
	    	else {
		        String id = this.memberIdField.getText().toUpperCase();
		        String name = this.memberNameField.getText();
		        if ((0 == id.length()) || (0 == name.length())) {
		            return false;
		        }
		        this.member = new Member(memberNameField.getText(), memberIdField.getText());
		        this.member.addLoyaltyPoints(Integer.valueOf(-1));
		        this.members.add(this.member);
	    	}
	        return true;
	    }
	    
	    public Member getMember() {
	    	return this.member;
	    }
	    
	    class LoyaltyPointsFilter extends DocumentFilter
	    {   
	        @Override
	        public void insertString(DocumentFilter.FilterBypass fp
	                , int offset, String string, AttributeSet aset)
	                                    throws BadLocationException
	        {
	            int len = string.length();
	            boolean isValidInteger = true;

	            for (int i = 0; i < len; i++)
	            {
	                if (!Character.isDigit(string.charAt(i)))
	                {
	                    isValidInteger = false;
	                    break;
	                }
	            }
	            if (isValidInteger)
	                super.insertString(fp, offset, string, aset);
	            else
	                Toolkit.getDefaultToolkit().beep();
	        }

	        @Override
	        public void replace(DocumentFilter.FilterBypass fp, int offset
	                        , int length, String string, AttributeSet aset)
	                                            throws BadLocationException
	        {
	            int len = string.length();
	            boolean isValidInteger = true;

	            for (int i = 0; i < len; i++)
	            {
	                if (!Character.isDigit(string.charAt(i)))
	                {
	                    isValidInteger = false;
	                    break;
	                }
	            }
	            if (isValidInteger)
	                super.replace(fp, offset, length, string, aset);
	            else
	                Toolkit.getDefaultToolkit().beep();
	        }
	    }

	}