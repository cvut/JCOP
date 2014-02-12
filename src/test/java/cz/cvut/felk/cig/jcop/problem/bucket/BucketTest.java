/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ProblemFormatException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests proper behavior of {@link Bucket} problem.
 *
 * @author Ondrej Skalicka
 */
public class BucketTest {
    @Test
    public void testCreate() throws Exception {
        try {
            new Bucket(new int[]{14, 10, 6, 2, 8}, new int[]{0, 0, 1, 0, 0}, new int[]{12, 6, 4, 1}); // invalid parameters
            assert false;
        } catch (ProblemFormatException ignore) {

        }
        try {
            new Bucket(new int[]{}, new int[]{}, new int[]{}); // invalid parameters
            assert false;
        } catch (ProblemFormatException ignore) {

        }
    }

    @Test
    public void testIsSolution() throws Exception {
        Bucket bucket = new Bucket(new int[]{14, 10, 6, 2, 8}, new int[]{0, 0, 1, 0, 0}, new int[]{12, 6, 4, 1, 8});

        List<Integer> attributes = new ArrayList<Integer>(5);
        attributes.add(12);
        attributes.add(6);
        attributes.add(4);
        attributes.add(1);

        assert !bucket.isSolution(new Configuration(attributes, "foo"));

        attributes.add(8);

        assert bucket.isSolution(new Configuration(attributes, "foo"));

        attributes.set(4, 7);

        assert !bucket.isSolution(new Configuration(attributes, "foo"));
    }
}
