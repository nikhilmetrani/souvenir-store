package sg.edu.nus.iss.se23pt2.posgui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EmptyPanel extends JPanel {

    private JFrame parent;

    public EmptyPanel (JFrame parent) {
        this.parent = parent;
        setLayout (new BorderLayout(5,5));
    }
}
