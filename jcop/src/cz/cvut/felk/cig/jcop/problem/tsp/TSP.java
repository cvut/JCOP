/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Traveling Salesman Problem.
 * <p/>
 * TSP problem has a list of cities and every city has distance to every other city. The goal is to find order in which
 * to visit every city exactly once and minimize travel distance.
 *
 * @author Ondrej Skalicka
 */
public class TSP extends BaseProblem implements StartingConfigurationProblem, GlobalSearchProblem {
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
     * 1 37 52
     * 2 49 49
     * (...)
     * 51 30 40
     * EOF
     * </pre>
     *
     * Where recognized EDGE_WEIGHT_TYPEs are EUC_2D (using {@link Math#round(float)} and CEIL_2D (using {@link Math#ceil(double)}. TYPE has to be TSP. Name and comment
     * are ignored (only added to label). Anything after EOF is ignored.
     * 
     * @param configFile input file to load data from
     * @throws IOException if problem loading file occurs
     * @throws ProblemFormatException if line format is invalid
     */
    public TSP(File configFile) throws IOException, ProblemFormatException {
        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String line;

        Pattern configPattern = Pattern.compile("^([A-Za-z0-9_]+) *: *(.*)");
        Pattern startPattern = Pattern.compile("^NODE_COORD_SECTION");
        Pattern stopPattern = Pattern.compile("^EOF");
        Pattern nodePattern = Pattern.compile("^\\s*(\\d+)\\s+(-?\\d+)\\s+(-?\\d+)\\s*$");

        boolean coordSection = false;
        int lineCounter = 0;
        String name = "";
        String comment = "";
        String type = "";
        String edge = "";
        this.dimension = 0;
        ArrayList<Integer[]> coordinates = new ArrayList<Integer[]>();

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
                Integer coordinate[] = {Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3))};

                if (index != coordinates.size() + 1) throw new ProblemFormatException("Found index " + index + " on line (" + lineCounter + ") \"" + line + "\", expected " + (coordinates.size() + 1));

                coordinates.add(coordinate);
            }
        }

        if (coordinates.size() != this.dimension) throw new ProblemFormatException("Required " + this.dimension + " coordinates, found " + coordinates.size());

        this.switchCityOperations = new ArrayList<SwitchCityOperation>(this.dimension * (this.dimension - 1) / 2);
        this.cities = new ArrayList<City>(this.dimension);
        for (int i = 0; i < this.dimension; ++i) {
            this.cities.add(new City(i, this.dimension));
        }
        for (int i = 0; i < this.dimension; ++i) {
            City city = this.cities.get(i);

            // add distance to target city
            for (int j = 0; j < this.dimension; ++j) {
                if (i < this.dimension - 1 && j > i)
                    this.switchCityOperations.add(new SwitchCityOperation(i, j));

                if (i != j) {
                    int distance;
                    if (edge.equals("CEIL_2D")) distance = (int)Math.ceil(Math.sqrt(Math.pow(coordinates.get(i)[0] - coordinates.get(j)[0], 2) + Math.pow(coordinates.get(i)[1] - coordinates.get(j)[1], 2)));
                    else distance = (int)Math.round(Math.sqrt(Math.pow(coordinates.get(i)[0] - coordinates.get(j)[0], 2) + Math.pow(coordinates.get(i)[1] - coordinates.get(j)[1], 2)));
                    city.addDistance(this.cities.get(j), distance);
                }
            }
        }

        // init starting configuration
        List<Integer> startingConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i)
            startingConfigurationAttributes.add(i);
        this.startingConfiguration = new Configuration(startingConfigurationAttributes, "TSP starting configuration");
    }

    /**
     * Returns absolute length of a path
     *
     * @param configuration path
     * @return length
     */
    public long pathLength (Configuration configuration) {
        long distance = 0;
        for (int i = 1; i < this.dimension; ++i) {
            distance += this.cities.get(configuration.valueAt(i - 1)).getDistance(this.cities.get(configuration.valueAt(i)));
        }

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

    /* GlobalSearchProblem interface */
    
    public Integer getMaximum(int index) {
        return this.cities.size() - 1;
    }
}
