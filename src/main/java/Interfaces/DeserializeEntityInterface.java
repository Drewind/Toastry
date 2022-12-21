package Interfaces;

import Entities.Entity;
import Models.LogMessage;

import java.util.List;

public interface DeserializeEntityInterface {
    Entity deserialize(final String csvLine);
    List<LogMessage> getLogs();
}
