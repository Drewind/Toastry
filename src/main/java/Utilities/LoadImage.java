package Utilities;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A simple singleton to load images. If this class encounters any exceptions, it will simply return null.
 */
public class LoadImage {
    /**
     * Attempts to load in an image and if successful, returns the image as a {@code JLabel} object.
     * @return {@code JLabel}
     */
    public static JLabel loadImage(final String path) {
        try {
            Image image = ImageIO.read(new File(path));
            return new JLabel(new ImageIcon(image));
        } catch (IOException ex) {
            System.out.println("WARNING: Couldn't load image.");
            return null;
        }
    }

    public static JLabel loadIcon(final String iconName, final int sizeX, final int sizeY) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/media/icons/" + iconName));
            Image image = bufferedImage.getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(image));
        } catch (IOException ex) {
            System.out.println("WARNING: Couldn't load image.");
            return null;
        }
    }

    public static ImageIcon loadIconImage(final String iconName, final int sizeX, final int sizeY) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/media/icons/" + iconName));
            Image image = bufferedImage.getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (IOException ex) {
            System.out.println("WARNING: Couldn't load image button.");
            return null;
        }
    }
}
