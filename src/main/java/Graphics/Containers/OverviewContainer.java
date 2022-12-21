package Graphics.Containers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.ContainerInterface;
import Utilities.GBC;

public class OverviewContainer extends JPanel implements ContainerInterface {
    private final JLabel boldLabel;
    private final JLabel plainLabel;
    private final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.##");
    private final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,###");
    private final Insets MARGINS = new Insets(0, 10, 0, 10);

    public OverviewContainer(Color bgColor, Color borderColor, Color _textHeaderColor, Color _textDescColor, JLabel content, String description, boolean formatCurrency) {
        super(new GridBagLayout());
        super.setBackground(bgColor);
        super.setOpaque(true);
        super.setVisible(true);
        super.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, borderColor));

        // Sets the font
        this.boldLabel = content;
        this.plainLabel = new JLabel(description);
        this.boldLabel.setFont(new Font("Arial", Font.BOLD, 28));
        this.plainLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        this.boldLabel.setForeground(_textHeaderColor);
        this.plainLabel.setForeground(_textDescColor);

        layoutView();
    }

    /**
     * Initializes the container's layout, called from the constructor.
     */
    @Override
    public void layoutView() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = MARGINS;

        super.add(this.boldLabel, GBC.setGBC(gbc, 0, 0, 0.0));
        super.add(this.plainLabel, GBC.setGBC(gbc, 0, 1, 0.0));
    }

    @Override
    public JComponent getContent() {
        return this.boldLabel;
    }
}
