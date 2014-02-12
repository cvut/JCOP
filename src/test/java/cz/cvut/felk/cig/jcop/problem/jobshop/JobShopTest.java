/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Tests proper behavior of {@link JobShop} problem.
 *
 * @author Ondrej Skalicka
 */
public class JobShopTest {
    @Test
    public void testGetStartingConfiguration() throws Exception {
        JobShop jobShop = new JobShop(Arrays.asList(3, 2, 1), 3);

        Configuration configuration = jobShop.getStartingConfiguration();

        for (int i = 0; i < 3; ++i)
            assert configuration.valueAt(i) == 0 : "Expected starting configurations values 0, " + configuration.valueAt(i) + " found on index " + i;
    }
}
