package Views.ProductView;

import Controllers.ViewActionInterface;
import Utilities.GBC;
import Graphics.TabButton;
import Utilities.Styler;
import Views.ProductCreationAction;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class CreateNavbar extends JPanel implements ViewActionInterface {
    private final int PANEL_HEIGHT = 40;
    private final ProductCreationAction action;

    public CreateNavbar(final ProductCreationAction action) {
        this.action = action;
    }

    @Override
    public JPanel renderView() {
        super.setLayout(new GridBagLayout());
        super.setOpaque(false);

        super.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
        super.setMinimumSize(new Dimension(0, PANEL_HEIGHT));
        super.setMaximumSize(new Dimension(0, PANEL_HEIGHT));

        createTabButtons();
        return this;
    }

    private void createTabButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 0, 0, 0);

        TabButton generalTab = new TabButton("General", new Dimension(0, PANEL_HEIGHT), Styler.CONTAINER_BACKGROUND);
        TabButton variantTab = new TabButton("Variant", new Dimension(0, PANEL_HEIGHT), Styler.CONTAINER_BACKGROUND);

        generalTab.setAsActive();
        generalTab.setFocusable(false);
        generalTab.addActionListener(e -> {
                this.action.viewGeneral();
                generalTab.setAsActive();
                variantTab.setAsInactive();
            }
        );
        super.add(generalTab, GBC.setGBC(gbc, 0, 0, 1.0));

        variantTab.setFocusable(false);
        variantTab.addActionListener(e -> {
                this.action.viewVariants();
                variantTab.setAsActive();
                generalTab.setAsInactive();
            }
        );
        super.add(variantTab, GBC.setGBC(gbc, 1, 0, 1.0));
    }
}
