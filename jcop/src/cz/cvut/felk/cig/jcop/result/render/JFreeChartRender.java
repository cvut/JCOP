/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.solver.message.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

/**
 * Listens to solver and draws graph with best solutions.
 *
 * @author Ondrej Skalicka
 */
public class JFreeChartRender implements MessageListener {
    /**
     * Dataset containing all series.
     */
    protected XYSeriesCollection dataSet;
    /**
     * Active series to add new values to.
     */
    protected XYSeries activeSeries;
    /**
     * Number of iterations for current active series.
     */
    protected int optimizeCounter;
    /**
     * Last best fitness, used if {@link #insertLast} is true.
     */
    protected double lastBestFitness;
    /**
     * Chart which we render to.
     */
    protected JFreeChart chart;
    /**
     * If true, when a solver ends, one last point will be added to chart.
     */
    protected boolean insertLast = false;
    /**
     * Chart style used in the thesis.
     * <p/>
     * Uses:
     * <p/>
     * <ul> <li>setBaseShapesVisible(true)</li> <li>setBaseShapesFilled(false)</li> <li>setBaseLinesVisible(false)</li>
     * <li>setLegendItemFont(new Font("Dialog", Font.PLAIN, 9))</li> <li>setBackgroundPaint(Color.white)</li>
     * <li>setGridPaint(Color.gray)</li> <li>setInsertLast(false)</li> <li>removeLegend()</li> </ul>
     */
    public final static int STYLE_THESIS = 0;
    /**
     * Chart style used in the thesis.
     * <p/>
     * Uses:
     * <p/>
     * <ul> <li>setBaseShapesVisible(true)</li> <li>setBaseShapesFilled(false)</li> <li>setBaseLinesVisible(false)</li>
     * <li>setLegendItemFont(new Font("Dialog", Font.PLAIN, 9))</li> <li>setBackgroundPaint(Color.white)</li>
     * <li>setGridPaint(Color.gray)</li> <li>setInsertLast(false)</li> </ul>
     */
    public final static int STYLE_THESIS_LEGEND = 1;

