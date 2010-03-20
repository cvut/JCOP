package basicusage.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

public class DemoCondition {
    public static void main(String[] args) {
        // create problem & algorithm
        Problem problem = new Knapsack("9000 4 100 18 114 42 136 88 192 3 223");
        Algorithm algorithm = new BreadthFirstSearch();

        // create solver
        Solver solver = new SimpleSolver(algorithm, problem);

        // max 10 optimizations
        solver.addStopCondition(new IterationCondition(10));
        // and max 10ms of solver iteration run
        solver.addStopCondition(new TimeoutCondition(10));

        // run!
        solver.run();
    }
}
