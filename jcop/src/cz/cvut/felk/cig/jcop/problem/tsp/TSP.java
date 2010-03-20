/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Traveling Salesman Problem.
 * <p/>
 * TSP problem has a list of cities and every city has distance to every other city. The goal is to find order in which
 * to visit every city exactly once and minimize travel distance.
 *
 * @author Ondrej Skalicka
 */
public class TSP extends BaseProblem implements StartingConfigurationProblem {
    /**
     * List of all possible {@link SwitchCityOperation MoveCityOperations}. First operations has source index 0,
     * destination index 1..dimension-1. Then comes operation with source 1, destination 2..dimension-1 etc. Last is
     * source index = dimension-2, destination dimension-1.
     */
    protected List<SwitchCityOperation> switchCityOperations;
    /**
     * List of all cities in problem.
     */
    protected List<City> cities;
    /**
     * Starting TSP configuration - cities visited in order in which they were created.
     */
    protected Configuration startingConfiguration;

    /**
     * Creates new TSP problem from matrix of distances.
     * <p/>
     * Distances matrix must be n*n, where n is number of cities. Any other matrix (larger, smaller) would raise
     * ProblemFormatException.
     * <p/>
     * distance[i][i] (eg. distance from city to itself) is ignored value. Value -1 stands for impossible path.
     *
     * @param distances distance matrix. First index is source city, second destination
     * @throws ProblemFormatException if distances is not n*n matrix
     */
    public TSP(Integer[][] distances) throws ProblemFormatException {
        StringBuffer labelStringBuffer = new StringBuffer("Dist={");
        this.dimension = distances.length;

        if (this.dimension < 2)
            throw new ProblemFormatException("Dimension must be greater than one, " + this.dimension + " found");

        // prepare new cities
        this.cities = new ArrayList<City>(this.dimension);
        for (int i = 0; i < this.dimension; ++i) {
            this.cities.add(new City(i, this.dimension - 1));
        }

        // prepare switch operation container
        this.switchCityOperations = new ArrayList<SwitchCityOperation>(this.dimension * (this.dimension - 1) / 2);

        // parse distances
        for (int i = 0; i < this.dimension; ++i) {
            City city = this.cities.get(i);
            if (distances[i].length != this.dimension)
                throw new ProblemFormatException("Distance matrix' distances[" + i + "].length is not " + this.dimension + ", " + distances[i].length + "found.");

            labelStringBuffer.append("{");

            // add distance to target city
            for (int j = 0; j < this.dimension; ++j) {
                labelStringBuffer.append(j == 0 ? "" : ",").append(distances[i][j]);

                if (i < this.dimension - 1 && j > i)
                    this.switchCityOperations.add(new SwitchCityOperation(i, j));

                if (i != j)
                    city.addDistance(this.cities.get(j), distances[i][j]);
            }

            labelStringBuffer.append("}");
        }

        labelStringBuffer.append("}");
        this.setLabel(labelStringBuffer.toString());

        // init starting configuration
        List<Integer> startingConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i)
            startingConfigurationAttributes.add(i);
        this.startingConfiguration = new Configuration(startingConfigurationAttributes, "TSP starting configuration");
    }

    /** Problem Interface */

    public boolean isSolution(Configuration configuration) {
        try {
            boolean[] presenceMap = new boolean[this.dimension];
            Arrays.fill(presenceMap, false);

            for (int i = 0; i < this.dimension; ++i) {
                if (presenceMap[configuration.valueAt(i)]) return false;
                presenceMap[configuration.valueAt(i)] = true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    public TSPIterator getOperationIterator(Configuration configuration) {
        return new TSPIterator(configuration, this);
    }

    public Fitness getDefaultFitness() {
        return new TSPFitness(this);
    }

    /* StartingConfigurationProblem interface */

    public Configuration getStartingConfiguration() {
        return this.startingConfiguration;
    }
}
