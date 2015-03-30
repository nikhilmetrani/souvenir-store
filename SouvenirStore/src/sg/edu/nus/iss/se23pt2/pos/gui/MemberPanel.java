package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

public class MemberPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final java.util.List<Member> members;
    private final JFrame parent;
    private final JScrollPane scrollPane;
    private final JTable table;
    private final MemberTableModel model;

    public MemberPanel(List<Member> members, JFrame parent) {
        this.members = members;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        this.model = new MemberTableModel(this.members);
        this.table = new JTable(this.model);

        this.table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (TableModelEvent.UPDATE == e.getType()) {
                    DataStoreFactory dsFactory = DataStoreFactory.getInstance();

                    try {
                        dsFactory.getMemberDS().update(MemberPanel.this.getSelected());
                    } catch (UpdateFailedException | IOException ufe) {
                        JOptionPane.showMessageDialog(null,
                                "Error :: " + ufe.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(this.table);
        add("North", new JLabel("Members"));
        add("Center", this.scrollPane);
        add("East", this.createButtonPanel());
    }

    public void refresh() {
        this.table.setVisible(false);
        this.table.setVisible(true);
    }

    public void select(int index) {
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        if ((index >= 0) && (this.model.size() > index)) {
            selectionModel.setSelectionInterval(index, index);
        }
    }

    public Member getSelected() {
        int idx = this.table.getSelectedRow();
        return (idx == -1) ? null : this.model.get(idx);
    }

    private JPanel createButtonPanel() {

        JPanel p = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton b = new JButton("Add");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMemberDialog d = new AddMemberDialog(MemberPanel.this.parent);
                d.setVisible(true);
                if (null != d.getAdded()) {
                    //VendorPanel.this.inventory.addVendor(selectedCat, d.getAdded());
                    MemberPanel.this.model.add(d.getAdded());
                    MemberPanel.this.refresh();
                    MemberPanel.this.select(MemberPanel.this.model.size() - 1);
                }
            }
        });
        p.add(b);

        b = new JButton("Remove");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null != MemberPanel.this.getSelected()) {
                    if (0 == StoreAppWindow.confirm("Are you sure you want to remove selected member?\nClick Ok to continue.")) {
                        int index = MemberPanel.this.table.getSelectedRow();

                        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
                        try {
                            dsFactory.getMemberDS().remove(MemberPanel.this.getSelected());
                            MemberPanel.this.members.remove(MemberPanel.this.getSelected());
                            MemberPanel.this.model.remove(MemberPanel.this.getSelected());
                            MemberPanel.this.refresh();
                            if (1 <= index) {
                                index -= 1;
                                MemberPanel.this.select(index);
                            } else {
                                if (MemberPanel.this.model.size() >= 1) {
                                    MemberPanel.this.select(0);
                                }
                            }
                        } catch (RemoveFailedException | IOException rfe) {
                            JOptionPane.showMessageDialog(null,
                                    "Error :: " + rfe.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        p.add(b);

        b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberPanel.this.parent.setContentPane(new EmptyPanel(MemberPanel.this.parent));
                MemberPanel.this.parent.repaint();
            }
        });
        p.add(b);

        JPanel bp = new JPanel();
        bp.setLayout(new BorderLayout());
        bp.add("North", p);
        return bp;
    }

}
