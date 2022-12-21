package Graphics.Builders;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;

import Graphics.Containers.StandardContainer;
import Graphics.Factories.TextFactory;
import Graphics.Text.RegularDescription;
import Utilities.Styler;

/**
 * Container factory using the builder pattern.
 */
public class ContainerBuilder {
    private Color _containerColor = Styler.CONTAINER_BACKGROUND; // Defaults to the standard container bg.
    private JLabel _header; // Optional header with default header properties.
    private JLabel _description; // Optional description to describe this container.
    private JComponent _content; // Main content of this container.
    
    public ContainerBuilder() {}

    public StandardContainer buildContainer() {
        return new StandardContainer(_containerColor, _header, _description, _content);
    }

    public ContainerBuilder bgColor(Color _containerColor) {
        this._containerColor = _containerColor;
        return this;
    }

    /**
     * header
     * Creates a standardized header object with the requested text.
     * @param _text text to be inserted into the header object.
     * @return {@code ContainerBuilder}
     */
    public ContainerBuilder header(String _text) {
        this._header = TextFactory.buildStandardHeader(_text);
        return this;
    }

    /**
     * description
     * Inserts a standard description label with the requested text.
     * Used to describe the purpose of this UI container to the user.
     * @param _text {@code String}
     * @return {@code ContainerBuilder}
     */
    public ContainerBuilder description(String _text) {
        this._description = new RegularDescription(_text);
        return this;
    }

    public ContainerBuilder content(JComponent _content) {
        this._content = _content;
        return this;
    }
}