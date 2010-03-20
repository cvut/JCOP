package basicusage.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.solver.BaseSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;

public class PrimitiveSolver extends BaseSolver implements Solver {
    protected Problem problem;
    protected Algorithm algorithm;

    public PrimitiveSolver(Algorithm algorithm, Problem problem) {
        this.algorithm = algorithm;
        this.problem = problem;
    }

    public void run() {
        // init algorithm
        this.algorithm.init(new BaseObjectiveProblem(this.problem));

        // make 10 optimizations
        for (int i = 0; i < 10; i++) this.algorithm.optimize();
    }
}
