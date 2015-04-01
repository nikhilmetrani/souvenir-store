package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

public class ProductPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Inventory inventory;
    private final JScrollPane scrollPane;
    private final JFrame parent;
    private final JTable table;
    private final ProductTableModel model;

    public ProductPanel(Inventory inventory, JFrame parent) {
        this.inventory = inventory;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        //this.scrollPane = new JScrollPane();
        this.model = new ProductTableModel(this.inventory.getProducts());
        this.table = new JTable(model);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Dimension tableSize = new Dimension(900, 400);
        this.table.setPreferredSize(tableSize);

        this.setTableHeadersWidth();

        this.table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (TableModelEvent.UPDATE == e.getType()) {
                    DataStoreFactory dsFactory = DataStoreFactory.getInstance();

                    try {
                        dsFactory.getProductDS().update(ProductPanel.this.getSelected());
                    } catch (UpdateFailedException | IOException ufe) {
                        JOptionPane.showMessageDialog(null,
                                "Error :: " + ufe.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //this.scrollPane.setViewportView(this.table);
        //this.table.setFillsViewportHeight(true);
        this.scrollPane = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        add("North", new JLabel("Products"));
        add("Center", this.scrollPane);
        add("East", createButtonPanel());
    }

    public void setTableHeadersWidth() {
        TableColumn column = null;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(25);
                case 1:
                    column.setPreferredWidth(50);
                case 2:
                    column.setPreferredWidth(150);
                case 3:
                    column.setPreferredWidth(25);
                case 4:
                    column.setPreferredWidth(25);
                case 5:
                    column.setPreferredWidth(25);
                case 6:
                    column.setPreferredWidth(25);
                case 7:
                    column.setPreferredWidth(50);
            }
        }
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

    public Product getSelected() {
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
                AddProductDialog d = new AddProductDialog(ProductPanel.this.inventory, ProductPanel.this.parent);
                d.setVisible(true);
                if (null != d.getAdded()) {
                    ProductPanel.this.inventory.addProduct(d.getAdded());
                    ProductPanel.this.model.add(d.getAdded());
                    ProductPanel.this.refresh();
                    ProductPanel.this.select(ProductPanel.this.model.size() - 1);
                }
            }
        });
        p.add(b);
/*
        b = new JButton("Remove");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null != ProductPanel.this.getSelected()) {
                    if (0 == StoreAppWindow.confirm("Are you sure you want to remove selected product?\nClick Ok to continue.")) {
                        int index = ProductPanel.this.table.getSelectedRow();

                        DataStoreFactory dsFactory = DataStoreFactory.getInstance();
                        try {
                            dsFactory.getProductDS().remove(ProductPanel.this.getSelected());
                            ProductPanel.this.inventory.removeProduct(ProductPanel.this.getSelected().getId());
                            ProductPanel.this.model.remove(ProductPanel.this.getSelected());
                            ProductPanel.this.refresh();
                            if (1 <= index) {
                                index -= 1;
                                ProductPanel.this.select(index);
                            } else {
                                if (ProductPanel.this.model.size() >= 1) {
                                    ProductPanel.this.select(0);
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
                ProductPanel.this.parent.setContentPane(new EmptyPanel(ProductPanel.this.parent));
                ProductPanel.this.parent.repaint();
            }
        });
        p.add(b);

        JPanel bp = new JPanel();
        bp.setLayout(new BorderLayout());
        bp.add("North", p);
        return bp;
    }

}
