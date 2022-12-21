package Interfaces;

import java.util.List;
import java.util.function.Supplier;

/**
 * All entities must implement this interface. Allows for models
 * and views to interact with entities.
 * <ul>
 * <li>{@code @method} getID: returns @return int.</li>
 * <li>{@code @method} hasChanged: returns whether the entity has changed since last save.</li>
 * <li>{@code @method} resetChangedState: sets hasChanged to false; indicating this entity has saved.</li>
 * <li>{@code @method} getLoadOrder: list of methods to load an entity from the DB to the application.</li>
 * <li>{@code @method} getSaveOrder: list of methods to persist an entity to the DB.</li>
 * </ul>
 */
public interface Entity {
    String getID();
    boolean hasChanged();
    void resetChangedState();
    List<Supplier<Object>> getSerializationChain();
}
