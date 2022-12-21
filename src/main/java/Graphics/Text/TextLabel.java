package Graphics.Text;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class TextLabel extends JLabel {
    public TextLabel(String name, Color bgColor, Color fgColor, String text, Font font, boolean bgVisible, boolean isVisible, int horizontalAlignment) {
        super.setName(name);
        super.setBackground(bgColor);
        super.setForeground(fgColor);
        super.setText(text);
        super.setFont(font);
        super.setOpaque(bgVisible);
        super.setVisible(isVisible);
        super.setHorizontalAlignment(horizontalAlignment);
    }
}
