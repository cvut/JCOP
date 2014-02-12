/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import java.util.List;

/**
 * Implementation of Mutation interface are responsible for mutation of chromosomes just before they are to be returned
 * as children.
 *
 * @author Ondrej Skalicka
 */
public interface Mutation {
    /**
     * Performs mutation on attributes of a chromosome.
     * <p/>
     * This operation should be executed in {@link cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm.Reproduction#reproduce(Chromosome,
     * Chromosome, Mutation)} just before creating chromosomes/configurations from attributes.
     *
     * @param attributes attributes to be mutated.
     */
    public void mutate(List<Integer> attributes);
}
