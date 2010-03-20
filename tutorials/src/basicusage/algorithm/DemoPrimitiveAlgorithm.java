package basicusage.algorithm;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;

public class DemoPrimitiveAlgorithm {
    public static void main(String[] args) {
        // create problem & algorithm
        Problem problem = new Knapsack("9000 4 100 18 114 42 136 88 192 3 223");
        Algorithm algorithm = new PrimitiveAlgorithm();

        // create solver
        Solver solver = new SimpleSolver(algorithm, problem);

        // run!
        solver.run();

        // render results
        solver.render();
    }
}
