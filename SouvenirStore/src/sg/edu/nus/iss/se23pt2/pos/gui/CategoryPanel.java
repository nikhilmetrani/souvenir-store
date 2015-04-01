package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.RemoveFailedException;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class CategoryPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Inventory inventory;
    private final JFrame parent;
    private final JScrollPane scrollPane;
    private final JTable table;
    private final CategoryTableModel model;

    public CategoryPanel(Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout(new BorderLayout(5, 5));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        this.scrollPane = new JScrollPane();
        this.model = new CategoryTableModel(this.inventory.getCategories());
        this.table = new JTable(model);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (TableModelEvent.UPDATE == e.getType()) {
                    DataStoreFactory dsFactory = DataStoreFactory.getInstance();

                    try {
                        dsFactory.getCategoryDS().update(CategoryPanel.this.getSelected());
                    } catch (UpdateFailedException | IOException ufe) {
                        JOptionPane.showMessageDialog(null,
                                "Error :: " + ufe.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.scrollPane.setViewportView(this.table);

        add("North", new JLabel("Categories"));
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

    public Category getSelected() {
        int idx = this.table.getSelectedRow();
        return (idx == -1) ? null : this.model.get(idx);
    }

    private JPanel createButtonPanel() {

        JPanel p = new JPanel(new GridLayout(0, 1, 5, 5));
        p.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton b = new JButton("Add");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCategoryDialog d = new AddCategoryDialog(CategoryPanel.this.parent);
                d.setVisible(true);
                if (null != d.getAdded()) {
                    CategoryPanel.this.inventory.addCategory(d.getAdded());
                    CategoryPanel.this.model.add(d.getAdded());
                    CategoryPanel.this.refresh();
                    CategoryPanel.this.select(CategoryPanel.this.model.size() - 1);
                }
            }
        });
        p.add(b);
/*
        b = new JButton("Remove");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null != CategoryPanel.this.getSelected()) {
                    if (0 == StoreAppWindow.confirm("Are you sure you want to remove selected category?\nClick Ok to continue.")) {
                        int index = CategoryPanel.this.table.getSelectedRow();

                        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
                        try {
                            dsFactory.getCategoryDS().remove(CategoryPanel.this.getSelected());
                            CategoryPanel.this.inventory.removeCategory(CategoryPanel.this.getSelected().getCode());
                            CategoryPanel.this.model.remove(CategoryPanel.this.getSelected());
                            CategoryPanel.this.refresh();
                            if (1 <= index) {
                                index -= 1;
                                CategoryPanel.this.select(index);
                            } else {
                                if (CategoryPanel.this.model.size() >= 1) {
                                    CategoryPanel.this.select(0);
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
*/
        b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoryPanel.this.parent.setContentPane(new EmptyPanel(CategoryPanel.this.parent));
                CategoryPanel.this.parent.repaint();
            }
        });
        p.add(b);

        JPanel bp = new JPanel();
        bp.setLayout(new BorderLayout());
        bp.add("North", p);
        return bp;
    }

}
