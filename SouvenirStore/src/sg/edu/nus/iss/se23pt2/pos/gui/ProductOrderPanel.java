package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import sg.edu.nus.iss.se23pt2.pos.Category;
import sg.edu.nus.iss.se23pt2.pos.Inventory;
import sg.edu.nus.iss.se23pt2.pos.Product;

/**
 *
 * @author Nikhil Metrani UI to view products below threshold quantity and
 * replenish stocks
 */
public class ProductOrderPanel extends ProductPanel {

    private JComboBox<String> categoryCombo;
    protected ProductOrderTableModel model;

    public ProductOrderPanel(Inventory inventory, JFrame parent) {
        super(inventory, parent);
    }

    @Override
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        //Initialize Choice for user to select category
        this.categoryCombo = new JComboBox();
        this.loadCategories();

        this.model = new ProductOrderTableModel(this.inventory.getProductsBelowThresholdQuantity());
        this.table = new JTable(model);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Add the listener for categoryCombo
        this.categoryCombo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ProductOrderPanel.this.showProducts();
            }
        });

        this.scrollPane = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.scrollPane.setViewportView(this.table);

        //JPanel for Category
        JPanel c = new JPanel();
        c.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.setLayout(new FlowLayout(FlowLayout.LEADING));
        c.add(new JLabel("Please choose category:"));
        c.add(this.categoryCombo);

        //JPanel for Product
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        p.add("North", new JLabel("Products"));
        p.add("Center", this.scrollPane);
        p.add("East", createButtonPanel());

        add("North", c);
        add("Center", p);
    }

    private void showProducts() {
        List<Product> products = ("all categories".equals(this.getSelectedCategory().toLowerCase()))
                ? this.inventory.getProductsBelowThresholdQuantity()
                : this.inventory.getProductsBelowThresholdQuantity(this.getSelectedCategory());
        // null check required
        if (null == products) {
            products = new ArrayList();
        }
        this.model = new ProductOrderTableModel(products);
        this.table = new JTable(this.model);
        this.scrollPane.setViewportView(this.table);
        this.refresh();
    }

    public String getSelectedCategory() {
        int idx = categoryCombo.getSelectedIndex();
        return (idx == -1) ? null : categoryCombo.getSelectedItem().toString();
    }

    private void loadCategories() {
        java.util.List<Category> catList = inventory.getCategories();

        if (null != catList) {
            this.categoryCombo.removeAll();
            Category cat = null;
            Iterator<Category> i = catList.iterator();
            this.categoryCombo.addItem("All categories");
            while (i.hasNext()) {
                cat = i.next();
                this.categoryCombo.addItem(cat.getCode());
            }
            if (0 < this.categoryCombo.getItemCount()) {
                this.categoryCombo.setSelectedIndex(0);
            }
        }
    }

    @Override
    protected JPanel createButtonPanel() {

        JPanel p = new JPanel(new GridLayout(0, 1, 5, 5));
        p.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton b = new JButton("Replenish stock");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Info :: This functionality is not yet implemented",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(b);

        b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductOrderPanel.this.parent.setContentPane(new EmptyPanel(ProductOrderPanel.this.parent));
                ProductOrderPanel.this.parent.repaint();
            }
        });
        p.add(b);

        JPanel bp = new JPanel();
        bp.setLayout(new BorderLayout());
        bp.add("North", p);
        return bp;
    }
}
