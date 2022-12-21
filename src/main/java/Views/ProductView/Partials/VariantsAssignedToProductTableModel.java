package Views.ProductView.Partials;

import Entities.ProductVariant;
import Graphics.Tables.EntityTableModel;

import java.util.Collection;

public class VariantsAssignedToProductTableModel extends EntityTableModel {
    public VariantsAssignedToProductTableModel(Collection<ProductVariant> collection) {
        super();
        for (ProductVariant entity : collection) {
            super.addRow(entity);
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Variant Name";
            case 1 -> "Variant Cost";
            case 2 -> "Variant Type";
            case 3 -> "Variant Options";
            case 4 -> "Controls";
            default -> null;
        };
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;

        try {
            ProductVariant variant = (ProductVariant)super.entities.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    value = variant.getVariantName() + " (" + variant.getID().substring(31, 35) + ")";
                    break;
                case 1:
                    value = variant.getVariantCost();
                    break;
                case 2:
                    value = variant.getSelectionType();
                    break;
                case 3:
                    value = variant.getSelectionOptions();
                    break;
                case 4:
                    break;
                case 5:
            }
        } catch (Exception ex) {
            System.out.println("Warning: VariantsAssignedToProductTableModel couldn't convert class, getValueAt.");
        }
        
        return value;
    }

    // This partial table view shouldn't be updatable.
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    // Disable editable cells for this partial view.
    @Override
    public boolean isCellEditable(int row, int column) {
        return (column == 4);
    }
}