package sg.edu.nus.iss.se23pt2.pos.gui;

import sg.edu.nus.iss.se23pt2.pos.*;
import sg.edu.nus.iss.se23pt2.pos.datastore.DataStoreFactory;
import sg.edu.nus.iss.se23pt2.pos.exception.UpdateFailedException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    public AddCategoryDialog(JFrame parent) {
        super(parent, "Add Category");
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
        if ((3 != code.length())) {
            JOptionPane.showMessageDialog(null,
                        "Error :: Category code must be three characters long.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            this.category = new Category(code, name);
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
        catch (InvalidCategoryCodeException icce) {
            return false;
        }
    }

    public Category getAdded() {
        return this.category;
    }
}
