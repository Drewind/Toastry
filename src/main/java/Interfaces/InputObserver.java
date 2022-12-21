package Interfaces;

public interface InputObserver {
    void notifyFocusGained();
    void notifyFocusLost();
    void notifyInputChanged(Object value);
}
