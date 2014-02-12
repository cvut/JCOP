/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.InvalidConfigurationException;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Moves one job from its previous machine to other machine.
 *
 * @author Ondrej Skalicka
 */
public class MoveOperation implements Operation {
    /**
     * Which job to move
     */
    protected Job job;
    /**
     * To which machine to move the job
     */
    protected int machine;

    /**
     * Creates new MoveOperation with given job and destination machine number.
     *
     * @param job     job to move
     * @param machine machine number to move job to
     */
    public MoveOperation(Job job, int machine) {
        this.job = job;
        this.machine = machine;
    }

    public Configuration execute(Configuration configuration) throws InvalidConfigurationException {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.job.getIndex(), this.machine);

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "MoveOperation{" +
                "job=" + job +
                ", machine=" + machine +
                '}';
    }
}
