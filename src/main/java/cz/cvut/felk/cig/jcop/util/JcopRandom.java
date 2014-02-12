/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.util;

import java.util.Random;

/**
 * JcopRandom is wrapper around {@link java.util.Random} such that it removes the necessity to create lots of new
 * Random() objects and it allows predictable behavior of whole application.
 *
 * @author Ondrej Skalicka
 */
public class JcopRandom {
    /**
     * Random instance to redirect randoms to
     */
    protected static Random random = new Random();


    /**
     * Provides access to {@link java.util.Random#nextBoolean()}.
     *
     * @return the next pseudorandom, uniformly distributed boolean value from this random number generator's sequence
     */
    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * Provides access to {@link java.util.Random#nextDouble()}.
     *
     * @return the next pseudorandom, uniformly distributed double value between 0.0 and 1.0 from this random number
     *         generator's sequence
     */
    public static double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Provides access to {@link java.util.Random#nextFloat()}.
     *
     * @return the next pseudorandom, uniformly distributed double value between 0.0 and 1.0 from this random number
     *         generator's sequence
     */
    public static float nextFloat() {
        return random.nextFloat();
    }

    /**
     * Provides access to {@link java.util.Random#nextGaussian()}.
     *
     * @return the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0 and standard
     *         deviation 1.0 from this random number generator's sequence
     */
    public static double nextGaussian() {
        return random.nextGaussian();
    }

    /**
     * Provides access to {@link java.util.Random#nextInt()}.
     *
     * @return the next pseudorandom, uniformly distributed int value from this random number generator's sequence
     */
    public static int nextInt() {
        return random.nextInt();
    }

    /**
     * Provides access to {@link java.util.Random#nextInt(int)}.
     *
     * @param n the bound on the random number to be returned. Must be positive.
     * @return the next pseudorandom, uniformly distributed int value between 0 (inclusive) and n (exclusive) from this
     *         random number generator's sequence
     */
    public static int nextInt(int n) {
        return random.nextInt(n);
    }


    /**
     * Provides access to {@link java.util.Random#nextLong()}.
     *
     * @return the next pseudorandom, uniformly distributed long value from this random number generator's sequence
     */
    public static long nextLong() {
        return random.nextLong();
    }

    /**
     * Provides access to {@link java.util.Random#setSeed(long)} )}.
     *
     * @param seed the initial seed
     */
    public static void setSeed(long seed) {
        random.setSeed(seed);
    }
}
