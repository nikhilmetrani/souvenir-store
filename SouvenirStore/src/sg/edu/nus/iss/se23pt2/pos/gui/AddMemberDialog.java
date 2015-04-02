package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import sg.edu.nus.iss.se23pt2.pos.Member;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class AddMemberDialog extends OkCancelDialog {

    private static final long serialVersionUID = 1L;

    private Member member; 
    private JTextField memberIdField;
    private JTextField memberNameField;
    private JTextField loyaltyPointsField;
    private ArrayList<Member> members;

    public AddMemberDialog (JFrame parent, List<Member> members2) {
        super(parent, "Add Member");
        this.members = (ArrayList<Member>) members2;
        this.member = null;
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
        loyaltyPointsField.setEditable(false);
    }
    @Override
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
        loyaltyPointsField.setText("-1");
        p.add (loyaltyPointsField);
        ((AbstractDocument)loyaltyPointsField.getDocument()).setDocumentFilter(
                new LoyaltyPointsFilter()); 
        return p;
    }

    protected boolean performOkAction () {
        String id = this.memberIdField.getText().toUpperCase();
        String name = this.memberNameField.getText();
        if ((0 == id.length()) || (0 == name.length())) {
            return false;
        }
        this.member = new Member(memberNameField.getText(), memberIdField.getText());
        this.member.addLoyaltyPoints(Integer.parseInt(this.loyaltyPointsField.getText()));
        
        if(memberIdField.getText().equalsIgnoreCase("public")) {
        	JOptionPane.showMessageDialog(null,
                    "Error :: " + "Member ID already exists",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        for (Member m: members) {
        	if(memberIdField.getText().equalsIgnoreCase(m.getId())) {
            	JOptionPane.showMessageDialog(null,
                        "Error :: " + "Member ID already exists",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            	return false;
            }
        }
        
        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
        try {
                dsFactory.getMemberDS().update(this.member);
                return true;
        }
        catch (UpdateFailedException | IOException ufe) {
                JOptionPane.showMessageDialog(null,
                "Error :: " + ufe.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
                this.member = null;
                return false;
        }
    }

    public Member getAdded() {
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