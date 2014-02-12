/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import java.util.List;

/**
 * Allows selection of parents from population.
 *
 * @author Ondrej Skalicka
 */
public interface Selection {
    /**
     * Prepares Selection for new generation.
     * <p/>
     * This method is called just before first selection on every new generation.
     *
     * @param population whole population
     */
    public void init(List<Chromosome> population);

    /**
     * Selects one parent from current population (set in {@link #init(java.util.List)}).
     *
     * @return selected parent
     */
    public Chromosome select();
}
