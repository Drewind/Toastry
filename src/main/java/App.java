import Constants.ActiveController;
import Controllers.*;
import Entities.Product;
import Graphics.Text.RegularText;
import Models.*;
import Services.VariantService;
import Utilities.LogService;
import Utilities.Styler;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App {
    private static JFrame window;
    private static HomeController homeController;
    private static ProductController productController;
    private static LocationController locationController;
    private static NavbarController navController;
    private static PointOfSalesController posController;
    private static final VariantService variantService = new VariantService();
    private static final ProductVariantOptionModel variantOptionModel = new ProductVariantOptionModel(variantService);
    private static final ProductVariantModel variantModel = new ProductVariantModel(variantService);
    private static final ProductModel productModel = new ProductModel(variantService);
    private static final TransactionModel transactionModel = new TransactionModel(variantService);
    private static final Model locationModel = new RestaurantModel();
    private static final JPanel MAIN_PANEL = new JPanel(new CardLayout());

    public static void main(String[] args) throws Exception {
        loadFont(new File("src/main/resources/MADE Evolve Sans Regular (PERSONAL USE).otf"));
        initializeControllers();
        initializeWindow();

        MAIN_PANEL.setOpaque(false); // Set to invisible so the content pane background is visible.
        window.getContentPane().setBackground(Styler.APP_BG_COLOR); // Sets content pane background.
        window.getContentPane().add(navController.getDefaultView(), BorderLayout.NORTH);
        window.getContentPane().add(MAIN_PANEL, BorderLayout.CENTER);

        // App icon
        try {
            BufferedImage logoImage = ImageIO.read(new File("src/main/resources/media/logos/Toastry-logos_transparent.png"));
            Image logo = logoImage.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
            window.setIconImage(logo);
        } catch (IOException ex) {
            System.out.println("WARNING: Couldn't load application icon.");
        }

        //MAIN_PANEL.add(this.createHomeCard(), "main");
        MAIN_PANEL.add(homeController.getDefaultView(), ActiveController.HOME.name());
        MAIN_PANEL.add(productController.getDefaultView(), ActiveController.PRODUCT.name());
        MAIN_PANEL.add(locationController.getDefaultView(), ActiveController.LOCATION.name());
        MAIN_PANEL.add(posController.getDefaultView(), ActiveController.POS.name());
    }

    private static void initializeControllers() {
        variantService.init(productModel, variantModel, variantOptionModel);
        variantModel.loadEntities();
        variantOptionModel.loadEntities();
        productModel.loadEntities();

        DailyStatsModel statsModel = new DailyStatsModel();
        productController = new ProductController(productModel, variantModel, MAIN_PANEL);
        homeController = new HomeController(productModel, statsModel, MAIN_PANEL);
        locationController = new LocationController(locationModel, MAIN_PANEL);
        posController = new PointOfSalesController(productModel, productModel, MAIN_PANEL); // TODO important! replace second arg
        navController = new NavbarController();
    }

    private static void initializeWindow() {
        window = new JFrame("Toastry");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        window.setSize(900, 720);
        window.getContentPane().setBackground(Styler.CONTAINER_BACKGROUND);

        window.getContentPane().add(new RegularText("Developed by Andrew Michael"), BorderLayout.SOUTH);
    }

    private static void loadFont(final File fontFile) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font newFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            ge.registerFont(newFont);
            System.out.println("Successfully loaded in custom font '" + newFont.getFamily() + "'.");
        } catch (IOException | FontFormatException ex) {
            System.out.println("WARNING: An exception was thrown when loading in custom fonts.\n" + ex);
        }
    }
}