package Views.Popups;

import javax.swing.JOptionPane;

public class Popup {
    public static void infoBox(String titleBar, String infoMessage) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}