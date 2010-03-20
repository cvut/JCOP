package jcoptest;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.result.render.CSVRender;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.result.render.XMLRender;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Solver solver = new SimpleSolver(new BreadthFirstSearch(), new Knapsack("9000 4 100 18 114 42 136 88 192 3 223"));
        solver.addListener(new JFreeChartRender("Test"));
        solver.addRender(new SimpleRender());
        solver.addRender(new CSVRender(new File("test.csv")));
        solver.addRender(new XMLRender(new File("test.xml")));
        solver.run();
        solver.render();
    }
}