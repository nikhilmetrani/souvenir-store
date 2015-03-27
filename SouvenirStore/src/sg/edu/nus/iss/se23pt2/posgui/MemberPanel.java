package sg.edu.nus.iss.se23pt2.posgui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MemberPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
    private java.util.List<Member> 	members;
    private java.awt.List          	memberList;
    private JFrame						parent;
    private JScrollPane 				scrollPane;
    
    public MemberPanel (List<Member> members, JFrame parent) {
    	this.members = members;
        this.parent = parent;
        setLayout (new BorderLayout(5,5));
        this.memberList = new java.awt.List();
        this.memberList.setMultipleMode(false);
        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.memberList);
        add ("North", new JLabel ("Members"));
        add ("Center", this.memberList);
        add ("East", this.createButtonPanel());
    }

    public void refresh () {
        memberList.removeAll();
        Member mem = null;
        Iterator<Member> i = members.iterator();
        while (i.hasNext()) {
        	mem = i.next();
            memberList.add(mem.getName() + ", " + mem.getId() + ", " + mem.getLoyaltyPoints());
        }
    }

    public Member getSelectedMember () {
        int idx = memberList.getSelectedIndex();
        return (idx == -1) ? null : members.get(idx);
    }

    private JPanel createButtonPanel () {

        JPanel p = new JPanel (new GridLayout (0, 1, 5, 5));

        JButton b = new JButton ("Add");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                AddEditMemberDialog d = new AddEditMemberDialog(MemberPanel.this.members, MemberPanel.this.parent);
                d.setVisible (true);
                if (null != d.getMember()) {
                	MemberPanel.this.refresh();
                }
            }
        });
        p.add (b);

        b = new JButton ("Remove");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	if (null != MemberPanel.this.getSelectedMember()) {
            		int index = MemberPanel.this.memberList.getSelectedIndex();
	                members.remove(MemberPanel.this.getSelectedMember());
	                MemberPanel.this.refresh();
	            	if (1 <= index) {
	            		index -= 1;
	            	}
	            	else {
	            	}
            	}
            }
        });
        p.add (b);

        b = new JButton ("Edit");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
            	if (null != MemberPanel.this.getSelectedMember()) {
	            	AddEditMemberDialog d = new AddEditMemberDialog(MemberPanel.this.getSelectedMember(), MemberPanel.this.parent);
	            	d.setVisible (true);
	            	if (null != d.getMember()) {
	                	MemberPanel.this.refresh();
	                }
            	}
            }
        });
        p.add (b);

        b = new JButton ("Close");
        b.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                MemberPanel.this.parent.setContentPane(new EmptyPanel(MemberPanel.this.parent));
                MemberPanel.this.parent.repaint();
            }
        });
        p.add (b);

        JPanel bp = new JPanel ();
        bp.setLayout (new BorderLayout());
        bp.add ("North", p);
        return bp;
    }

}