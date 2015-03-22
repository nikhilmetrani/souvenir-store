/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

//import sg.edu.nus.iss.se23pt2.pos.datastore.*;
//import sg.edu.nus.iss.se23pt2.pos.exception.*;
import sg.edu.nus.iss.se23pt2.posgui.*;

import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;

/**
 * @author Nikhil Metrani
 *
 */
public class StoreApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StoreAppWindow window = new StoreAppWindow();
					window.setLocationRelativeTo(null); //show in center of screen
					if (window.isSessionActive())
						window.setVisible(true);
					else
						window = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
