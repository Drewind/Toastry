package Graphics.Tables;

import Constants.LogLevel;
import Entities.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * An implementation of {@code AbstractTableModel} used with Toastry's {@code Entity} data structures.
 * Holds a list of entities and uses CRUD-like commands to manage its data.
 */
public class EntityTableModel extends AbstractTableModel {
    protected List<Entity> entities;

    public EntityTableModel() {
        this.entities = new ArrayList<>();
    }

    public void addRow(Entity entity) {
        try {
            this.entities.add(entity);
            fireTableRowsInserted(this.entities.size() - 1, this.entities.size() - 1);
        } catch (Exception ex) {
            System.out.println("Warning: couldn't add new entity to EntityTableModel.java.");
        }
    }

    public void removeRow(Entity entity) {
        try {
            this.entities.remove(entity);
            fireTableRowsDeleted(this.entities.size() - 1, this.entities.size() - 1);
        } catch (Exception ex) {
            System.out.println("Warning: couldn't remove entity to EntityTableModel.java.");
        }
    }

    public void removeRow(int row) {
        this.entities.remove(row);
        fireTableRowsDeleted(this.entities.size() - 1, this.entities.size() - 1);
    }

    public Entity getEntityAt(int row) {
        try {
            return this.entities.get(row);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(LogLevel.WARNING + "Could not find row " + row + " in EntityTableModel.java.");
            return null;
        }
    }

    public List<Entity> getChangedEntities() {
        List<Entity> changed = new ArrayList<>(this.entities.size());

        for (Entity entity : this.entities) {
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
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
}