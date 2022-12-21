package Views.Shared;

import Constants.LogLevel;
import Entities.Entity;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * Renders an entity's friendly name in a list.
 * <p>
 *
 * Extends {@link javax.swing.DefaultListCellRenderer} to retain expected functionality with one key change:
 * overrides `getListCellRendererComponent` so that it displays the entity's friendly name to the user.
 */

public class EntityListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (c instanceof JLabel) {
            try {
                ((JLabel) c).setText(((Entity)value).getEntityName());
            } catch (Exception ex) {
                System.out.println(LogLevel.WARNING + "An unexpected exception was thrown in EntityListCellRenderer.\n" + ex);
            }
        }

        return c;
    }
}
