package Views.POS.Partials;

import Controllers.PointOfSalesController;
import Graphics.Controls.TextInput;
import Graphics.Factories.ButtonFactory;
import Utilities.LoadImage;
import Utilities.Styler;
import Views.POS.Menu;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class MenuNavbar extends JPanel {
    final PointOfSalesController controller;
    JButton fourSquareIcon = ButtonFactory.buildImageIcon(LoadImage.loadIconImage("icon_square_four.png", 26, 26));
    JButton nineSquareIcon = ButtonFactory.buildImageIcon(LoadImage.loadIconImage("icon_square_nine.png", 26, 26));
    JButton backIcon = ButtonFactory.buildImageIcon(LoadImage.loadIconImage("icon_arrow_left.png", 26, 26));
    Menu menu;

    public MenuNavbar(Menu menu, PointOfSalesController controller) {
        super(new BorderLayout());
        Dimension PANEL_SIZE = new Dimension(0, 26);
        super.setMinimumSize(PANEL_SIZE);
        super.setPreferredSize(PANEL_SIZE);
        super.setMaximumSize(PANEL_SIZE);
        super.setBackground(Styler.DARK_SHADE2_COLOR);
        super.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(61,61,61)));

        super.add(viewOptions(), BorderLayout.WEST);
        super.add(searchBar(), BorderLayout.EAST);
        this.controller = controller;
        this.menu = menu;
    }

    private JPanel viewOptions() {
        fourSquareIcon.addActionListener(e -> this.menu.switchToFourSquares());
        nineSquareIcon.addActionListener(e -> this.menu.switchToNineSquares());
        backIcon.addActionListener(e -> this.controller.switchToHomeCreation());

        return new JPanel() {{
            setOpaque(false);
            add(backIcon);
            add(fourSquareIcon);
            add(nineSquareIcon);
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
