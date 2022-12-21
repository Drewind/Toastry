package Interfaces;

import Entities.Entity;

/**
 * Specific methods that the view actions require to keep their children updated.
 * 
 * Models which implement ModelEntityObserver will call observers listening to the
 * model with these methods. i.e., a ModelEntity will fire notifyNewEntity on
 * the listeners from the model. This ensures view actions will be properly
 * able to update their children.
 */
public interface ViewActionObserver {
    void notifyNewEntity(Entity entity);   // Entity was added to model
    void notifyRemovedEntity(Entity entity); // Entity was removed from model
    void notifyModifiedEntity(Entity entity); // Entity was modified from model
}