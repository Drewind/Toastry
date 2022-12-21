/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * -----------------------
 * StandardChartTheme.java
 * -----------------------
 * (C) Copyright 2008-2020, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package Graphics.Charts;

import Utilities.Styler;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.*;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.GradientXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.*;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.*;

import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * Custom implementation of {@link ChartTheme} interface.
 * Designed to fit inside Toastry.
 */
public class ToastryChartTheme implements ChartTheme, Cloneable,
        PublicCloneable, Serializable {

    /** The name of this theme. */
    private final String name;

    /**
     * The largest font size.  Use for the main chart title.
     */
    private Font extraLargeFont;

    /**
     * A large font.  Used for subtitles.
     */
    private Font largeFont;

    /**
     * The regular font size.  Used for axis tick labels, legend items etc.
     */
    private Font regularFont;

    /**
     * The small font size.
     */
    private Font smallFont;

    /** The paint used to display the main chart title. */
    private transient Paint titlePaint;

    /** The paint used to display subtitles. */
    private transient Paint subtitlePaint;

    /** The background paint for the chart. */
    private transient Paint chartBackgroundPaint;

    /** The legend background paint. */
    private transient Paint legendBackgroundPaint;

    /** The legend item paint. */
    private transient Paint legendItemPaint;

    /** The drawing supplier. */
    private DrawingSupplier drawingSupplier;

    /** The background paint for the plot. */
    private transient Paint plotBackgroundPaint;

    /** The plot outline paint. */
    private transient Paint plotOutlinePaint;

    /** The label link style for pie charts. */
    private PieLabelLinkStyle labelLinkStyle;

    /** The label link paint for pie charts. */
    private transient Paint labelLinkPaint;

    /** The domain grid line paint. */
    private transient Paint domainGridlinePaint;

    /** The range grid line paint. */
    private transient Paint rangeGridlinePaint;

    /* The baseline paint (used for domain and range zero baselines). */
    private transient Paint baselinePaint;

    /** The crosshair paint. */
    private transient Paint crosshairPaint;

    /** The axis offsets. */
    private RectangleInsets axisOffset;

    /** The axis label paint. */
    private transient Paint axisLabelPaint;

    /** The tick label paint. */
    private transient Paint tickLabelPaint;

    /** The item label paint. */
    private transient Paint itemLabelPaint;

    /**
     * A flag that controls whether shadows are visible (for example,
     * in a bar renderer).
     */
    private boolean shadowVisible;

    /** The shadow paint. */
    private transient Paint shadowPaint;

    /** The bar painter. */
    private BarPainter barPainter;

    /** The XY bar painter. */
    private XYBarPainter xyBarPainter;

    /** The thermometer paint. */
    private transient Paint thermometerPaint;

    /** The error indicator paint for the {@link StatisticalBarRenderer}. */
    private transient Paint errorIndicatorPaint;

    /** The grid band paint for a {@link SymbolAxis}. */
    private transient Paint gridBandPaint = SymbolAxis.DEFAULT_GRID_BAND_PAINT;

    /** The grid band alternate paint for a {@link SymbolAxis}. */
    private transient Paint gridBandAlternatePaint
            = SymbolAxis.DEFAULT_GRID_BAND_ALTERNATE_PAINT;

    /* The shadow generator (can be null). */
    private final ShadowGenerator shadowGenerator;

    /**
     * Creates a new default instance.
     *
     * @param name  the name of the theme ({@code null} not permitted).
     */
    public ToastryChartTheme(String name) {
        this(name, false);
    }

    /**
     * Creates a new default instance.
     *
     * @param name  the name of the theme ({@code null} not permitted).
     * @param shadow  a flag that controls whether a shadow generator is
     *                included.
     */
    public ToastryChartTheme(String name, boolean shadow) {
        Args.nullNotPermitted(name, "name");
        this.name = name;
        this.extraLargeFont = new Font("Arial", Font.BOLD, 20);
        this.largeFont = new Font("Arial", Font.BOLD, 14);
        this.regularFont = new Font("Arial", Font.PLAIN, 12);
        this.smallFont = new Font("Arial", Font.PLAIN, 10);
        this.titlePaint = Color.WHITE;
        this.subtitlePaint = Color.BLACK;
        this.legendBackgroundPaint = Color.RED;
        this.legendItemPaint = Color.WHITE;
        this.chartBackgroundPaint = Styler.CONTAINER_BACKGROUND;
        this.drawingSupplier = new DefaultDrawingSupplier();
        this.plotBackgroundPaint = Styler.CONTAINER_BACKGROUND.brighter();
        this.plotOutlinePaint = Styler.THEME_COLOR;
        this.labelLinkPaint = Styler.THEME_COLOR;
        this.labelLinkStyle = PieLabelLinkStyle.CUBIC_CURVE;
        this.axisOffset = new RectangleInsets(4, 4, 4, 4);
        this.domainGridlinePaint = Color.WHITE;
        this.rangeGridlinePaint = Color.WHITE;
        this.baselinePaint = Styler.THEME_COLOR;
        this.crosshairPaint = Color.BLUE;
        this.axisLabelPaint = Styler.TEXT_COLOR.darker();
        this.tickLabelPaint = Styler.TEXT_COLOR.darker();
        this.barPainter = new GradientBarPainter();
        this.xyBarPainter = new GradientXYBarPainter();
        this.shadowVisible = false;
        this.shadowPaint = Color.GRAY;
        this.itemLabelPaint = Color.BLACK;
        this.thermometerPaint = Color.WHITE;
        this.errorIndicatorPaint = Color.BLACK;
        this.shadowGenerator = shadow ? new DefaultShadowGenerator() : null;
    }

    public void convertToLightMode() {
        Args.nullNotPermitted(name, "name");
        this.extraLargeFont = new Font("Arial", Font.BOLD, 20);
        this.largeFont = new Font("Arial", Font.BOLD, 14);
        this.regularFont = new Font("Arial", Font.PLAIN, 12);
        this.smallFont = new Font("Arial", Font.PLAIN, 10);
        this.titlePaint = Color.WHITE;
        this.subtitlePaint = Color.BLACK;
        this.legendBackgroundPaint = Color.RED;
        this.legendItemPaint = Color.WHITE;
        this.chartBackgroundPaint = Styler.CONTAINER_BACKGROUND;
        this.drawingSupplier = new DefaultDrawingSupplier();
        this.plotBackgroundPaint = Styler.CONTAINER_BACKGROUND.brighter();
        this.plotOutlinePaint = Styler.THEME_COLOR;
        this.labelLinkPaint = Styler.THEME_COLOR;
        this.labelLinkStyle = PieLabelLinkStyle.CUBIC_CURVE;
        this.axisOffset = new RectangleInsets(4, 4, 4, 4);
        this.domainGridlinePaint = Color.WHITE;
        this.rangeGridlinePaint = Color.WHITE;
        this.baselinePaint = Styler.THEME_COLOR;
        this.crosshairPaint = Color.BLUE;
        this.axisLabelPaint = Styler.TEXT_COLOR.darker();
        this.tickLabelPaint = Styler.TEXT_COLOR.darker();
        this.barPainter = new GradientBarPainter();
        this.xyBarPainter = new GradientXYBarPainter();
        this.shadowVisible = false;
        this.shadowPaint = Color.GRAY;
        this.itemLabelPaint = Color.BLACK;
        this.thermometerPaint = Color.WHITE;
        this.errorIndicatorPaint = Color.BLACK;
    }

    /**
     * Returns the name of this theme.
     *
     * @return The name of this theme.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a clone of the drawing supplier for this theme.
     *
     * @return A clone of the drawing supplier.
     */
    public DrawingSupplier getDrawingSupplier() {
        DrawingSupplier result = null;
        if (this.drawingSupplier instanceof PublicCloneable pc) {
            try {
                result = (DrawingSupplier) pc.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Applies this theme to the supplied chart.
     *
     * @param chart  the chart ({@code null} not permitted).
     */
    @Override
    public void apply(JFreeChart chart) {
        Args.nullNotPermitted(chart, "chart");
        TextTitle title = chart.getTitle();
        if (title != null) {
            title.setFont(this.extraLargeFont);
            title.setPaint(this.titlePaint);
        }

        int subtitleCount = chart.getSubtitleCount();
        for (int i = 0; i < subtitleCount; i++) {
            applyToTitle(chart.getSubtitle(i));
        }

        chart.setBackgroundPaint(this.chartBackgroundPaint);

        // now process the plot if there is one
        Plot plot = chart.getPlot();
        if (plot != null) {
            applyToPlot(plot);
        }
    }

    /**
     * Applies the attributes of this theme to the specified title.
     *
     * @param title  the title.
     */
    protected void applyToTitle(Title title) {
        if (title instanceof TextTitle tt) {
            tt.setFont(this.largeFont);
            tt.setPaint(this.subtitlePaint);
        }
        else if (title instanceof LegendTitle lt) {
            if (lt.getBackgroundPaint() != null) {
                lt.setBackgroundPaint(this.legendBackgroundPaint);
            }
            lt.setItemFont(this.regularFont);
            lt.setItemPaint(this.legendItemPaint);
            if (lt.getWrapper() != null) {
                applyToBlockContainer(lt.getWrapper());
            }
        }
        else if (title instanceof PaintScaleLegend psl) {
            psl.setBackgroundPaint(this.legendBackgroundPaint);
            ValueAxis axis = psl.getAxis();
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }
        else if (title instanceof CompositeTitle ct) {
            BlockContainer bc = ct.getContainer();
            List blocks = bc.getBlocks();
            for (Object block : blocks) {
                Block b = (Block) block;
                if (b instanceof Title) {
                    applyToTitle((Title) b);
                }
            }
        }
    }

    /**
     * Applies the attributes of this theme to the specified container.
     *
     * @param bc  a block container ({@code null} not permitted).
     */
    protected void applyToBlockContainer(BlockContainer bc) {
        for (Object o : bc.getBlocks()) {
            Block b = (Block) o;
            applyToBlock(b);
        }
    }

    /**
     * Applies the attributes of this theme to the specified block.
     *
     * @param b  the block.
     */
    protected void applyToBlock(Block b) {
        if (b instanceof Title) {
            applyToTitle((Title) b);
        }
        else if (b instanceof LabelBlock lb) {
            lb.setFont(this.regularFont);
            lb.setPaint(this.legendItemPaint);
        }
    }

    /**
     * Applies the attributes of this theme to a plot.
     *
     * @param plot  the plot ({@code null}).
     */
    protected void applyToPlot(Plot plot) {
        Args.nullNotPermitted(plot, "plot");
        if (plot.getDrawingSupplier() != null) {
            plot.setDrawingSupplier(getDrawingSupplier());
        }
        if (plot.getBackgroundPaint() != null) {
            plot.setBackgroundPaint(this.plotBackgroundPaint);
        }
        plot.setOutlinePaint(this.plotOutlinePaint);

        // now handle specific plot types (and yes, I know this is some
        // hideous code that has to be manually updated any time a new
        // plot type is added - I should have written something much cooler,
        // but I didn't and neither did anyone else).
        if (plot instanceof PiePlot) {
            applyToPiePlot((PiePlot) plot);
        }
        else if (plot instanceof MultiplePiePlot) {
            applyToMultiplePiePlot((MultiplePiePlot) plot);
        }
        else if (plot instanceof CategoryPlot) {
            applyToCategoryPlot((CategoryPlot) plot);
        }
        else if (plot instanceof XYPlot) {
            applyToXYPlot((XYPlot) plot);
        }
        else if (plot instanceof FastScatterPlot) {
            applyToFastScatterPlot((FastScatterPlot) plot);
        }
        else if (plot instanceof MeterPlot) {
            applyToMeterPlot((MeterPlot) plot);
        }
        else if (plot instanceof ThermometerPlot) {
            applyToThermometerPlot((ThermometerPlot) plot);
        }
        else if (plot instanceof SpiderWebPlot) {
            applyToSpiderWebPlot((SpiderWebPlot) plot);
        }
        else if (plot instanceof PolarPlot) {
            applyToPolarPlot((PolarPlot) plot);
        }
    }

    /**
     * Applies the attributes of this theme to a {@link PiePlot} instance.
     * This method also clears any set values for the section paint, outline
     * etc., so that the theme's {@link DrawingSupplier} will be used.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToPiePlot(PiePlot plot) {
        plot.setLabelLinkPaint(this.labelLinkPaint);
        plot.setLabelLinkStyle(this.labelLinkStyle);
        plot.setLabelFont(this.regularFont);
        plot.setShadowGenerator(this.shadowGenerator);

        // clear the section attributes so that the theme's DrawingSupplier
        // will be used
        if (plot.getAutoPopulateSectionPaint()) {
            plot.clearSectionPaints(false);
        }
        if (plot.getAutoPopulateSectionOutlinePaint()) {
            plot.clearSectionOutlinePaints(false);
        }
        if (plot.getAutoPopulateSectionOutlineStroke()) {
            plot.clearSectionOutlineStrokes(false);
        }
    }

    /**
     * Applies the attributes of this theme to a {@link MultiplePiePlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToMultiplePiePlot(MultiplePiePlot plot) {
        apply(plot.getPieChart());
    }

    /**
     * Applies the attributes of this theme to a {@link CategoryPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToCategoryPlot(CategoryPlot plot) {
        plot.setAxisOffset(this.axisOffset);
        plot.setDomainGridlinePaint(this.domainGridlinePaint);
        plot.setRangeGridlinePaint(this.rangeGridlinePaint);
        plot.setRangeZeroBaselinePaint(this.baselinePaint);
        plot.setShadowGenerator(this.shadowGenerator);

        // process all domain axes
        int domainAxisCount = plot.getDomainAxisCount();
        for (int i = 0; i < domainAxisCount; i++) {
            CategoryAxis axis = plot.getDomainAxis(i);
            if (axis != null) {
                applyToCategoryAxis(axis);
            }
        }

        // process all range axes
        int rangeAxisCount = plot.getRangeAxisCount();
        for (int i = 0; i < rangeAxisCount; i++) {
            ValueAxis axis = plot.getRangeAxis(i);
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }

        // process all renderers
        int rendererCount = plot.getRendererCount();
        for (int i = 0; i < rendererCount; i++) {
            CategoryItemRenderer r = plot.getRenderer(i);
            if (r != null) {
                applyToCategoryItemRenderer(r);
            }
        }

        if (plot instanceof CombinedDomainCategoryPlot cp) {
            for (Object o : cp.getSubplots()) {
                CategoryPlot subplot = (CategoryPlot) o;
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
        if (plot instanceof CombinedRangeCategoryPlot cp) {
            for (Object o : cp.getSubplots()) {
                CategoryPlot subplot = (CategoryPlot) o;
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
    }

    /**
     * Applies the attributes of this theme to a {@link XYPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToXYPlot(XYPlot plot) {
        plot.setAxisOffset(this.axisOffset);
        plot.setDomainZeroBaselinePaint(this.baselinePaint);
        plot.setRangeZeroBaselinePaint(this.baselinePaint);
        plot.setDomainGridlinePaint(this.domainGridlinePaint);
        plot.setRangeGridlinePaint(this.rangeGridlinePaint);
        plot.setDomainCrosshairPaint(this.crosshairPaint);
        plot.setRangeCrosshairPaint(this.crosshairPaint);
        plot.setShadowGenerator(this.shadowGenerator);

        // process all domain axes
        int domainAxisCount = plot.getDomainAxisCount();
        for (int i = 0; i < domainAxisCount; i++) {
            ValueAxis axis = plot.getDomainAxis(i);
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }

        // process all range axes
        int rangeAxisCount = plot.getRangeAxisCount();
        for (int i = 0; i < rangeAxisCount; i++) {
            ValueAxis axis = plot.getRangeAxis(i);
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }

        // process all renderers
        int rendererCount = plot.getRendererCount();
        for (int i = 0; i < rendererCount; i++) {
            XYItemRenderer r = plot.getRenderer(i);
            if (r != null) {
                applyToXYItemRenderer(r);
            }
        }

        // process all annotations
        for (Object o : plot.getAnnotations()) {
            XYAnnotation a = (XYAnnotation) o;
            applyToXYAnnotation(a);
        }

        if (plot instanceof CombinedDomainXYPlot cp) {
            for (Object o : cp.getSubplots()) {
                XYPlot subplot = (XYPlot) o;
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
        if (plot instanceof CombinedRangeXYPlot cp) {
            for (Object o : cp.getSubplots()) {
                XYPlot subplot = (XYPlot) o;
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
    }

    /**
     * Applies the attributes of this theme to a {@link FastScatterPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToFastScatterPlot(FastScatterPlot plot) {
        plot.setDomainGridlinePaint(this.domainGridlinePaint);
        plot.setRangeGridlinePaint(this.rangeGridlinePaint);
        ValueAxis xAxis = plot.getDomainAxis();
        if (xAxis != null) {
            applyToValueAxis(xAxis);
        }
        ValueAxis yAxis = plot.getRangeAxis();
        if (yAxis != null) {
            applyToValueAxis(yAxis);
        }

    }

    /**
     * Applies the attributes of this theme to a {@link PolarPlot}.  This
     * method is called from the {@link #applyToPlot(Plot)} method.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToPolarPlot(PolarPlot plot) {
        plot.setAngleLabelFont(this.regularFont);
        plot.setAngleLabelPaint(this.tickLabelPaint);
        plot.setAngleGridlinePaint(this.domainGridlinePaint);
        plot.setRadiusGridlinePaint(this.rangeGridlinePaint);
        ValueAxis axis = plot.getAxis();
        if (axis != null) {
            applyToValueAxis(axis);
        }
    }

    /**
     * Applies the attributes of this theme to a {@link SpiderWebPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToSpiderWebPlot(SpiderWebPlot plot) {
        plot.setLabelFont(this.regularFont);
        plot.setLabelPaint(this.axisLabelPaint);
        plot.setAxisLinePaint(this.axisLabelPaint);
    }

    /**
     * Applies the attributes of this theme to a {@link MeterPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToMeterPlot(MeterPlot plot) {
        plot.setDialBackgroundPaint(this.plotBackgroundPaint);
        plot.setValueFont(this.largeFont);
        plot.setValuePaint(this.axisLabelPaint);
        plot.setDialOutlinePaint(this.plotOutlinePaint);
        plot.setNeedlePaint(this.thermometerPaint);
        plot.setTickLabelFont(this.regularFont);
        plot.setTickLabelPaint(this.tickLabelPaint);
    }

    /**
     * Applies the attributes for this theme to a {@link ThermometerPlot}.
     * This method is called from the {@link #applyToPlot(Plot)} method.
     *
     * @param plot  the plot.
     */
    protected void applyToThermometerPlot(ThermometerPlot plot) {
        plot.setValueFont(this.largeFont);
        plot.setThermometerPaint(this.thermometerPaint);
        ValueAxis axis = plot.getRangeAxis();
        if (axis != null) {
            applyToValueAxis(axis);
        }
    }

    /**
     * Applies the attributes for this theme to a {@link CategoryAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToCategoryAxis(CategoryAxis axis) {
        axis.setLabelFont(this.largeFont);
        axis.setLabelPaint(this.axisLabelPaint);
        axis.setTickLabelFont(this.regularFont);
        axis.setTickLabelPaint(this.tickLabelPaint);
        if (axis instanceof SubCategoryAxis sca) {
            sca.setSubLabelFont(this.regularFont);
            sca.setSubLabelPaint(this.tickLabelPaint);
        }
    }

    /**
     * Applies the attributes for this theme to a {@link ValueAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToValueAxis(ValueAxis axis) {
        axis.setLabelFont(this.largeFont);
        axis.setLabelPaint(this.axisLabelPaint);
        axis.setTickLabelFont(this.regularFont);
        axis.setTickLabelPaint(this.tickLabelPaint);
        if (axis instanceof SymbolAxis) {
            applyToSymbolAxis((SymbolAxis) axis);
        }
        if (axis instanceof PeriodAxis) {
            applyToPeriodAxis((PeriodAxis) axis);
        }
    }

    /**
     * Applies the attributes for this theme to a {@link SymbolAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToSymbolAxis(SymbolAxis axis) {
        axis.setGridBandPaint(this.gridBandPaint);
        axis.setGridBandAlternatePaint(this.gridBandAlternatePaint);
    }

    /**
     * Applies the attributes for this theme to a {@link PeriodAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToPeriodAxis(PeriodAxis axis) {
        PeriodAxisLabelInfo[] info = axis.getLabelInfo();
        for (int i = 0; i < info.length; i++) {
            PeriodAxisLabelInfo e = info[i];
            PeriodAxisLabelInfo n = new PeriodAxisLabelInfo(e.getPeriodClass(),
                    e.getDateFormat(), e.getPadding(), this.regularFont,
                    this.tickLabelPaint, e.getDrawDividers(),
                    e.getDividerStroke(), e.getDividerPaint());
            info[i] = n;
        }
        axis.setLabelInfo(info);
    }

    /**
     * Applies the attributes for this theme to an {@link AbstractRenderer}.
     *
     * @param renderer  the renderer ({@code null} not permitted).
     */
    protected void applyToAbstractRenderer(AbstractRenderer renderer) {
        if (renderer.getAutoPopulateSeriesPaint()) {
            renderer.clearSeriesPaints(false);
        }
        if (renderer.getAutoPopulateSeriesStroke()) {
            renderer.clearSeriesStrokes(false);
        }
    }

    /**
     * Applies the settings of this theme to the specified renderer.
     *
     * @param renderer  the renderer ({@code null} not permitted).
     */
    protected void applyToCategoryItemRenderer(CategoryItemRenderer renderer) {
        Args.nullNotPermitted(renderer, "renderer");

        if (renderer instanceof AbstractRenderer) {
            applyToAbstractRenderer((AbstractRenderer) renderer);
        }

        renderer.setDefaultItemLabelFont(this.regularFont);
        renderer.setDefaultItemLabelPaint(this.itemLabelPaint);

        // now we handle some special cases - yes, UGLY code alert!

        // BarRenderer
        if (renderer instanceof BarRenderer br) {
            br.setBarPainter(this.barPainter);
            br.setShadowVisible(this.shadowVisible);
            br.setShadowPaint(this.shadowPaint);
        }


        //  StatisticalBarRenderer
        if (renderer instanceof StatisticalBarRenderer sbr) {
            sbr.setErrorIndicatorPaint(this.errorIndicatorPaint);
        }

        // MinMaxCategoryRenderer
        if (renderer instanceof MinMaxCategoryRenderer mmcr) {
            mmcr.setGroupPaint(this.errorIndicatorPaint);
        }
    }

    /**
     * Applies the settings of this theme to the specified renderer.
     *
     * @param renderer  the renderer ({@code null} not permitted).
     */
    protected void applyToXYItemRenderer(XYItemRenderer renderer) {
        Args.nullNotPermitted(renderer, "renderer");
        if (renderer instanceof AbstractRenderer) {
            applyToAbstractRenderer((AbstractRenderer) renderer);
        }
        renderer.setDefaultItemLabelFont(this.regularFont);
        renderer.setDefaultItemLabelPaint(this.itemLabelPaint);
        if (renderer instanceof XYBarRenderer br) {
            br.setBarPainter(this.xyBarPainter);
            br.setShadowVisible(this.shadowVisible);
        }
    }

    /**
     * Applies the settings of this theme to the specified annotation.
     *
     * @param annotation  the annotation.
     */
    protected void applyToXYAnnotation(XYAnnotation annotation) {
        Args.nullNotPermitted(annotation, "annotation");
        if (annotation instanceof XYTextAnnotation xyta) {
            xyta.setFont(this.smallFont);
            xyta.setPaint(this.itemLabelPaint);
        }
    }

    /**
     * Tests this theme for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.jfree.chart.StandardChartTheme)) {
            return false;
        }
        ToastryChartTheme that = (ToastryChartTheme) obj;
        if (!this.name.equals(that.name)) {
            return false;
        }
        if (!this.extraLargeFont.equals(that.extraLargeFont)) {
            return false;
        }
        if (!this.largeFont.equals(that.largeFont)) {
            return false;
        }
        if (!this.regularFont.equals(that.regularFont)) {
            return false;
        }
        if (!this.smallFont.equals(that.smallFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.titlePaint, that.titlePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.subtitlePaint, that.subtitlePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.chartBackgroundPaint,
                that.chartBackgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.legendBackgroundPaint,
                that.legendBackgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.legendItemPaint, that.legendItemPaint)) {
            return false;
        }
        if (!this.drawingSupplier.equals(that.drawingSupplier)) {
            return false;
        }
        if (!PaintUtils.equal(this.plotBackgroundPaint,
                that.plotBackgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.plotOutlinePaint,
                that.plotOutlinePaint)) {
            return false;
        }
        if (!this.labelLinkStyle.equals(that.labelLinkStyle)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelLinkPaint, that.labelLinkPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.domainGridlinePaint,
                that.domainGridlinePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.rangeGridlinePaint,
                that.rangeGridlinePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.crosshairPaint, that.crosshairPaint)) {
            return false;
        }
        if (!this.axisOffset.equals(that.axisOffset)) {
            return false;
        }
        if (!PaintUtils.equal(this.axisLabelPaint, that.axisLabelPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.tickLabelPaint, that.tickLabelPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.itemLabelPaint, that.itemLabelPaint)) {
            return false;
        }
        if (this.shadowVisible != that.shadowVisible) {
            return false;
        }
        if (!PaintUtils.equal(this.shadowPaint, that.shadowPaint)) {
            return false;
        }
        if (!this.barPainter.equals(that.barPainter)) {
            return false;
        }
        if (!this.xyBarPainter.equals(that.xyBarPainter)) {
            return false;
        }
        if (!PaintUtils.equal(this.thermometerPaint,
                that.thermometerPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.errorIndicatorPaint,
                that.errorIndicatorPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.gridBandPaint, that.gridBandPaint)) {
            return false;
        }
        return PaintUtils.equal(this.gridBandAlternatePaint,
                that.gridBandAlternatePaint);
    }

    /**
     * Returns a clone of this theme.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the theme cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     */
    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.titlePaint, stream);
        SerialUtils.writePaint(this.subtitlePaint, stream);
        SerialUtils.writePaint(this.chartBackgroundPaint, stream);
        SerialUtils.writePaint(this.legendBackgroundPaint, stream);
        SerialUtils.writePaint(this.legendItemPaint, stream);
        SerialUtils.writePaint(this.plotBackgroundPaint, stream);
        SerialUtils.writePaint(this.plotOutlinePaint, stream);
        SerialUtils.writePaint(this.labelLinkPaint, stream);
        SerialUtils.writePaint(this.baselinePaint, stream);
        SerialUtils.writePaint(this.domainGridlinePaint, stream);
        SerialUtils.writePaint(this.rangeGridlinePaint, stream);
        SerialUtils.writePaint(this.crosshairPaint, stream);
        SerialUtils.writePaint(this.axisLabelPaint, stream);
        SerialUtils.writePaint(this.tickLabelPaint, stream);
        SerialUtils.writePaint(this.itemLabelPaint, stream);
        SerialUtils.writePaint(this.shadowPaint, stream);
        SerialUtils.writePaint(this.thermometerPaint, stream);
        SerialUtils.writePaint(this.errorIndicatorPaint, stream);
        SerialUtils.writePaint(this.gridBandPaint, stream);
        SerialUtils.writePaint(this.gridBandAlternatePaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    @Serial
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.titlePaint = SerialUtils.readPaint(stream);
        this.subtitlePaint = SerialUtils.readPaint(stream);
        this.chartBackgroundPaint = SerialUtils.readPaint(stream);
        this.legendBackgroundPaint = SerialUtils.readPaint(stream);
        this.legendItemPaint = SerialUtils.readPaint(stream);
        this.plotBackgroundPaint = SerialUtils.readPaint(stream);
        this.plotOutlinePaint = SerialUtils.readPaint(stream);
        this.labelLinkPaint = SerialUtils.readPaint(stream);
        this.baselinePaint = SerialUtils.readPaint(stream);
        this.domainGridlinePaint = SerialUtils.readPaint(stream);
        this.rangeGridlinePaint = SerialUtils.readPaint(stream);
        this.crosshairPaint = SerialUtils.readPaint(stream);
        this.axisLabelPaint = SerialUtils.readPaint(stream);
        this.tickLabelPaint = SerialUtils.readPaint(stream);
        this.itemLabelPaint = SerialUtils.readPaint(stream);
        this.shadowPaint = SerialUtils.readPaint(stream);
        this.thermometerPaint = SerialUtils.readPaint(stream);
        this.errorIndicatorPaint = SerialUtils.readPaint(stream);
        this.gridBandPaint = SerialUtils.readPaint(stream);
        this.gridBandAlternatePaint = SerialUtils.readPaint(stream);
    }
}