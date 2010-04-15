/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

import java.util.LinkedList;
import java.util.List;

/**
 * Operation history is used to record which operations in which order were applied to get operation.
 * <p/>
 * Operation history has reference to parent operation history, but not to attributes itself. Nor does attributes has
 * reference to parent attributes. This is because garbage collector could faster free unused
 * configurations/operationHistories from memory. Every time attributes becomes unreachable from rest of code, it could
 * be freed, along with its operation history (usually).
 *
 * @author Ondrej Skalicka
 */
public class OperationHistory {
    /**
     * Operation used in this step of history
     */
    protected Operation operation;
    /**
     * Preceding operationHistory, root has null
     */
    protected OperationHistory parent;
    /**
     * Counter indicating order of this entry in history of attributes.
     * <p/>
     * Root has 0, each other child has its parent counter+1.
     */
    protected int counter;

    /**
     * Constructs new OperationHistory without parent (eg. root)
     *
     * @param operation operation associated with OperationHistory
     */
    public OperationHistory(Operation operation) {
        this.operation = operation;
        this.parent = null;
        this.counter = 0;
    }

    /**
     * Creates new OperationHistory with parent (eg. node or leaf)
     *
     * @param operation operation associated with OperationHistory
     * @param parent    parent OperationHistory
     */
    public OperationHistory(Operation operation, OperationHistory parent) {
        this.operation = operation;
        this.parent = parent;
        this.counter = parent.getCounter() + 1;
    }

    /**
     * Returns operation history as string in format
     * <p/>
     * 0: firstOperation, 1: secondOperation ... n-1: lastOperation
     *
     * @return string representation of OperationHistory
     */
    @Override
    public String toString() {
        String base = "";
        if (this.parent != null) base = this.parent.toString();

        return base + (this.parent == null ? "" : ", ") + this.counter + ":" + operation.toString();
    }

    /**
     * Returns all parents (including this child) ordered from root to leaf (eg. in chronological order).
     *
     * @return list of all OperationHistory associated with this one
     */
    public List<OperationHistory> getChronologicalList() {
        OperationHistory operationHistory = this;
        LinkedList<OperationHistory> operationHistoryList = new LinkedList<OperationHistory>();
        do {
            operationHistoryList.addFirst(operationHistory);
            operationHistory = operationHistory.getParent();
        } while (operationHistory != null);

        return operationHistoryList;
    }

    /**
     * Returns counter - starting from node with counter = 0, each child has counter incremented by 1.
     *
     * @return counter of node
     */
    public int getCounter() {
        return counter;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperationHistory getParent() {
        return parent;
    }
}