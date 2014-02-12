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
    protected long counter;
    /**
     * Actual size of history.
     *
     * This can vary from {@link #counter} if history has been empties. In such case, {@link #counter} remains unchanged, while
     * size is reset to zero.
     */
    protected long size;
    /**
     * How many items are allowed in history at most.
     *
     * If this number is reached, history is emptied in order to free allocated memory. Constants {@link #LIMIT_DISABLED}
     * and {@link #LIMIT_UNLIMITED} are allowed values as well as any positive number. 
     */
    public static int limit = 1024;
    /**
     * No history is collected.
     *
     * Every configuration has only one OperationHistory element (with null {@link #parent}).
     */
    public static final int LIMIT_DISABLED = 0;
    /**
     * History is never erased.
     *
     * Note that this can lead to huge memory overhead as single configuration can have as many as hundred million items in
     * history (which with about 32B for every OperationHistory object means several GB of memory).
     */
    public static final int LIMIT_UNLIMITED = -1;

    /**
     * Constructs new OperationHistory without parent (eg. root)
     *
     * @param operation operation associated with OperationHistory
     */
    public OperationHistory(Operation operation) {
        this.operation = operation;
        this.parent = null;
        this.counter = 0;
        this.size = 0;
    }

    /**
     * Creates new OperationHistory with parent (eg. node or leaf)
     *
     * @param operation operation associated with OperationHistory
     * @param parent    parent OperationHistory
     */
    public OperationHistory(Operation operation, OperationHistory parent) {
        this.operation = operation;
        this.counter = parent.getCounter() + 1;

        if (OperationHistory.limit >= 0 && parent.size >= OperationHistory.limit) {
            this.parent = null;
            this.size = 0;
        } else {
            this.parent = parent;
            this.size = parent.size + 1;
        }
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
    public long getCounter() {
        return counter;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperationHistory getParent() {
        return parent;
    }
}