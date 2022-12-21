package Graphics.Controls;

import Interfaces.InputObserver;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class TextInput extends JTextField{
    private final boolean ALLOW_NULLABLE;
    private final ArrayList<InputObserver> observers = new ArrayList<>();

    public TextInput(final boolean nullable) {
        setBackground(Styler.APP_BG_COLOR.brighter());
        setForeground(Styler.TEXT_COLOR);
        setCaretColor(Styler.THEME_COLOR.brighter());

        setFont(Styler.INPUT_FONT);
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.CONTAINER_BACKGROUND.darker()));

        this.ALLOW_NULLABLE = nullable;
        addFocusListener(getFocusListener());
    }

    private FocusListener getFocusListener() {
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                for (InputObserver observer : observers) { observer.notifyFocusGained(); }
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.THEME_COLOR));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                for (InputObserver observer : observers) { observer.notifyFocusLost(); }
                if (!ALLOW_NULLABLE && getText().equals("")) {
                    setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.DANGER_COLOR));
                } else {
                    setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Styler.CONTAINER_BACKGROUND.darker()));
                }
            }
        };
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

    // Detects when enter is clicked.
    // @todo remove this or figure out if we need this
//        nameField.addActionListener(new java.awt.event.ActionListener() {
//        public void actionPerformed(java.awt.event.ActionEvent evt) {
//        }
//    });
}
