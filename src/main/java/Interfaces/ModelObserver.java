package Interfaces;

/**
 * @deprecated is not being used
 */
public interface ModelObserver {
    void registerObserver(ViewObserver observer);
    void removeObserver(ViewObserver observer);
    void notifyObservers();
}
