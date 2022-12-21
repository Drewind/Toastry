package Views.Home;

import Graphics.Charts.ToastryChartTheme;
import Utilities.Styler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import java.awt.Font;
import java.util.Random;

public class SalesChart {
    private ChartPanel chartPanel;

    public SalesChart() {
        initUI();
    }

    public ChartPanel getChartPanel() {
        return this.chartPanel;
    }

    private void initUI() {
        JFreeChart chart = createChart(createDataset());
        this.chartPanel = new ChartPanel(chart);
        this.chartPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        this.chartPanel.setBackground(Styler.CONTAINER_BACKGROUND);
    }

    private XYDataset createDataset() {
        var series = new XYSeries("");
        series.add(1, randomizeInt(0, 5000));
        series.add(2, randomizeInt(0, 5000));
        series.add(3, randomizeInt(0, 5000));
        series.add(4, randomizeInt(0, 5000));
        series.add(5, randomizeInt(0, 5000));
        series.add(6, randomizeInt(0, 5000));
        series.add(7, randomizeInt(0, 5000));
        series.add(8, randomizeInt(0, 5000));
        series.add(9, randomizeInt(0, 5000));
        series.add(10, randomizeInt(0, 5000));
        series.add(11, randomizeInt(0, 5000));
        series.add(12, randomizeInt(0, 5000));

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {
        ToastryChartTheme xx = new ToastryChartTheme("Darkness");
        ChartFactory.setChartTheme(xx);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "YTD",
                "Month",
                "Sales",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );


        TextTitle title = new TextTitle("Sales YTD", new Font("Arial", Font.BOLD, 18));
        title.setPaint(Styler.TEXT_COLOR);

        chart.setTitle(title);

        return chart;
    }

    // TODO: remove this
    private static int randomizeInt(int min, int max) {
        Random r = new Random();
        return (r.nextInt(max - min) + min);
    }
}