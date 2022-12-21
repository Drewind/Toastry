package Views.ProductView.Partials;

import Graphics.Builders.TextLabelBuilder;
import Graphics.Factories.TextFactory;
import Graphics.Tables.EntityTableModel;
import Graphics.ViewPartial;
import Utilities.GBC;
import Utilities.Styler;
import Views.ProductView.CreateVariantForm;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class AssignedVariantsView extends ViewPartial {
    private final JLabel noVariantsAddedLabel = TextFactory.buildLabel("This product does not have variants associated with it.");
    private final VariantsAssignedToProductTableModel tableModel = new VariantsAssignedToProductTableModel(List.of());
    private final JPanel variantsAssignedPanel = new JPanel(new GridBagLayout()) {{
        setBackground(Styler.APP_BG_COLOR);
    }};
    private final JPanel contentPane;
    private final CreateVariantForm createVariantForm;

    public AssignedVariantsView(CreateVariantForm createVariantForm) {
        TextLabelBuilder textBuilder = new TextLabelBuilder();
        this.createVariantForm = createVariantForm;
        this.variantsAssignedPanel.add(createVariantsTable(), GBC.setVertical(0));
        setOpaque(false);

        this.contentPane = new JPanel(new CardLayout()) {{
            setBackground(Styler.CONTAINER_BACKGROUND.brighter());
            add(noVariantsAddedLabel, "NoVariants");
            add(variantsAssignedPanel, "VariantsAssigned");
        }};

        add(textBuilder.text("Configure Variants").horizontal(JLabel.LEFT).fontSize(18).boldify().buildLabel(), GBC.setVertical(0));
        add(this.contentPane, GBC.setVertical(1));
    }

    private JScrollPane createVariantsTable() {
        JTable table = new JTable(tableModel) {{
            super.setRowHeight(32);
            super.setAutoCreateRowSorter(true);
            super.setForeground(Styler.TEXT_COLOR);
            super.setIntercellSpacing(new Dimension(14, 8));
            super.setAutoCreateColumnsFromModel(false);

            setBackground(Styler.APP_BG_COLOR.brighter());
            setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        }};

        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.parseInt( e.getActionCommand() );
                createVariantForm.removeVariantFromView(((EntityTableModel)table.getModel()).getEntityAt(modelRow));
                ((EntityTableModel)table.getModel()).removeRow(modelRow);
            }
        };

        EntityTableColumn entityTableColumn = new EntityTableColumn(table, delete, 4);
        entityTableColumn.setMnemonic(KeyEvent.VK_C);

        return new JScrollPane(table) {{
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            setBackground(Styler.APP_BG_COLOR.brighter());
            getViewport().setBackground(Styler.APP_BG_COLOR);
            setMinimumSize(new Dimension(0, 240)); // 80
            setPreferredSize(new Dimension(0, 240)); // 100
            setMaximumSize(new Dimension(0, 240)); // 140
        }};
    }

    public EntityTableModel getTableModel() {
        return this.tableModel;
    }

    public void showVariants() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, "VariantsAssigned");
    }

    public void hideVariants() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, "NoVariants");
    }

//    public void resetView() {
//        variantsTable.removeAll();
//        variantsTable = createVariantsTable();
//        System.out.println("view reset");
//        this.invalidate();
//    }
}
