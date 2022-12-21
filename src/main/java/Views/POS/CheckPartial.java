package Views.POS;

import Controllers.PointOfSalesController;
import Interfaces.ControllerInterface;
import Utilities.GBC;
import Utilities.Styler;
import Graphics.Builders.TextLabelBuilder;
import Graphics.Factories.ButtonFactory;
import Graphics.ModernScrollBar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CheckPartial extends JPanel {
    private final PointOfSalesController controller;
    private JScrollPane scrollPane;
    private JLabel tableNumber;
    private JButton checkoutButton;
    private final JPanel ordersPane;

    public CheckPartial(ControllerInterface controller, JPanel ordersPanel) {
        super(new GridBagLayout());
        super.setOpaque(true);
        super.setBackground(new Color(61,61,61));
        super.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Styler.CONTAINER_BACKGROUND));
        this.controller = (PointOfSalesController) controller;
        this.ordersPane = ordersPanel;

        Dimension PANEL_SIZE = new Dimension(280, 0);
        super.setMinimumSize(PANEL_SIZE);
        super.setPreferredSize(PANEL_SIZE);
        super.setMaximumSize(PANEL_SIZE);
        init();

        // Set constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.ipadx = 7;
        gbc.ipady = 7;
        super.add(createHeader(), GBC.setGBC(gbc, 0, 0, 0.0));
        super.add(this.scrollPane, GBC.setGBC(gbc, 0, 1, 1.0, 1.0));
        super.add(this.checkoutButton, GBC.setGBC(gbc, 0, 2, 0.0));
    }

    public JLabel createHeader() {
        TextLabelBuilder builder = new TextLabelBuilder();

        return builder.text("A29").bgColor(Styler.DARK_SHADE2_COLOR).boldify().buildLabel();
    }

    public void init() {
        this.scrollPane = new ModernScrollBar(this.ordersPane);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setOpaque(false);

        this.checkoutButton = ButtonFactory.buildStandardButton("Checkout", null, new Font("Arial", Font.BOLD, 16));
        this.checkoutButton.addActionListener(e -> controller.checkoutOrder());
    }
}
