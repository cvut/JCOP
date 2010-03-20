/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * This class is used to get variable times (CPU, System, User) in variable precision (miliseconds, nanoseconds)
 *
 * @author Ondrej Skalicka
 */
public class PreciseTime {
    /**
     * Bean to take time from.
     */
    protected static ThreadMXBean bean = null;

    /**
     * Gets ThreadMXBean, creates one from {@link java.lang.management.ManagementFactory} if {@link #bean} is null.
     *
     * @return ThreadMXBean for precise time
     */
    protected static ThreadMXBean getBean() {
        if (PreciseTime.bean == null)
            PreciseTime.bean = ManagementFactory.getThreadMXBean();
        return PreciseTime.bean;
    }

    /**
     * Gets current thread CPU time in nanoseconds.
     *
     * @return current thread CPU time in nanoseconds.
     */
    public static long getCpuTimeNano() {
        return getBean().isCurrentThreadCpuTimeSupported() ?
                getBean().getCurrentThreadCpuTime() : 0L;
    }

    /**
     * Gets current thread User time in nanoseconds.
     *
     * @return current thread User time in nanoseconds.
     */
    public static long getUserTimeNano() {
        return getBean().isCurrentThreadCpuTimeSupported() ?
                getBean().getCurrentThreadUserTime() : 0L;
    }

    /**
     * Gets current thread System time in nanoseconds.
     *
     * @return current thread System time in nanoseconds.
     */
    public static long getSystemTimeNano() {
        return getBean().isCurrentThreadCpuTimeSupported() ?
                (getBean().getCurrentThreadCpuTime() - getBean().getCurrentThreadUserTime()) : 0L;
    }

    /**
     * Gets current thread CPU time in nanoseconds.
     *
     * @return current thread CPU time in nanoseconds.
     */
    public static long getCpuTimeMili() {
        return getCpuTimeNano() / 1000000L;
    }

    /**
     * Gets current thread User time in miliseconds.
     *
     * @return current thread User time in miliseconds.
     */
    public static long getUserTimeMili() {
        return getUserTimeNano() / 1000000L;
    }

    /**
     * Gets current thread System time in miliseconds.
     *
     * @return current thread System time in miliseconds.
     */
    public static long getSystemTimeMili() {
        return getSystemTimeNano() / 1000000L;
    }

    /**
     * Returns clock time.
     * <p/>
     * Note that clock time is very unprecise.
     *
     * @return clock time.
     */
    public static long getClockTimeMili() {
        return System.currentTimeMillis();
    }
}
