package sg.edu.nus.iss.se23pt2.pos.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EmptyPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame parent;

    public EmptyPanel (JFrame parent) {
        this.parent = parent;
        setLayout (new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
