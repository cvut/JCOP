/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Scaffold for more complex conditions in AND relation.
 *
 * @author Ondrej Skalicka
 */
public class AndCondition extends BaseCondition {
    /**
     * List of all stop conditions associated with this AndCondition.
     * <p/>
     * All these conditions must be met in order to {@link #isConditionMet()} return true.
     */
    protected List<StopCondition> stopConditions;

    /**
     * Creates new AndCondition with empty list of other StopConditions
     */
    public AndCondition() {
        this.stopConditions = new ArrayList<StopCondition>();
    }

    /**
     * Creates new AndCondition with list of other StopConditions.
     *
     * @param stopConditions list of stop conditions to be grouped into one
     */
    public AndCondition(List<StopCondition> stopConditions) {
        this.stopConditions = stopConditions;
    }

    /**
     * Creates AND condition from two other stop conditions. It is less generalized form of {@link
     * #AndCondition(java.util.List)}.
     *
     * @param stopCondition  left part of AND relation
     * @param stopCondition2 right part of AND relation
     */
    public AndCondition(StopCondition stopCondition, StopCondition stopCondition2) {
        this.stopConditions = new ArrayList<StopCondition>(2);
        this.stopConditions.add(stopCondition);
        this.stopConditions.add(stopCondition2);
    }

    /**
     * Adds new stop condition to list of conditions to be met.
     *
     * @param stopCondition new stop condition that must be met
     */
    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
    }

    /**
     * Returns true iff all stop conditions passed to constructor are true.
     *
     * @return true iff all grouped conditions are true
     */
    public boolean isConditionMet() {
        for (StopCondition sc : this.stopConditions)
            if (!sc.isConditionMet()) return false;
        return true;
    }
}
