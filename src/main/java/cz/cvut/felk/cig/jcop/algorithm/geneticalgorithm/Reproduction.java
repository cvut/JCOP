/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import java.util.List;

/**
 * Combines two parents into two children.
 *
 * @author Ondrej Skalicka
 */
public interface Reproduction {
    /**
     * Reproduces father and mother into two children.
     *
     * @param father   father chromosome
     * @param mother   mother chromosome
     * @param mutation mutation which will be used on both children
     * @return list of two children
     */
    public List<Chromosome> reproduce(Chromosome father, Chromosome mother, Mutation mutation);
}
