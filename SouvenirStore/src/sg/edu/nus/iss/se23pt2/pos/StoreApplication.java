/**
 *
 */
package sg.edu.nus.iss.se23pt2.pos;
import sg.edu.nus.iss.se23pt2.pos.gui.*;
import java.awt.*;

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
            @Override
            public void run() {
                try {
                    StoreAppWindow window = new StoreAppWindow();
                    window.setLocationRelativeTo(null); //show in center of screen
                    if (window.isSessionActive()) {
                        window.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
