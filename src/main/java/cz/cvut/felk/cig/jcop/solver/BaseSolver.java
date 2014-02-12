/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.result.SimpleResult;
import cz.cvut.felk.cig.jcop.result.render.Render;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.solver.condition.StopCondition;
import cz.cvut.felk.cig.jcop.solver.message.*;
import cz.cvut.felk.cig.jcop.util.PreciseTimestamp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements some methods commonly used in solvers.
 * <p/>
 * BaseSolver implements method to notify all {@link cz.cvut.felk.cig.jcop.solver.condition.StopCondition
 * stopConditions} in solver that something happened and to found if any condition is met.
 * <p/>
 * BaseSolver has default render {@link cz.cvut.felk.cig.jcop.result.render.SimpleRender} (however if you add more, this
 * one is removed, so if you want both {@link cz.cvut.felk.cig.jcop.result.render.SimpleRender} and your own, add both
 * using {@link #addRender(cz.cvut.felk.cig.jcop.result.render.Render)}.
 * <p/>
 * Default {@link cz.cvut.felk.cig.jcop.result.Result} is {@link cz.cvut.felk.cig.jcop.result.SimpleResult} which can be
 * overridden by {@link #setResult(cz.cvut.felk.cig.jcop.result.Result)}.
 *
 * @author Ondrej Skalicka
 */
public abstract class BaseSolver implements Solver {
    /**
     * List of all stop conditions associated with this solver.
     * <p/>
     * At least one must be met to stop executing optimize on algorithm/problem.
     */
    protected List<StopCondition> stopConditions = new ArrayList<StopCondition>();
    /**
     * Solver logger
     */
    protected Logger logger;
    /**
     * List of all renders in this solver.
     */
    protected List<Render> renders = new ArrayList<Render>();
    /**
     * Result for this solver.
     */
    protected Result result;
    /**
     * List of renders used if no other renders are specified.
     * <p/>
     * By default, this list contains only {@link cz.cvut.felk.cig.jcop.result.render.SimpleRender}, but subclasses
     * could modify this behaviour. For example Compare solvers could have both SimpleRender and some kind of
     * CompareRender.
     */
    protected List<Render> defaultRenders = new ArrayList<Render>();
    /**
     * List of all message listeners registered to this solver.
     */
    protected List<MessageListener> messageListeners = new ArrayList<MessageListener>();

    /**
     * Creates new BaseSolver, gets log {@link Logger#getLogger(Class)} and set result to {@link
     * cz.cvut.felk.cig.jcop.result.SimpleResult}.
     */
    protected BaseSolver() {
        logger = Logger.getLogger(this.getClass());
        this.result = new SimpleResult();
        this.defaultRenders.add(new SimpleRender());
    }

    public void addRender(Render render) {
        this.renders.add(render);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * BaseSolver has a pitfall if no renders are specified so that it returns list of default renders instead.
     *
     * @return list of all renders in this solver
     */
    public List<Render> getRenders() {
        if (this.renders.size() == 0) return this.defaultRenders;

        return this.renders;
    }

    public void setResult(Result result) {
        this.result = result;

    }

    public Result getResult() {
        return this.result;
    }

    public void render() {
        for (Render render : this.getRenders()) {
            try {
                render.render(this.getResult());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message message) {
        for (MessageListener messageListener : messageListeners) {
            messageListener.onMessage(message);
        }
    }

    /**
     * Applies one algorithm on one problem until any condition is met or exception is raised.
     * <p/>
     * This method just removes code duplicities since most solvers has identical this part of solving. It is not
     * obligatory to use this method however, change it if necessary (for example algorithm switching on simple
     * problem).
     *
     * @param objectiveProblem problem to be solved
     * @param algorithm        algorithm to be used during solving
     * @return result entry for this optimization
     */
    protected ResultEntry optimize(ObjectiveProblem objectiveProblem, Algorithm algorithm) {
        PreciseTimestamp startPreciseTimestamp = null;
        Exception algorithmException = null;
        Configuration bestConfiguration = null;
        int optimizeCounter = 0;

        try {
            startPreciseTimestamp = new PreciseTimestamp();
            
            algorithm.init(objectiveProblem);
            this.sendMessage(new MessageSolverStart(algorithm, objectiveProblem));
            logger.info("Started optimize, " + algorithm + " on " + objectiveProblem + ".");

            do {
                algorithm.optimize();
                this.sendMessage(new MessageOptimize());
                optimizeCounter++;

                if (algorithm.getBestConfiguration() != null && (bestConfiguration == null || !bestConfiguration.equals(algorithm.getBestConfiguration()))) {
                    logger.debug("Better solution " + algorithm.getBestFitness() + ", " + optimizeCounter + ", " + algorithm.getBestConfiguration());

                    bestConfiguration = algorithm.getBestConfiguration();

                    this.sendMessage(new MessageBetterConfigurationFound(bestConfiguration, algorithm.getBestFitness()));

                    if (objectiveProblem.isSolution(bestConfiguration))
                        this.sendMessage(new MessageSolutionFound(bestConfiguration, algorithm.getBestFitness()));
                }

                if (this.isConditionMet()) break;
            } while (true);
        } catch (Exception e) {
            logger.warn("Got exception " + e.getClass().getSimpleName() + ".");
            algorithmException = e;
        }

        this.sendMessage(new MessageSolverStop(algorithm, objectiveProblem));
        logger.info("Stopped optimize.");

        algorithm.cleanUp();

        return new ResultEntry(algorithm, objectiveProblem, bestConfiguration, algorithm.getBestFitness(), optimizeCounter, algorithmException, startPreciseTimestamp);
    }

    public void addListener(MessageListener messageListener) {
        this.messageListeners.add(messageListener);
    }

    public boolean isConditionMet() {
        for (StopCondition stopCondition : this.stopConditions)
            if (stopCondition.isConditionMet()) return true;
        return false;
    }

    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
        this.addListener(stopCondition);
    }
}
