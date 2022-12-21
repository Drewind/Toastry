package Graphics.Controls;

import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class NumberInput extends JTextField {
    public NumberInput(final DocumentFilter filter) {
        setBackground(Styler.APP_BG_COLOR.brighter());
        setForeground(Styler.TEXT_COLOR);
        setCaretColor(Styler.THEME_COLOR.brighter());

        setFont(Styler.INPUT_FONT);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        setText("0.00");

        PlainDocument doc = (PlainDocument) super.getDocument();
        doc.setDocumentFilter(filter);

        addFocusListener(getFocusListener());
    }

    private FocusListener getFocusListener() {
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }
        };
    }
}
