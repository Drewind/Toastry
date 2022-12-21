package Graphics.UI;

import Graphics.Factories.ButtonFactory;
import Utilities.LoadImage;

import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.Component;

public class SpinnerUI extends BasicSpinnerUI {
    @Override
    protected Component createPreviousButton() {
        Component component = ButtonFactory.buildImageIcon(LoadImage.loadIconImage("icon_arrow_down.png", 15, 15));
        super.createPreviousButton();
        installPreviousButtonListeners(component);
        return component;
    }

    @Override
    protected Component createNextButton() {
        Component component = ButtonFactory.buildImageIcon(LoadImage.loadIconImage("icon_arrow_up.png", 15, 15));
        installNextButtonListeners(component);
        return component;
    }
}
