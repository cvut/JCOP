/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Provides basic methods for computing common fitness operations.
 * <p/>
 * Currently, only {@link #normalize(double)} fitness operation is supported. It is used to normalize fitness to (0.0,
 * 1.0) scale.
 *
 * @author Ondrej Skalicka
 */
public abstract class BaseFitness implements Fitness {
    /**
     * Minimal possible fitness. If fitness is by any chance lower than this number, it is set to minFitness.
     */
    protected double minFitness = Double.NaN;
    /**
     * Maximal possible fitness. If fitness is by any chance higher than this number, it is set to maxFitness.
     */
    protected double maxFitness = Double.NaN;
    /**
     * Asymmetric scaling does not scale fitness evenly but allocates (0.0, 0.5) range to negative fitness values and
     * (0.5, 1.0) to positive values. Zero value will be represented as 0.5. Only works if minFitness &lt; 0.0 and
     * maxFitness &gt; 0.0
     */
    protected boolean asymmetricScale = true;

    public double normalize(double fitness) throws IllegalStateException {
        if (this.minFitness == Double.NaN || this.maxFitness == Double.NaN) throw new IllegalStateException("Fitness has not prepared min/max fitnesses");

        if (minFitness >= maxFitness) return 0.5;

        if (fitness < minFitness) fitness = minFitness;
        if (fitness > maxFitness) fitness = maxFitness;

        if (asymmetricScale && minFitness < 0.0 && maxFitness > 0.0) {
            if (fitness < 0.0)
                return (minFitness - fitness) / (2.0 * minFitness);
            else return fitness / (2.0 * maxFitness) + 0.5;
        }


        return (fitness - minFitness) / (maxFitness - minFitness);
    }

    public double getMaxFitness() {
        return maxFitness;
    }

    public double getMinFitness() {
        return minFitness;
    }
}
