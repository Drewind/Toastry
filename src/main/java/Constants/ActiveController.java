package Constants;

/**
 * An enum class to define which controller is active.
 * Each controller will have its own value listed here.
 * <br>
 * <p>
 * See below for an example on how to use this class.
 * <pre>
 * CardLayout cl = (CardLayout)contentPane.getLayout();
 * cl.show(contentPane, ActiveController.LOCATION.toString());
 * </pre>
 */
public enum ActiveController {
    HOME,
    PRODUCT,
    LOCATION,
    POS
}
