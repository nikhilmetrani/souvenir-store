package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.event.*;

import javax.swing.*;

public abstract class OkCancelDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public OkCancelDialog (JFrame parent, String title) {
        super (parent, title);
        add ("Center", createFormPanel());
        add ("South",  createButtonPanel());
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
    }

    public OkCancelDialog (JFrame parent) {
        this (parent, "");
        this.setLocationRelativeTo(parent);
        this.setModal(true);
        this.pack();
    }

    private JPanel createButtonPanel () {
        JPanel p = new JPanel ();

        JButton btnOk;
        JButton btnCx;
        ActionListener alOk;
        ActionListener alCx;

        btnOk = new JButton ("OK");
        alOk = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                boolean success = performOkAction ();
                if (success) {
                    destroyDialog ();
                }
            }
        };
        btnOk.addActionListener (alOk);
        p.add (btnOk);

        btnCx = new JButton ("Cancel");
        alCx = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                destroyDialog ();
            }
        };
        btnCx.addActionListener (alCx);
        p.add (btnCx);

        return p;
    }

    private void destroyDialog () {
        setVisible (false);
        dispose();
    }

    protected abstract JPanel createFormPanel () ;

    protected abstract boolean performOkAction () ;

}