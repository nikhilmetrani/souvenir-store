package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.*;

import javax.swing.*;

public abstract class ConfirmDialog extends OkCancelDialog {

    private JLabel       messageLabel;

    public ConfirmDialog (JFrame parent, String title, String message) {
        super (parent, title);
        messageLabel.setText (message);
    }

    protected JPanel createFormPanel () {
        JPanel p = new JPanel ();
        messageLabel = new JLabel ();
        p.add (messageLabel);
        return p;
    }

}
