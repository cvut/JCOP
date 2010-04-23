/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.solver.message.MessageBetterConfigurationFound;
import cz.cvut.felk.cig.jcop.solver.message.MessageOptimize;
import cz.cvut.felk.cig.jcop.solver.message.MessageSolverStart;

/**
 * @author Savannah
 */
public class DemoJFreeChartSettings {
    protected static void addDummyValues(JFreeChartRender jFreeChartRender) {
        jFreeChartRender.onMessage(new MessageSolverStart(new SimulatedAnnealing(), null));
        jFreeChartRender.onMessage(new MessageOptimize());
        jFreeChartRender.onMessage(new MessageBetterConfigurationFound(null, 0.5));
        jFreeChartRender.onMessage(new MessageOptimize());
        jFreeChartRender.onMessage(new MessageOptimize());
        jFreeChartRender.onMessage(new MessageBetterConfigurationFound(null, 1));
        jFreeChartRender.onMessage(new MessageOptimize());
        jFreeChartRender.onMessage(new MessageOptimize());
        jFreeChartRender.onMessage(new MessageOptimize());
        jFreeChartRender.onMessage(new MessageBetterConfigurationFound(null, 4));
    }

    public static void main(String[] args) {
        DemoJFreeChartSettings.addDummyValues(new JFreeChartRender("SetBaseShapesVisible").setBaseShapesVisible(true));
        DemoJFreeChartSettings.addDummyValues(new JFreeChartRender("SetBaseShapesVisible").setBaseShapesVisible(true));
        DemoJFreeChartSettings.addDummyValues(new JFreeChartRender("SetBaseShapesVisible").setBaseShapesVisible(true));
        DemoJFreeChartSettings.addDummyValues(new JFreeChartRender("SetBaseShapesVisible").setBaseShapesVisible(true));
    }
}
