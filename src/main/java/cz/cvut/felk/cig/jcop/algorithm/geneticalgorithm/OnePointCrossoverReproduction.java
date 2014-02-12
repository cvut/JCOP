/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes two chromosomes, randomly selects one point (crossover) which splits chromosome into two parts. Creates son
 * from father's first part and mother's second and creates daughter from father's second part and mother's first.
 *
 * @author Ondrej Skalicka
 */
public class OnePointCrossoverReproduction implements Reproduction {
    public List<Chromosome> reproduce(Chromosome father, Chromosome mother, Mutation mutation) {
        int size = father.getConfiguration().size();
        int crossover = JcopRandom.nextInt(size);

        List<Integer> sonAttributes = new ArrayList<Integer>(size);
        List<Integer> daughterAttributes = new ArrayList<Integer>(size);

        for (int i = 0; i < size; ++i) {
            if (i < crossover) {
                sonAttributes.add(father.getConfiguration().valueAt(i));
                daughterAttributes.add(mother.getConfiguration().valueAt(i));
            } else {
                sonAttributes.add(mother.getConfiguration().valueAt(i));
                daughterAttributes.add(father.getConfiguration().valueAt(i));
            }
        }

        mutation.mutate(sonAttributes);
        mutation.mutate(daughterAttributes);

        List<Chromosome> children = new ArrayList<Chromosome>(2);
        children.add(new Chromosome(new Configuration(sonAttributes)));
        children.add(new Chromosome(new Configuration(daughterAttributes)));

        return children;
    }
}
