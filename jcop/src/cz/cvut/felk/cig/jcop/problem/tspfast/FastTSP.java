/*
 * Copyright © 2011 by Oleg Kovarik. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tspfast;

import cz.cvut.felk.cig.jcop.problem.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Traveling Salesman Problem - fast implementation.
 * <p/>
 * TSP problem has a list of cities and every city has distance to every other city. The goal is to find order in which
 * to visit every city exactly once and minimize travel distance.
 *
 * @author Oleg Kovarik
 */
public class FastTSP extends BaseProblem implements StartingConfigurationProblem, GlobalSearchProblem, RandomConfigurationProblem {
    /**
     * List of all possible {@link FastInvertSubtourOperation}. First operations has source index 0,
     * destination index 1..dimension-1. Then comes operation with source 1, destination 2..dimension-1 etc. Last is
     * source index = dimension-2, destination dimension-1.
     */
    protected List<FastInvertSubtourOperation> fastInvertSubtourOperations;

    /**
     * Matrix of distances between cities
     */
    protected int[][] distances;

    /**
     * List of nearest neighbors
     */
    protected int[][] nearestNeighbors;

    /**
     * Starting TSP configuration - cities visited in order in which they were created.
     */
    protected Configuration startingConfiguration;

    /**
     * Static meta-data (input data statistics)
     */
    protected TSPMetainfoStatic metainfo;

    /**
     * City coordinates
     */
    private ArrayList<Double[]> coordinates;


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
    public FastTSP(Integer[][] distances) throws ProblemFormatException {
        StringBuffer labelStringBuffer = new StringBuffer("Dist={");
        this.dimension = distances.length;

        if (this.dimension < 2)
            throw new ProblemFormatException("Dimension must be greater than one, " + this.dimension + " found");

        // init starting configuration
        List<Integer> startingConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i)
            startingConfigurationAttributes.add(i);
        this.startingConfiguration = new Configuration(startingConfigurationAttributes, "TSP starting configuration");

        this.initOperations();

