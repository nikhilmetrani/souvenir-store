package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import sg.edu.nus.iss.se23pt2.pos.exception.InvalidCategoryCodeException;

/**
 * Author: Nikhil Metrani Class: AddCategoryDialog
 *
 */
public class AddCategoryDialog extends OkCancelDialog {

    private static final long serialVersionUID = 1L;

    private Category category = null;
    private JTextField categoryCodeField;
    private JTextField categoryNameField;
    private final Inventory inventory;

    public AddCategoryDialog(Inventory inventory, JFrame parent) {
        super(parent, "Add Category");
        this.inventory = inventory;
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
        this.category = null;
    }

    @Override
    protected JPanel createFormPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(0, 2));
        p.add(new JLabel("Category code:"));
        categoryCodeField = new JTextField(20);
        p.add(categoryCodeField);
        p.add(new JLabel("Category name:"));
        categoryNameField = new JTextField(20);
        p.add(categoryNameField);
        return p;
    }

    @Override
    protected boolean performOkAction() {
        String code = this.categoryCodeField.getText().toUpperCase();
        String name = this.categoryNameField.getText();
        try {
            this.category = new Category(code, name);
            try {
                this.inventory.getCategory(code);
                JOptionPane.showMessageDialog(null,
                        "Error :: The category " + code + " already exists",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                this.category = null;
                return false;
            }
            catch (InvalidCategoryCodeException e) {
                //Exception is good!, Let's try to add the category to data store.
                DataStoreFactory dsFactory = DataStoreFactory.getInstance();
                try {
                    dsFactory.getCategoryDS().update(this.category);
                    return true;
                } catch (UpdateFailedException | IOException ufe) {
                    JOptionPane.showMessageDialog(null,
                            "Error :: " + ufe.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    this.category = null;
                    return false;
                }
            }
        }
        catch (InvalidCategoryCodeException icce) {
            JOptionPane.showMessageDialog(null,
                        "Error :: " + icce.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                this.category = null;
            return false;
        }
    }

    public Category getAdded() {
        return this.category;
    }
}
