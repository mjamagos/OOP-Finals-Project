package Forms;

import javax.swing.JFrame;

/**
 * Base class for all frames that need a link back to the main page
 * Can now work with either Instructor or Admin main page
 */
public abstract class BaseFrame extends JFrame {

    // Keep a reference to the main page, can be Instructor or Admin
    protected JFrame homePage;

    /**
     * Constructor: accepts any main page (Instructor or Admin)
     */
    public BaseFrame(JFrame homePage) {
        this.homePage = homePage;                      // save the main page instance
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the whole app when this frame closes
        setLocationRelativeTo(null);                   // center the frame on screen
    }

    /**
     * Handy method to hide this frame and bring back the main page
     */
    protected void goBackToMain() {
        this.setVisible(false);  // hide current frame
        if (homePage != null) {
            homePage.setVisible(true); // show the main page again
        }
    }

    /**
     * Optional: method to set logged-in user info for this frame
     * Can be called from login regardless of role
     */
    protected String userID;
    protected String role;
    protected String FName;
    protected String MName;
    protected String LName;

    public void setLoggedInUser(String userID, String role, String FName, String MName, String LName) {
        this.userID = userID;
        this.role = role;
        this.FName = FName;
        this.MName = MName;
        this.LName = LName;
    }
}
