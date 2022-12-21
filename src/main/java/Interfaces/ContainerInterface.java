package Interfaces;

import javax.swing.JComponent;

/**
 * Enforces functionality amongst container instances. All containers MUST implement:
 * * layoutView: to initialize the layout. This happens automatically during the layout creation, though
 *               having this method exposed allows for external classes to further control layout operations.
 * * getContent: to obtain the main content JComponent for this container.
 */
public interface ContainerInterface {
    void layoutView();
    JComponent getContent();
}
