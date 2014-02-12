/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

/**
 * Single job of JobShop problem.
 * <p/>
 * Contains of job index and time it takes to finish the job.
 *
 * @author Ondrej Skalicka
 */
public class Job {
    /**
     * Index of job (position in configuration).
     */
    protected int index;
    /**
     * Time this job takes to finish.
     */
    protected int time;

    /**
     * Creates new job with given index and time.
     *
     * @param index index of job (position in configuration)
     * @param time  time job takes to finish
     */
    public Job(int index, int time) {
        this.index = index;
        this.time = time;
    }

    public int getIndex() {
        return index;
    }

    public int getTime() {
        return time;
    }
}
