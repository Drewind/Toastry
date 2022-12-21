package Interfaces;

import Entities.Entity;

import javax.swing.JPanel;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Interface for all views that act as a form. Input is entered,
 * output is then inquired by another file, often by a controller.
 *
 * <p><br>
 * Usually hooked up to model observers to obtain information, but not required.
 * <ul>
 * <li>void resetView: resets the form's components.</li>
 * <li>void updateView (<T>): uses the generic parameter (T) to update the view using the parameter.</li>
 * <li>HashMap<String, Object> getInputs: gathers all the form's input fields
 *         where key is the name of the field and value is the {@code object} value.</li>
 * </ul>
 */
public interface FormInterface<T extends Entity> {
    void resetView();
    void updateView(final T UID);
    List<String> getInputs();
    void invalidateForm();
    JPanel getView();
}