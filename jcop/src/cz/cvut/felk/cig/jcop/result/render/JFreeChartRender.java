/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.solver.message.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
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
    protected XYSeriesCollection dataSet;
    protected XYSeries activeSeries;
    protected int optimizeCounter;
    protected double lastBestFitness;
    protected JFreeChart chart;
    protected boolean insertLast = true;

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


        // create and display a frame...
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
            if (this.activeSeries != null && this.insertLast) this.activeSeries.add(this.optimizeCounter, this.lastBestFitness);
        }
    }

    public JFreeChart getChart() {
        return chart;
    }

    public JFreeChartRender useFormatA () {
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(false);
        chart.getLegend().setItemFont(new Font("Dialog", Font.PLAIN, 9));
        chart.setBackgroundPaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.gray);
        this.insertLast = false;

        return this;
    }

    public JFreeChartRender removeLegend () {
        this.chart.removeLegend();

        return this;
    }
}
