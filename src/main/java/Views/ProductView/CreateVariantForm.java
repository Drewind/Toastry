package Views.ProductView;

import Constants.LogLevel;
import Constants.ViewLayoutStyle;
import Entities.Entity;
import Entities.ProductVariant;
import Graphics.BlockWithHeader;
import Graphics.Builders.TextLabelBuilder;
import Graphics.Controls.DropdownInput;
import Graphics.Factories.ButtonFactory;
import Graphics.Factories.TextFactory;
import Graphics.ViewPartial;
import Interfaces.FormInterface;
import Interfaces.InputObserver;
import Models.EntityModel;
import Models.ViewModels.VariantCreationVM;
import Utilities.GBC;
import Utilities.Styler;
import Views.ProductView.Partials.AssignedVariantsView;
import Views.Shared.EntityListCellRenderer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.*;
import java.util.stream.Collectors;

public class CreateVariantForm extends ViewPartial implements FormInterface<ProductVariant>, InputObserver {
    private final AssignedVariantsView assignedVariantsPanel = new AssignedVariantsView(this);
    private final VariantCreationVM viewModel = new VariantCreationVM();
    private final EntityModel<ProductVariant> variantModel;
    private final DropdownInput<ProductVariant> variantTemplateDropdown;

    public CreateVariantForm(final EntityModel<ProductVariant> variantModel) {
        this.variantModel = variantModel;
        this.variantTemplateDropdown = new DropdownInput<>(variantModel.getEntities());
        this.variantTemplateDropdown.setRenderer(new EntityListCellRenderer());
        this.variantTemplateDropdown.registerObserver(this);
        this.viewModel.updateComponents(variantModel.getEntities().get(0));

        layout.insets(new Insets(16, 20, 0, 20));
        super.add(assignedVariantsPanel, layout.layoutVertical());
        super.add(createAddVariantPanel(), layout.layoutVertical());
    }

    private JPanel createAddVariantPanel() {
        TextLabelBuilder textBuilder = new TextLabelBuilder();
        JLabel header = textBuilder.text("Add a Variant").fontSize(18).boldify().buildLabel();

        return new BlockWithHeader(header) {{
            add(new ViewPartial(Styler.CONTAINER_BACKGROUND.brighter()) {{
                add(
                    TextFactory.buildTextArea("Use the dropdown box to choose an existing variant template or create a new variant."),
                    layout.width(2).layoutVertical()
                );

                add(new JPanel(new GridLayout(1, 2)) {{
                    setOpaque(false);
                    add(newRow(
                        textBuilder.text("Variant Template").resetFont().buildLabel(),
                        variantTemplateDropdown,
                        ViewLayoutStyle.HORIZONTAL
                    ));
                    add(new JPanel(new GridLayout(0, 2)) {{
                        setOpaque(false);
                        for (ArrayList<JLabel> entry : viewModel.getComponents()) {
                            add(entry.get(0));
                            add(entry.get(1));
                        }
                    }});
                }}, layout.width(1).layoutVertical());

                add(createButtons(), layout.layoutVertical());
            }}, BorderLayout.CENTER);
        }};
    }

    private JPanel createButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton addButton = ButtonFactory.buildStandardButton("Add Variant", Styler.THEME_COLOR,
                Styler.REGULAR_FONT);
        JButton editButton = ButtonFactory.buildStandardButton("Edit Variant", Styler.APP_BG_COLOR.brighter(),
                Styler.REGULAR_FONT);
        addButton.setBackground(Styler.THEME_COLOR);
        editButton.setBackground(Styler.APP_BG_COLOR.brighter());
        addButton.addActionListener(evt -> addVariantToView(this.variantTemplateDropdown.getSelectedItem()));
        editButton.addActionListener(evt -> addVariantToView(this.variantTemplateDropdown.getSelectedItem()));

        return new JPanel(new GridBagLayout()) {{
            setOpaque(false);
            gbc.insets = new Insets(0, 40, 0, 0);
            add(addButton, GBC.setGBC(gbc, 0, 0));

            gbc.insets = new Insets(0, 0, 0, 40);
            add(editButton, GBC.setGBC(gbc, 1, 0));
        }};
    }

    public void addVariantToView(final Object selectedObject) {
        if (selectedObject instanceof Entity) {
            Optional<ProductVariant> variant = this.variantModel.findEntity(((Entity)selectedObject).getID());
            if (variant.isPresent())
                this.viewModel.updateComponents(variant.get());
            else
                System.out.println(LogLevel.WARNING + "VariantForm could not find entity from '" + selectedObject + "'.");
        }

        Entity entity = getEntityFromDropdown(selectedObject);
        if (entity != null) {
            this.assignedVariantsPanel.getTableModel().addRow(entity);
            this.assignedVariantsPanel.showVariants();
            this.variantTemplateDropdown.removeSelectableValue((ProductVariant) entity);
        }
    }

    public void removeVariantFromView(final Entity entity) {
//        assignedVariantsPanel.removeVariantFromPartial((ProductVariant) entity);
//        this.selectedVariants.remove(entity);
        variantTemplateDropdown.addItem((ProductVariant) entity);
        if (variantTemplateDropdown.getSelectedItemsCount() <= 0)
            assignedVariantsPanel.hideVariants();
    }


    @Override
    public void resetView() {
        this.variantTemplateDropdown.resetItems();
        assignedVariantsPanel.hideVariants();
    }

    @Override
    public void updateView(ProductVariant UID) {

    }

    @Override
    public List<String> getInputs() {
        return this.variantTemplateDropdown.getSelectedItems().stream().map(Entity::getID).collect(Collectors.toList());
    }

    @Override
    public void invalidateForm() {
    }

    @Override
    public JPanel getView() {
        return this;
    }

    @Override
    public void notifyFocusGained() {}

    @Override
    public void notifyFocusLost() {}

    @Override
    public void notifyInputChanged(Object value) {
        Entity potentialEntity = getEntityFromDropdown(value);
        if (potentialEntity != null)
            this.viewModel.updateComponents((ProductVariant) potentialEntity);
        else
            System.out.println(LogLevel.WARNING + "VariantForm could not find entity from '" + value + "'.");
    }

    /**
     * Validates the input provided from the dropdown input control that it is a valid {@code Entity} object.
     * If it is indeed an entity, it will return the entity otherwise will return null.
     * @param value  {@code Object} from the dropdown control
     * @return {@code Entity} or {@code null}
     */
    private Entity getEntityFromDropdown(final Object value) {
        if (value instanceof Entity) {
            Optional<ProductVariant> variant = this.variantModel.findEntity(((Entity)value).getID());
            return variant.orElse(null);
        }
        return null;
    }
}
