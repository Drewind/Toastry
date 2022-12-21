package Views.POS.Partials;

import Graphics.Controls.TextInput;
import Graphics.Factories.ButtonFactory;
import Utilities.LoadImage;
import Utilities.Styler;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class MenuNavbar extends JPanel {
    public MenuNavbar() {
        super(new BorderLayout());
        Dimension PANEL_SIZE = new Dimension(0, 26);
        super.setMinimumSize(PANEL_SIZE);
        super.setPreferredSize(PANEL_SIZE);
        super.setMaximumSize(PANEL_SIZE);
        super.setBackground(Styler.DARK_SHADE2_COLOR);
        super.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(61,61,61)));

        super.add(viewOptions(), BorderLayout.WEST);
        super.add(searchBar(), BorderLayout.EAST);
    }

    private JPanel viewOptions() {
        return new JPanel() {{
            setOpaque(false);
            add(ButtonFactory.buildImageIcon(LoadImage.loadIconImage("icon_square_four.png", 26, 26)));
            add(LoadImage.loadIcon("icon_square_nine.png", 26, 26));
        }};
    }

    private JPanel searchBar() {
        TextInput searchBox = new TextInput(true);
        searchBox.setText("Search...");

        return new JPanel() {{
            setOpaque(false);
            add(searchBox);
        }};
    }
}
