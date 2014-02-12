/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

import cz.cvut.felk.cig.jcop.solver.message.Message;
import cz.cvut.felk.cig.jcop.solver.message.MessageSolverStart;
import cz.cvut.felk.cig.jcop.util.PreciseTime;

/**
 * Limits solver to run for certain amount of CPU time.
 *
 * @author Ondrej Skalicka
 * @see cz.cvut.felk.cig.jcop.util.PreciseTime#getCpuTimeMili() utility to measure time used in this condition.
 */
public class TimeoutCondition extends BaseCondition {
    /**
     * When solver started.
     */
    protected long cpuTimeStart = 0;
    /**
     * How long (ms) to allow solver to run.
     */
    protected long timeout;

    /**
     * Creates new TimeoutCondition with given timeout in ms.
     *
     * @param timeout timeout in miliseconds
     */
    public TimeoutCondition(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof MessageSolverStart) this.cpuTimeStart = PreciseTime.getCpuTimeMili();
    }

    public boolean isConditionMet() {
        return (PreciseTime.getCpuTimeMili() > this.cpuTimeStart + this.timeout);
    }
}
