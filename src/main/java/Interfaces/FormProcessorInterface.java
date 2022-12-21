package Interfaces;

/**
 * View actions that use a form should inherit this interface.
 *
 * Ensures the controllers can properly call on view actions to
 * process said form in a safe and reliable matter.
 */
public interface FormProcessorInterface {
    void processForm();
}
