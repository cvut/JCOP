/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link TournamentSelection} part of genetic algorithm.
 *
 * @author Ondrej Skalicka
 */
public class TournamentSelectionTest {
    @Test
    public void testInit() throws Exception {
        TournamentSelection tournamentSelection = new TournamentSelection(4, 0.5);
        assert tournamentSelection.maxRand == 0.9375 : "Expected maxRand 0.9375, found " + tournamentSelection.maxRand;

        tournamentSelection = new TournamentSelection(3, 0.7);
        assert tournamentSelection.maxRand == 0.973 : "Expected maxRand 0.0.973, found " + tournamentSelection.maxRand;
    }
}
