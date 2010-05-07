/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Scaffold for more complex conditions in OR relation.
 *
 * @author Ondrej Skalicka
 */
public class OrCondition extends BaseCondition {
    /**
     * List of all stop conditions associated with this OrCondition.
     * <p/>
     * At least one of these conditions must be met in order to {@link #isConditionMet()} return true.
     */
    protected List<StopCondition> stopConditions;

    /**
     * Creates new OrCondition with empty list of other StopConditions
     */
    public OrCondition() {
        this.stopConditions = new ArrayList<StopCondition>();
    }

    /**
     * Creates new OrCondition with array of other StopConditions.
     *
     * @param stopConditions list of stop conditions to be grouped into one
     */
    public OrCondition(List<StopCondition> stopConditions) {
        this.stopConditions = stopConditions;
    }

    /**
     * Creates OR condition from two other stop conditions. It is less generalized form of {@link
     * #OrCondition(java.util.List)}.
     *
     * @param stopCondition  left part of OR relation
     * @param stopCondition2 right part of OR relation
     */
    public OrCondition(StopCondition stopCondition, StopCondition stopCondition2) {
        this.stopConditions = new ArrayList<StopCondition>(2);
        this.stopConditions.add(stopCondition);
        this.stopConditions.add(stopCondition2);
    }

    /**
     * Adds new stop condition to list of conditions to be met.
     *
     * @param stopCondition new stop condition that can be met
     */
    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
    }

    /**
     * Returns true iff at least one stop condition passed to constructor is true.
     *
     * @return true iff all at least one condition is true
     */
    public boolean isConditionMet() {
        for (StopCondition sc : this.stopConditions)
            if (sc.isConditionMet()) return true;
        return false;
    }
}