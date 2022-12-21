package Services;

import Entities.CheckLineItem;
import Interfaces.UndoInterface;

public class UndoTransaction implements UndoInterface {
    private final CheckLineItem lineItem;

    public UndoTransaction(CheckLineItem lineItem) {
        this.lineItem = lineItem;
    }

    @Override
    public CheckLineItem undo() {
        return this.lineItem;
    }
}
