/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.render.Render;
import cz.cvut.felk.cig.jcop.solver.condition.StopCondition;
import cz.cvut.felk.cig.jcop.solver.message.Message;
import cz.cvut.felk.cig.jcop.solver.message.MessageListener;

import java.util.List;

/**
 * Base of all solvers, defines methods required for solver to be used in JCOP.
 *
 * @author Ondrej Skalicka
 */
public interface Solver {
    /**
     * Solves one or more problems using one or more algorithms as long as possible (usually stops upon any
     * StopCondition is met or when algorithm raises CannotContinueException).
     */
    public void run();

    /**
     * Adds new render to solver.
     *
     * @param render render to be added to solver
     */
    public void addRender(Render render);

    /**
     * Gets list of all renders associated with this solver.
     *
     * @return list of all renders in this solver
     */
    public List<Render> getRenders();

    /**
     * Sets Result to store results of algorithm-problem runs in.
     *
     * @param result store results in this
     */
    public void setResult(Result result);

    /**
     * Returns Result associated with this solver.
     *
     * @return Result associated with this solver
     */
    public Result getResult();

    /**
     * Render Result using all registered Renders.
     *
     * @throws java.io.IOException if cannot write to output
     */
    public void render();

    /**
     * Adds new stop condition to list. All stop conditions are in OR relation, eg. al least one must be met to end run
     * cycle.
     * <p/>
     * Note that stop condition is automatically registered as listener, so do NOT add it again using {@link
     * #addListener(cz.cvut.felk.cig.jcop.solver.message.MessageListener)}!
     *
     * @param stopCondition new stop condition
     */
    public void addStopCondition(StopCondition stopCondition);

    /**
     * Checks if at least one condition is met. If yes, returns true, otherwise returns false.
     *
     * @return true if at least one condition is met
     */
    public boolean isConditionMet();

    /**
     * Adds new listener to solver.
     * <p/>
     * Note that all {@link cz.cvut.felk.cig.jcop.solver.condition.StopCondition stopConditions} added via {@link
     * #addStopCondition(cz.cvut.felk.cig.jcop.solver.condition.StopCondition)} are automatically registered as
     * listeners so do NOT add them again.
     *
     * @param messageListener new message listener to be registered
     */
    public void addListener(MessageListener messageListener);

    /**
     * Sends message to all registered listeners.
     *
     * @param message message to be send
     */
    public void sendMessage(Message message);
}
