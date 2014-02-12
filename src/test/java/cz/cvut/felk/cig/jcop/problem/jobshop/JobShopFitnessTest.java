/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Tests proper behavior of {@link JobShopFitness} default fitness.
 *
 * @author Ondrej Skalicka
 */
public class JobShopFitnessTest {
    @Test
    public void testGetValue() throws Exception {
        JobShop jobShop = new JobShop(Arrays.asList(6, 3, 2, 6), 2);
        JobShopFitness fitness = new JobShopFitness(jobShop);

        assert fitness.getMaxFitness() == 8.0 : "Expected maxFitness 8.0, found " + fitness.getMaxFitness();
        assert fitness.getMinFitness() == 0.0 : "Expected maxFitness 0.0, found " + fitness.getMinFitness();

        assert fitness.normalize(fitness.getValue(new Configuration(Arrays.asList(0, 0, 1, 1), "test"))) == 1.0 : "Expected fitness 1.0, found " + fitness.normalize(fitness.getValue(new Configuration(Arrays.asList(0, 0, 1, 1), "test")));
        assert fitness.normalize(fitness.getValue(new Configuration(Arrays.asList(0, 0, 0, 0), "test"))) == 0.0 : "Expected fitness 0.0, found " + fitness.normalize(fitness.getValue(new Configuration(Arrays.asList(0, 0, 0, 0), "test")));

        assert fitness.getValue(new Configuration(Arrays.asList(0, 0, 0, 1), "test")) == 6 : "Expected fitness 6, found " + fitness.getValue(new Configuration(Arrays.asList(0, 0, 0, 1), "test"));

    }
}
