package Views.Home;

import java.util.ArrayList;
import java.util.Collection;

import Entities.Product;
import Graphics.Tables.EntityTableModel;
import Interfaces.Entity;

public class TMTopSales extends EntityTableModel {
    public TMTopSales(Collection<Product> collection) {
        super();
        for (Product entity : collection) {
            super.addRow(entity);
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Menu Item";
            case 1 -> "Quantity Sold";
            case 2 -> "Net Sales";
            default -> null;
        };
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public ArrayList<Product> getChangedProducts() {
        ArrayList<Product> entities = new ArrayList<>(super.getRowCount());
        for (Entity entity : super.entities) {
            try {
                entities.add((Product)entity);
            } catch (Exception ex) {
                System.out.println("Warning: TMTopSales couldn't convert entity to product, getChangedProducts.");
            }
        }
        return entities;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;

        try {
            Product product = (Product)super.entities.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    value = product.getProductName();
                    break;
                case 1:
                    value = product.getTotalSales();
                    break;
                case 2:
                    value = product.getTotalProfit();
                    break;
                case 3:
            }
        } catch (Exception ex) {
            System.out.println("Warning: TMTopSales.java couldn't convert class, getValueAt.");
        }
        
        return value;
    }

    // This partial table view shouldn't be updatable.
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // if (aValue instanceof String) {
        //     try {
        //         Product p = (Product)super.entities.get(rowIndex);
        //         switch (columnIndex) {
        //             case 0:
        //                 p.setProductName(aValue.toString());
        //                 break;
        //             case 1:
        //                 p.setPrice(Double.parseDouble(aValue.toString()));
        //                 break;
        //             case 2:
        //                 p.setCost(Double.parseDouble(aValue.toString()));
        //                 break;
        //         }
        //     } catch (Exception ex) {
        //         System.out.println("Warning: TMTopSales.java couldn't convert class, setValueAt.");
        //     }
        //     fireTableRowsUpdated(rowIndex, rowIndex);
        // }
    }

    // Disable editable cells for this partial view.
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}