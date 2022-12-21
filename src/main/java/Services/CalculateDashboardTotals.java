package Services;

import Entities.Product;
import Models.ProductModel;

/**
 * Service to be injected into views/controllers.
 * Computes the grand totals for the Dashboard.
 */
public class CalculateDashboardTotals {
    private final ProductModel model;

    public CalculateDashboardTotals(ProductModel model) {
        this.model = model;
    }

    public Double computeGrossSales() {
        double result = 0.0;
        for (Product product : this.model.getEntities()) {
            result += product.getTotalSales();
        }
        return result;
    }

    public Double computeNetSales() {
        double result = 0.0;
        for (Product product : this.model.getEntities()) {
            result += (product.getTotalSales() - product.getTotalExpenses());
        }
        return result;
    }

    public int computeTotalSales() {
        int result = 0;
        for (Product product : this.model.getEntities()) {
            result += product.getTotalSales();
        }
        return result;
    }
}
