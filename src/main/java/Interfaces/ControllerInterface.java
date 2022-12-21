package Interfaces;

import javax.swing.JPanel;

/**
 * Enforces common functionality that all controllers must provide.
 * <p>
 * getDefaultView -- returns the default view for that controller
 */
public interface ControllerInterface {
    JPanel getDefaultView();
}