    /**
     * Creates new JFreeChartRender with given title.
     *
     * @param title title of the chart
     */
    public JFreeChartRender(String title) {
        dataSet = new XYSeriesCollection();

        chart = ChartFactory.createXYLineChart(
                title, // chart title
                "Optimization counter", // x axis label
                "Fitness", // y axis label
                dataSet, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }

    public void onMessage(Message message) {
        if (message instanceof MessageSolverStart) {
            MessageSolverStart messageSolverStart = (MessageSolverStart) message;
            this.activeSeries = new XYSeries(messageSolverStart.getAlgorithm() + "/" + messageSolverStart.getObjectiveProblem());

            this.dataSet.addSeries(this.activeSeries);
            this.optimizeCounter = 0;
            return;
        }
        if (message instanceof MessageOptimize) {
            this.optimizeCounter++;
            return;
        }
        if (message instanceof MessageBetterConfigurationFound) {
            this.lastBestFitness = ((MessageBetterConfigurationFound) message).getFitness();

            this.activeSeries.add(this.optimizeCounter, this.lastBestFitness);
            return;
        }
        if (message instanceof MessageSolverStop) {
            if (this.activeSeries != null && this.insertLast)
                this.activeSeries.add(this.optimizeCounter, this.lastBestFitness);
        }
    }

    /**
     * Returns render for this chart.
     *
     * @return render for this chart.
     */
    protected XYLineAndShapeRenderer getRenderer() {
        return ((XYLineAndShapeRenderer) ((XYPlot) chart.getPlot()).getRenderer());
    }

    /**
     * Returns plot for this chart.
     *
     * @return plot for this chart.
     */
    protected XYPlot getPlot() {
        return (XYPlot) chart.getPlot();
    }

    /**
     * Returns chart for this render.
     *
     * This method should be used for customization of chart if no better public method is provided by JFreeChartRender.
     *
     * @return chart for this render
     */
    public JFreeChart getChart () {
        return this.chart;
    }

    /**
     * Sets if shapes are drawn in addition to lines.
     * <p/>
     * Default false.
     *
     * @param baseShapesVisible if true, shapes will be drawn in addition to lines.
     * @return itself (fluent interface)
     */
    public JFreeChartRender setBaseShapesVisible(boolean baseShapesVisible) {
        getRenderer().setBaseShapesVisible(baseShapesVisible);
        return this;
    }

    /**
     * Sets if shapes are filled or not.
     * <p/>
     * Default true.
     *
     * @param baseShapesFilled if true, shapes are filled
     * @return itself (fluent interface)
     */
    public JFreeChartRender setBaseShapesFilled(boolean baseShapesFilled) {
        getRenderer().setBaseShapesFilled(baseShapesFilled);
        return this;
    }

    /**
     * Sets if plot points are connected with lines.
     * <p/>
     * Default true.
     *
     * @param baseLinesVisible if true, plot points are connected with lines
     * @return itself (fluent interface)
     */
    public JFreeChartRender setBaseLinesVisible(boolean baseLinesVisible) {
        getRenderer().setBaseLinesVisible(baseLinesVisible);
        return this;
    }

    /**
     * Sets font for legend.
     *
     * @param font new font for legend
     * @return itself (fluent interface)
     */
    public JFreeChartRender setLegendItemFont(Font font) {
        chart.getLegend().setItemFont(font);
        return this;
    }

    /**
     * Sets background color for chart (both chart and plot).
     *
     * @param color new background color
     * @return itself (fluent interface)
     */
    public JFreeChartRender setBackgroundPaint(Color color) {
        chart.setBackgroundPaint(color);
        getPlot().setBackgroundPaint(color);
        return this;
    }

    /**
     * Sets color for grid in chart.
     * <p/>
     * Default grey.
     *
     * @param color new color for chart grid
     * @return itself (fluent interface)
     */
    public JFreeChartRender setGridPaint(Color color) {
        getPlot().setRangeGridlinePaint(color);
        getPlot().setDomainGridlinePaint(color);
        return this;
    }

    /**
     * Control if last element is added to plot on {@link cz.cvut.felk.cig.jcop.solver.message.MessageSolverStop}.
     * <p/>
     * Default false.
     *
     * @param insertLast if true, one more point is added to plot
     * @return itself (fluent interface)
     */
    public JFreeChartRender setInsertLast(boolean insertLast) {
        this.insertLast = insertLast;

        return this;
    }

    /**
     * Removes legend from chart.
     * <p/>
     * Note that this is irreversible chane.
     *
     * @return itself (fluent interface)
     */
    public JFreeChartRender removeLegend() {
        this.chart.removeLegend();

        return this;
    }

    /**
     * Sets bounds for domain axis.
     *
     * @param lowerBound lower domain bound
     * @param upperBound upper domain bound
     * @return itself (fluent interface)
     */
    public JFreeChartRender setDomainAxis(double lowerBound, double upperBound) {
        ValueAxis valueAxis = getPlot().getDomainAxis();
        valueAxis.setUpperBound(upperBound);
        valueAxis.setLowerBound(lowerBound);
        return this;
    }

    /**
     * Sets bounds for range axis.
     *
     * @param lowerBound lower range bound
     * @param upperBound upper range bound
     * @return itself (fluent interface)
     */
    public JFreeChartRender setRangeAxis(double lowerBound, double upperBound) {
        ValueAxis valueAxis = getPlot().getRangeAxis();
        valueAxis.setUpperBound(upperBound);
        valueAxis.setLowerBound(lowerBound);
        return this;
    }

    /**
     * Applies prepared style to a chart.
     *
     * Recognizes {@link JFreeChartRender#STYLE_THESIS} and {@link JFreeChartRender#STYLE_THESIS_LEGEND}.
     *
     * @param style code of style
     * @return updated chart
     */
    public JFreeChartRender setStyle(int style) {
        switch (style) {
            case JFreeChartRender.STYLE_THESIS:
                return this.setBaseShapesVisible(true).
                        setBaseShapesFilled(false).
                        setBaseLinesVisible(false).
                        setLegendItemFont(new Font("Dialog", Font.PLAIN, 9)).
                        setBackgroundPaint(Color.white).
                        setGridPaint(Color.gray).
                        setInsertLast(false).
                        removeLegend();
            case JFreeChartRender.STYLE_THESIS_LEGEND:
                return this.setBaseShapesVisible(true).
                        setBaseShapesFilled(false).
                        setBaseLinesVisible(false).
                        setLegendItemFont(new Font("Dialog", Font.PLAIN, 9)).
                        setBackgroundPaint(Color.white).
                        setGridPaint(Color.gray).
                        setInsertLast(false);
            default:
                return this;
        }
    }
}
