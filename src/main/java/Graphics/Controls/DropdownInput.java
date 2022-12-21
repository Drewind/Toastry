package Graphics.Controls;

import Interfaces.InputObserver;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A standard dropdown-style control. Displays several options to the user.
 *
 * Remove elements from this list through {@code removeSelectableValue}.
 * <p>
 * Classes can subscribe to this control for updates when the selection changes.
 * @param <T>
 */
public class DropdownInput<T> extends JComboBox<T> {
    private final ArrayList<InputObserver> observers = new ArrayList<>();
    private ArrayList<T> selectedItems = new ArrayList<>();

    public DropdownInput(T[] values) {
        super(values);
        init();
    }

    public DropdownInput(List<T> values) {
        super();
        setSelectableValues(values);
        init();
    }

    public DropdownInput() {
        super();
        init();
    }

    public void resetItems() {
        this.selectedItems.forEach(super::addItem);
        this.selectedItems.clear();
    }

    /**
     * Adds the desired item to the selectable list of items. Will also remove the item from
     * the {@code selectedItems} array if present. If this item was not in the array, no error is thrown.
     * @param item the item to add to the list
     */
    @Override
    public void addItem(T item) {
        super.addItem(item);
        this.selectedItems.remove(item);
    }

    public int getSelectedItemsCount() { return this.selectedItems.size(); }

    public List<T> getSelectedItems() { return this.selectedItems; }

    /**
     * Allows for dynamically setting the selectable values in this combo box. This operation <u>will reset</u>
     * all of the current values in the combo box and then assign a {@code List<String>} to it.
     *
     * Since the collection is ordered, it is possible to retrieve the index of this combo box.
     * @param values {@code List<String>}
     */
    public void setSelectableValues(List<T> values) {
        DefaultComboBoxModel<T> model = (DefaultComboBoxModel<T>) this.getModel();
        model.removeAllElements();
        model.addAll(values);
        model.setSelectedItem(values.get(0));
    }

    /**
     * Removes the desired item as a selectable value. Will add the item to the {@code selectedItems} array,
     * indicating that this item was selected.
     * @param value the value to be inserted
     */
    public void removeSelectableValue(final T value) {
        DefaultComboBoxModel<T> model = (DefaultComboBoxModel<T>) this.getModel();
        model.removeElement(value);
        notifySelectionChanged();
        this.selectedItems.add(value);
    }

    private FocusListener getFocusListener() {
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.THEME_COLOR));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.CONTAINER_BACKGROUND.darker()));
            }
        };
    }

    private void init() {
        setBackground(Styler.APP_BG_COLOR.brighter());
        setForeground(Styler.TEXT_COLOR);
        setFont(Styler.INPUT_FONT);
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.CONTAINER_BACKGROUND.darker()));

        this.addActionListener (e -> notifySelectionChanged());
        addFocusListener(getFocusListener());
    }

    /**
     * Sends out notifications when selection has been modified.
     */
    private void notifySelectionChanged() {
        for (InputObserver observer : observers) { observer.notifyInputChanged(this.getSelectedItem()); }
    }

    /**
     * Registers a new observer for this input control.
     *
     * Notifies the observer when the input control gains and loses focus.
     * @param observer {@code observer} an object that implements {@code InputObserver} interface.
     */
    public void registerObserver(InputObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the input control.
     *
     * Notifies the observer when the input control gains and loses focus.
     * @param observer {@code observer} an object that implements {@code InputObserver} interface.
     */
    public void removeObserver(InputObserver observer) {
        observers.remove(observer);
    }
}
