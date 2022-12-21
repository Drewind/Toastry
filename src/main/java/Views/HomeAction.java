package Views;

import Controllers.HomeController;
import Controllers.ViewActionInterface;
import Graphics.Containers.OverviewContainer;
import Graphics.Builders.OverviewBuilder;
import Interfaces.ControllerInterface;
import Models.EntityModel;
import Models.Model;
import Models.ProductModel;
import Services.CalculateDashboardTotals;
import Utilities.GBC;
import Views.Home.Partials.DashboardBestSellers;
import Views.Home.Partials.DashboardControls;
import Views.Home.Partials.DashboardSummary;
import Views.Home.SalesChart;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

public class HomeAction implements ViewActionInterface {
    private final HomeController controller;
    private final ProductModel model;
    private final ArrayList<OverviewContainer> containers = new ArrayList<>();

    public HomeAction(ControllerInterface controller, EntityModel model) {
        this.controller = (HomeController) controller;
        this.model = (ProductModel) model;
        setupSummaryContainers(); // todo remove this later
    }

    @Override
    public JPanel renderView() {
        // Set constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();

        return new JPanel(new GridBagLayout()) {{
            setOpaque(false);
            add(new DashboardSummary(containers), GBC.setGBC(gbc, 0, 0, 1.0));
            add(new DashboardBestSellers(model).render(), GBC.setGBC(gbc, 0, 1, 1.0)); // todo revisit displayNumOfProducts
            add(new DashboardControls(controller).render(), GBC.setGBC(gbc, 0, 2, 1.0));
            add(new SalesChart().getChartPanel(), GBC.setGBC(gbc, 0, 3, 1.0, 1.0));
        }};
    }

    /**
     * A simple method to create the necessary dashboard containers,
     * will later revisit this and replace it with user-defined layout
     * containers/metrics.
     *
     * todo remove this
     */
    private void setupSummaryContainers() {
        OverviewBuilder overviewBuilder = new OverviewBuilder();
        CalculateDashboardTotals calculateService = new CalculateDashboardTotals(this.model);

        this.containers.add(overviewBuilder
                .contentText(String.format("%d", calculateService.computeTotalSales()))
                .description("Total Sales").buildContainer());

        this.containers.add(overviewBuilder
                .contentText(String.format("%d", calculateService.computeTotalSales()))
                .description("Empty Label").buildContainer());

        this.containers.add(overviewBuilder
                .contentText(String.format("%.2f", calculateService.computeNetSales()))
                .description("Gross Sales").buildContainer());

        this.containers.add(overviewBuilder
                .contentText(String.format("%,.2f", calculateService.computeGrossSales()))
                .description("Net Sales").buildContainer());
    }
}
