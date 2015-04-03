/**
 *
 */
package sg.edu.nus.iss.se23pt2.pos;
import sg.edu.nus.iss.se23pt2.pos.gui.StoreAppWindow;

/**
 * @author Nikhil Metrani
 *
 */
public class StoreApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            SouvenirStore store = new SouvenirStore();
            store.loadData();
            Session session = Session.getInstance(store);
            session.authenticate();
            if (session.isActive()) {
                StoreAppWindow window = new StoreAppWindow(store, session);
                window.setLocationRelativeTo(null); //show in center of screen
                window.setVisible(true);
            }
            else {
                System.exit(0);
            }
        } catch (Exception e) {
        }
    }

}
