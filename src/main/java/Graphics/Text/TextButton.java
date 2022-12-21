package Graphics.Text;

import javax.swing.JButton;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;

public class TextButton extends JButton {
    public TextButton(Color bgColor, Color fgColor, Border border, String text, Font font, boolean isVisible) {
        super.setBackground(bgColor);
        super.setForeground(fgColor);
        super.setBorder(border);
        super.setText(text);
        super.setFont(font);
        super.setOpaque(isVisible);
    }
}
