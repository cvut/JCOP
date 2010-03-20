/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;
import demopackage.demographsearch.MyAlgorithm;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoMyAlgorithm {
    public static void main(String[] args) throws IOException {
        Solver solver = new SimpleSolver(new MyAlgorithm(), new Knapsack(new File("data/knapsack/knap_4.txt")));

        solver.run();

        solver.render();
    }
}
