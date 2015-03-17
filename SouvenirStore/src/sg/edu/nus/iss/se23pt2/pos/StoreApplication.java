/**
 * 
 */
package sg.edu.nus.iss.se23pt2.pos;

//import sg.edu.nus.iss.se23pt2.pos.datastore.*;
//import sg.edu.nus.iss.se23pt2.pos.exception.*;
import sg.edu.nus.iss.se23pt2.posgui.*;
import javax.swing.*;

/**
 * @author Nikhil Metrani
 *
 */
public class StoreApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SouvenirStore store = new SouvenirStore();
		store.loadData();
		
		try {
			LoginDialog dialog = new LoginDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
