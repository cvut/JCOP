/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Formula of {@link SAT} problem.
 * <p/>
 * Formula consists of {@link Clause clauses}.
 *
 * @author Ondrej Skalicka
 */
public class Formula {
    /**
     * List of all clauses in this formula
     */
    protected List<Clause> clauses;

    /**
     * Creates new empty (eg. without clauses) formula
     */
    public Formula() {
        this.clauses = new ArrayList<Clause>();
    }

    /**
     * Adds clause to formula
     *
     * @param clause clause to be added
     */
    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }

    /**
     * Checks if formula evaluates to true.
     * <p/>
     * Formula evaluates to true iff all its clauses evaluates to true.
     *
     * @param configuration configuration for which to evaluate
     * @return formula evaluations
     */
    public boolean isTrue(Configuration configuration) {
        for (Clause clause : this.clauses) {
            if (!clause.isTrue(configuration)) return false;
        }

        return true;
    }

    /**
     * Returns list of all clauses in current formula
     *
     * @return list of clauses in formula
     */
    public List<Clause> getClauses() {
        return clauses;
    }
}
