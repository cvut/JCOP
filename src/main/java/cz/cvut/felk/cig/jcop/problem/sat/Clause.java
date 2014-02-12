/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of {@link Formula}, contains negated and non-negated variables.
 * <p/>
 * {@link Formula} consists of several clauses. Iff all clauses evaluates to true via {@link
 * #isTrue(cz.cvut.felk.cig.jcop.problem.Configuration)}, then formula is evaluated to true as well.
 *
 * @author Ondrej Skalicka
 */
public class Clause {
    /**
     * List of all variables in clause.
     */
    protected List<Variable> variables;
    /**
     * List of negated-flags for variables.
     * <p/>
     * Indexes in {@link #variables} and negatedFlags are equal. If a variable has negatedFlag set to true, it is
     * considered negated.
     */
    protected List<Boolean> negatedFlags;

    /**
     * Creates new clause from lists of non-negated and negated variables.
     *
     * @param variables    list of non-negated variables
     * @param negatedFlags list of negated variables
     */
    public Clause(List<Variable> variables, List<Boolean> negatedFlags) {
        this.variables = variables;
        this.negatedFlags = negatedFlags;
    }

    /**
     * Creates new clause with empty lists of variables.
     */
    public Clause() {
        this.variables = new ArrayList<Variable>();
        this.negatedFlags = new ArrayList<Boolean>();
    }

    /**
     * Adds non-negated variable to clause.
     *
     * @param variable non-negated variable to be added
     */
    public void addPositiveVariable(Variable variable) {
        this.variables.add(variable);
        this.negatedFlags.add(false);
    }

    /**
     * Adds negated variable to clause.
     *
     * @param variable negated variable to be added
     */
    public void addNegativeVariable(Variable variable) {
        this.variables.add(variable);
        this.negatedFlags.add(true);
    }

    /**
     * Number of variables in clause (both negated and non-negated together).
     *
     * @return number of variables in clause
     */
    public int size() {
        return this.variables.size();
    }

    /**
     * Checks if clause evaluates to true.
     * <p/>
     * Clause evaluates to true iff at least one variable evaluates to true.
     *
     * @param configuration configuration for which to evaluate
     * @return clause evaluations
     */
    public boolean isTrue(Configuration configuration) {
        for (int i = 0; i < this.variables.size(); ++i) {
            Variable variable = this.variables.get(i);
            if (configuration.valueAt(variable.getIndex()) == 1 && !this.negatedFlags.get(i))
                return true;
            if (configuration.valueAt(variable.getIndex()) == 0 && this.negatedFlags.get(i))
                return true;
        }

        return false;
    }
}
