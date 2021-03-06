package Graphics.Tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Interfaces.EntityInterface;

public class EntityTableModel extends AbstractTableModel {
    protected HashMap<String, EntityInterface> entities;

    public EntityTableModel() {
        this.entities = new HashMap<>();
    }

    public void addRow(EntityInterface entity) {
        try {
            this.entities.put(entity.getID(), entity);
            fireTableRowsInserted(this.entities.size() - 1, this.entities.size() - 1);
        } catch (Exception ex) {
            System.out.println("Warning: couldn't add new entity to EntityTableMOdel.java.");
        }
    }

    public void removeRow(EntityInterface entity) {
        try {
            this.entities.remove(entity.getID());
            fireTableRowsDeleted(this.entities.size() - 1, this.entities.size() - 1);
        } catch (Exception ex) {
            System.out.println("Warning: couldn't remove entity to EntityTableModel.java.");
        }
    }

    public void updateRow(EntityInterface entity) {
        try {
            this.entities.put(entity.getID(), entity);

            fireTableRowsUpdated(this.entities.size() - 1, this.entities.size() - 1);
        } catch (Exception ex) {
            System.out.println("Warning: couldn't update entity to EntityTableModel.java.");
        }
    }

    // public EntityInterface getEntityAt(int row) {
    //     return this.entities.get(row);
    // }

    public List<EntityInterface> getChangedEntities() {
        List<EntityInterface> changed = new ArrayList<>(this.entities.size());

        for (EntityInterface entity : this.entities.values()) {
            if (entity.hasChanged()) {
                changed.add(entity);
                entity.resetChangedState();
            }
        }

        return changed;    
    }

    @Override
    public int getRowCount() {
        return this.entities.size();
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
}