        this.calculateNearestNeighbors(20);
        this.metainfo = new TSPMetainfoStatic(this.coordinates, this.distances);
    }


    /**
     * Rounds double to integer using TSPLIB convention.
     *
     * @param x double to be rounded
     * @return rounded value
     */
    private int nint(double x) {
        return ((x) >= 0 ? (int) Math.floor(x + .5) : (int) -Math.floor(.5 - x));
    }

    /**
     * Loads TSP from a file.
     *
     * Expected format is:
     *
     * <pre>
     * NAME : eil51
     * COMMENT : 51-city problem (Christofides/Eilon)
     * TYPE : TSP
     * DIMENSION : 51
     * EDGE_WEIGHT_TYPE : EUC_2D
     * NODE_COORD_SECTION
     * 1 37 -52.0
     * 2 49 49
     * (...)
     * 51 30 40
     * EOF
     * </pre>
     *
     * Where recognized EDGE_WEIGHT_TYPEs are EUC_2D (using {@link Math#round(float)} and CEIL_2D (using {@link Math#ceil(double)}. TYPE has to be TSP. Name and comment
     * are ignored (only added to label). Anything after EOF is ignored. Fractional parts of coordinations are ignored.
     *
     * @param configFile input file to load data from
     * @throws IOException if problem loading file occurs
     * @throws ProblemFormatException if line format is invalid
     */
    public FastTSP(File configFile) throws IOException, ProblemFormatException {
        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String line;

        Pattern configPattern = Pattern.compile("^([A-Za-z0-9_]+) *: *(.*)");
        Pattern startPattern = Pattern.compile("^NODE_COORD_SECTION");
        Pattern stopPattern = Pattern.compile("^\\s*EOF");
        Pattern nodePattern = Pattern.compile("^\\s*(\\d+)\\s+(-?\\d+(?:\\.\\d+)?)\\s+(-?\\d+(?:\\.\\d+)?)\\s*$");

        boolean coordSection = false;
        int lineCounter = 0;
        String name = "";
        String comment = "";
        String type = "";
        String edge = "";
        this.dimension = 0;
        coordinates = new ArrayList<Double[]>();

        while ((line = br.readLine()) != null) {
            Matcher m;
            lineCounter++;

            if (!coordSection) {
                // config line
                m = configPattern.matcher(line);
                if (m.find()) {
                    if (m.group(1).equals("NAME")) name = m.group(2);
                    else if (m.group(1).equals("COMMENT")) comment = m.group(2);
                    else if (m.group(1).equals("TYPE")) type = m.group(2);
                    else if (m.group(1).equals("DIMENSION")) this.dimension = Integer.valueOf(m.group(2));
                    else if (m.group(1).equals("EDGE_WEIGHT_TYPE")) edge = m.group(2);
                }

                // coordinates started
                m = startPattern.matcher(line);
                if (m.find()) {
                    this.setLabel (name + " (" + comment + "; " + configFile.getName() + ")");
                    if (!type.equals("TSP")) throw new ProblemFormatException("Type must be TSP, " + type + " found");
                    if (!edge.equals("EUC_2D") && !edge.equals("CEIL_2D")) throw new ProblemFormatException("Edge type must be EUC_2D or CEIL_2D, " + edge + " found");
                    if (this.dimension < 2) throw new ProblemFormatException("Requires at least 2 cities, " + this.dimension + " found");
                    coordinates.ensureCapacity(this.dimension);
                    coordSection = true;
                }
            } else if (coordSection) {
                // EOF
                m = stopPattern.matcher(line);
                if (m.find()) break;

                m = nodePattern.matcher(line);
                if (!m.find()) throw new ProblemFormatException("Line (" + lineCounter + ") different from node or EOF found after NODE_COORD_SECTION: " + line);

                int index;
                index = Integer.valueOf(m.group(1));
                Double coordinate[] = {Double.valueOf(m.group(2)), Double.valueOf(m.group(3))};

                if (index != coordinates.size() + 1) throw new ProblemFormatException("Found index " + index + " on line (" + lineCounter + ") \"" + line + "\", expected " + (coordinates.size() + 1));

                coordinates.add(coordinate);
            }
        }

        if (coordinates.size() != this.dimension) throw new ProblemFormatException("Required " + this.dimension + " coordinates, found " + coordinates.size());

        distances = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; ++i) {
            // add distance to target city
            for (int j = 0; j < this.dimension; ++j) {
                if (i != j) {
                    int distance;
                    double z1 = (coordinates.get(i)[0] - coordinates.get(j)[0]) * (coordinates.get(i)[0] - coordinates.get(j)[0]);
                    double z2 = (coordinates.get(i)[1] - coordinates.get(j)[1]) * (coordinates.get(i)[1] - coordinates.get(j)[1]);
                    if (edge.equals("CEIL_2D")) {
                        distance = (int) Math.ceil(Math.sqrt(z1 + z2));
                    } else {
                        distance = nint(Math.sqrt(z1 + z2));
                    }
                    distances[i][j] = distance;
                }
            }
        }

        // init starting configuration
        List<Integer> startingConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i)
            startingConfigurationAttributes.add(i);
        this.startingConfiguration = new Configuration(startingConfigurationAttributes, "TSP starting configuration");

        this.calculateNearestNeighbors(20);
        this.metainfo = new TSPMetainfoStatic(this.coordinates, this.distances);

        this.initOperations();
    }

    protected void initOperations () {
        // prepare switch operation container
        this.fastInvertSubtourOperations = new ArrayList<FastInvertSubtourOperation>(this.dimension * (this.dimension - 1) / 2);

        for (int i = 0; i < this.dimension; ++i) {
            for (int j = 0; j < this.dimension; ++j) {
                if (i < this.dimension - 1 && j > i)
                    this.fastInvertSubtourOperations.add(new FastInvertSubtourOperation(i, j, distances, nearestNeighbors));
            }
        }

    }

    /**
     * Returns absolute length of a path
     *
     * @param configuration path
     * @return length
     */
    public double pathLength (Configuration configuration) {
        double distance = 0.0;
        for (int i = 1; i < this.dimension; ++i) {
            distance += distances[configuration.valueAt(i - 1)][configuration.valueAt(i)];
        }
        distance += distances[configuration.valueAt(configuration.getDimension() - 1)][configuration.valueAt(0)];

        return distance;
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

    public OperationIterator getOperationIterator(Configuration configuration) {
        return new FastTSPIterator(configuration, this);
    }

    public Fitness getDefaultFitness() {
        return new FastTSPFitness(this);
    }

    /* StartingConfigurationProblem interface */

    public Configuration getStartingConfiguration() {
        return this.startingConfiguration;
    }

    /* GlobalSearchProblem interface */

    public Integer getMaximum(int index) {
        return this.distances.length - 1;
    }

    /* RandomConfigurationProblem interface */

    public Configuration getRandomConfiguration() {
        List<Integer> integerList = getStartingConfiguration().asList();
        Collections.shuffle(integerList);
        return new Configuration(integerList, "TSP random configuration");
    }

    /**
     * Calculate nn nearest neighbors for each city (used by various speed up techniques)
     *
     * @param nn
     */
    private void calculateNearestNeighbors(int nn) {
        int n = this.dimension;
        if ((nn > 0) && (nn < n)) {
            nearestNeighbors = new int[n][nn];

            Neighbor neighbors[] = new Neighbor[n];
            for (int i = 0; i < n; i++) neighbors[i] = new Neighbor();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    neighbors[j].id = j;
                    neighbors[j].distance = this.distances[i][j];
                }
                neighbors[i].distance = Integer.MAX_VALUE;
                Arrays.sort(neighbors);
                for (int j = 0; j < nn; j++) nearestNeighbors[i][j] = neighbors[j].id;
            }
        }
     }
}
