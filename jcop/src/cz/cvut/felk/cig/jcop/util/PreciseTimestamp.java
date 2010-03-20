/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.util;

/**
 * Holds all time information about one moment.
 * <p/>
 * Holds three processor times - user (time spent running code), system (time spent on I/O) and cpu (user+system), and
 * one real time - clock (real world time). First three are in miliseconds, last is in miliseconds.
 *
 * @author Ondrej Skalicka
 */
public class PreciseTimestamp {
    /**
     * Current CPU time in miliseconds
     */
    protected long cpuTime;
    /**
     * Current System time in miliseconds
     */
    protected long systemTime;
    /**
     * Current User time in miliseconds
     */
    protected long userTime;
    /**
     * Current clock time in miliseconds
     */
    protected long clockTime;

    /**
     * Creates new PreciseTimestamp with current cpuTime, systemTime, userTime and clockTime fetched from {@link
     * PreciseTime}
     */
    public PreciseTimestamp() {
        this.cpuTime = PreciseTime.getCpuTimeMili();
        this.systemTime = PreciseTime.getSystemTimeMili();
        this.userTime = PreciseTime.getUserTimeMili();
        this.clockTime = PreciseTime.getClockTimeMili();
    }

    /**
     * Creates new PreciseTimestamp with given cpuTime, systemTime, userTime and clockTime.
     *
     * @param cpuTime    current CPU time in miliseconds
     * @param systemTime current system time in miliseconds
     * @param userTime   current user time in miliseconds
     * @param clockTime  current clock time in miliseconds
     */
    public PreciseTimestamp(long cpuTime, long systemTime, long userTime, long clockTime) {
        this.cpuTime = cpuTime;
        this.systemTime = systemTime;
        this.userTime = userTime;
        this.clockTime = clockTime;
    }

    /**
     * Returns user time spent from this timestamp to destination timestamp in miliseconds. Always returns number > 0
     * (even if time would be zero or negative).
     *
     * @param destinationTimestamp destination timestamp
     * @return user time spent between two timestamps
     */
    public long getUserTimeSpent(PreciseTimestamp destinationTimestamp) {
        return Math.max(1, destinationTimestamp.userTime - this.userTime);
    }

    /**
     * Returns system time spent from this timestamp to destination timestamp in miliseconds. Always returns number > 0
     * (even if time would be zero or negative).
     *
     * @param destinationTimestamp destination timestamp
     * @return system time spent between two timestamps
     */
    public long getSystemTimeSpent(PreciseTimestamp destinationTimestamp) {
        return Math.max(1, destinationTimestamp.systemTime - this.systemTime);
    }

    /**
     * Returns cpu time spent from this timestamp to destination timestamp in miliseconds. Always returns number > 0
     * (even if time would be zero or negative).
     *
     * @param destinationTimestamp destination timestamp
     * @return cpu time spent between two timestamps
     */
    public long getCpuTimeSpent(PreciseTimestamp destinationTimestamp) {
        return Math.max(1, destinationTimestamp.cpuTime - this.cpuTime);
    }

    /**
     * Returns clock time spent from this timestamp to destination timestamp. Always returns number > 0 (even if time
     * would be zero or negative).
     *
     * @param destinationTimestamp destination timestamp
     * @return clock time spent between two timestamps
     */
    public long getClockTimeSpent(PreciseTimestamp destinationTimestamp) {
        return Math.max(1, destinationTimestamp.clockTime - this.clockTime);
    }

    public long getClockTime() {
        return clockTime;
    }

    public void setClockTime(long clockTime) {
        this.clockTime = clockTime;
    }

    public long getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(long cpuTime) {
        this.cpuTime = cpuTime;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public long getUserTime() {
        return userTime;
    }

    public void setUserTime(long userTime) {
        this.userTime = userTime;
    }
}
