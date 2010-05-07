/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tournament selection randomly selects K individuals from population and select one according to his fitness.
 * <p/>
 * Formula to determine which one of K individuals are selected is as follows:
 * <p/>
 * Given K individuals and probability p,
 * <p/>
 * <pre>
 * choose the best individual from pool/tournament with probability p
 * choose the second best individual with probability p*(1-p)
 * choose the third best individual with probability p*((1-p)^2)
 * ...
 * </pre>
 * <p/>
 * Eg. each weaker tournament attendant has p-times the chance of being picked instead of directly better attendant.
 *
 * @author Ondrej Skalicka
 */
public class TournamentSelection implements Selection {
    /**
     * Number of individuals randomly selected to compete.
     */
    protected int k;
    /**
     * Chance to select best individual.
     */
    protected double p;
    /**
     * Current population to work on.
     */
    protected List<Chromosome> population;
    /**
     * Internal value used to calculate highest possible value of random used in {@link #select()}.
     * <p/>
     * Formula for maxRand is <code>1 + (1-p)<sup>k-1</sup>(p - 1) </code>
     */
    protected double maxRand;

    /**
     * Creates TournamentSelection with given k and p.
     *
     * @param k number of individuals randomly selected to compete
     * @param p chance to select best individual
     */
    public TournamentSelection(int k, double p) {
        if (k < 2) throw new InvalidParameterException("K cannot be lower than 2");
        if (p < 0.0 || p > 1.0) throw new InvalidParameterException("P cannot be lowe than 0.0 or grater than 1.0");
        this.k = k;
        this.p = p;
        this.maxRand = 1 + Math.pow(1 - p, k - 1) * (p - 1);
    }

    /**
     * Creates TournamentSelection with default values k = 4, p = 0.5.
     * <p/>
     * Best individual has 53% chance, 2nd has 27%, 3rd 13% and last 7% of being picked. (approximately)
     */
    public TournamentSelection() {
        this(4, 0.5);
    }

    public void init(List<Chromosome> population) {
        this.population = population;
    }

    public Chromosome select() {
        List<Chromosome> tournamentAttendants = new ArrayList<Chromosome>(this.k);

        for (int i = 0; i < k; ++i) {
            Chromosome attendant = this.population.get(JcopRandom.nextInt(this.population.size()));
            tournamentAttendants.add(attendant);
        }

        Collections.sort(tournamentAttendants);

        double tmp = JcopRandom.nextDouble() * this.maxRand;
        double p = this.p;
        double cumulativeP = p;

        // k-1 is because if all but the last one lost, we select last one automatically
        for (int i = 0; i < k - 1; ++i) {
            if (tmp < cumulativeP) return tournamentAttendants.get(i);
            p = p * (1 - this.p);
            cumulativeP += p;
        }

        return tournamentAttendants.get(k - 1);
    }
}
