/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

import cz.cvut.felk.cig.jcop.problem.*;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Knapsack problem definition consists of having a knapsack of given capacity and a list of items, each having weight
 * and price. The goal is to fit in the knapsack items worth the most possible amount while not exceeding the knapsack
 * capacity.
 *
 * @author Ondrej Skalicka
 * @see <a href="http://service.felk.cvut.cz/courses/X36PAA/knapsack.html"> Knapsack problem on felk.cvut.fel</a>
 */
public class Knapsack extends BaseProblem implements StartingConfigurationProblem, RandomConfigurationProblem, GlobalSearchProblem {
    /**
     * List of all possible AddOperations. Index is {@link KnapsackItem#index} of {@link KnapsackItem}.
     */
    protected List<AddOperation> addOperations;
    /**
     * List of all possible RemoveOperations. Index is {@link KnapsackItem#index} of {@link KnapsackItem}.
     */
    protected List<RemoveOperation> removeOperations;
    /**
     * Id of problem instance if supplied in {@link #Knapsack(java.io.File, String)}.
     */
    protected String id;
    /**
     * Capacity of knapsack. Only configurations with weight lower or equal to capacity are considered solutions.
     */
    protected int capacity;
    /**
     * List of all items for this knapsack.
     */
    protected List<KnapsackItem> knapsackItems;
    /**
     * Starting configuration, containing no items.
     */
    protected Configuration startingConfiguration;

    /**
     * Creates knapsack from file. Takes first line and then uses it for {@link #init(String)}. Expects same format as
     * {@link #init(String)}.
     *
     * @param configFile input file to load data from
     * @throws IOException            if problem loading file occurs
     * @throws ProblemFormatException if line format is invalid
     */
    public Knapsack(File configFile) throws IOException, ProblemFormatException {
        this.setLabel("file=" + configFile.getName());

        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String line;
        // fetch just one first line
        if ((line = br.readLine()) == null) {
            throw new IOException("Cannot fetch first line from file " + configFile);
        }
        br.close();

        this.init(line);
    }

    /**
     * Creates knapsack from file. Tries to find a line with id equal to specified parameter. Then initializes knapsack
     * using {@link #init(String)}.
     * <p/>
     * Line format is identical to {@link #init(String)}.
     *
     * @param configFile input file to load data from
     * @param id         id of row to be fetched
     * @throws IOException              if problem loading file occurs
     * @throws ProblemNotFoundException if problem with such id is not found
     * @throws ProblemFormatException   if line format is invalid
     */
    public Knapsack(File configFile, String id) throws IOException, ProblemNotFoundException, ProblemFormatException {
        this.setLabel("file=" + configFile.getName() + ",id=" + id);

        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String line;
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            if (st.nextToken().equals(id)) {
                this.init(line);
                br.close();
                return;
            }
        }
        br.close();
        throw new ProblemNotFoundException("Cannot fetch line with id " + id + " from file " + configFile);

    }

    /**
     * Create knapsack from a single string. Works just as {@link #init(String)} (actually {@link #init(String)} is
     * called in constructor) and format of line is expected the same.
     *
     * @param line string containing data to init this knapsack
     * @throws ProblemFormatException if line format is invalid
     */
    public Knapsack(String line) throws ProblemFormatException {
        this.setLabel("line=" + line);

        this.init(line);
    }

    /**
     * Init knapsack from one line of string. Expects format
     * <p/>
     * id dimension capacity weight1 price1 weight2 price2 ...  weightN priceN (all numeric)
     *
     * @param line string containing data to init this knapsack
     * @throws ProblemFormatException if line format is invalid
     */
    protected void init(String line) throws ProblemFormatException {
        StringTokenizer st = new StringTokenizer(line);

        try {
            this.id = st.nextToken();
            this.dimension = Integer.valueOf(st.nextToken());
            this.capacity = Integer.valueOf(st.nextToken());

            this.knapsackItems = new ArrayList<KnapsackItem>(this.dimension);
            for (int i = 0; i < dimension; i++) {
                int weight = Integer.valueOf(st.nextToken());
                int price = Integer.valueOf(st.nextToken());
                this.knapsackItems.add(new KnapsackItem(i, weight, price));
            }

            if (st.hasMoreTokens())
                throw new ProblemFormatException("Too many elements in line");
        } catch (NoSuchElementException e) {
            throw new ProblemFormatException("Insufficient number of elements in line");
        } catch (NumberFormatException e) {
            throw new ProblemFormatException("Non numeric elements found in line");
        }

        this.addOperations = new ArrayList<AddOperation>(this.dimension);
        this.removeOperations = new ArrayList<RemoveOperation>(this.dimension);
        AddOperation addTmp;
        RemoveOperation removeTmp;

        for (int i = 0; i < this.dimension; i++) {
            addTmp = new AddOperation(this.knapsackItems.get(i));
            removeTmp = new RemoveOperation(this.knapsackItems.get(i));
            // set reverses
            addTmp.setReverse(removeTmp);
            removeTmp.setReverse(addTmp);
            // add operations to list
            this.addOperations.add(addTmp);
            this.removeOperations.add(removeTmp);
        }

        List<Integer> tmp = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i) tmp.add(0);
        this.startingConfiguration = new Configuration(tmp, "Empty knapsack created");
    }

    public boolean isSolution(Configuration configuration) {
        int totalWeight = 0;
        for (KnapsackItem it : this.knapsackItems) {
            if (configuration.valueAt(it.getIndex()) == 1) {
                totalWeight += it.getWeight();
                if (totalWeight > this.capacity) return false;
            }
        }
        return true;
    }

    public KnapsackIterator getOperationIterator(Configuration configuration) {
        return new KnapsackIterator(configuration, this);
    }

    public Fitness getDefaultFitness() {
        return new KnapsackFitness(this);
    }

    /**
     * Returns price of items in given configuration.
     *
     * Does not check for capacity.
     *
     * @param configuration configuration to calculate price of
     * @return price of configuration
     */
    public long getPrice(Configuration configuration) {
        long total = 0;
        for (KnapsackItem knapsackItem : getKnapsackItems()) if (configuration.valueAt(knapsackItem.getIndex()) == 1) total += knapsackItem.getPrice();
        return total;
    }

    /* required for fitness calculations */

    public int getCapacity() {
        return capacity;
    }

    public List<KnapsackItem> getKnapsackItems() {
        return knapsackItems;
    }

    /* StartingConfigurationProblem interface */

    public Configuration getStartingConfiguration() {
        return this.startingConfiguration;
    }

    /* RandomConfigurationProblem interface */

    public Configuration getRandomConfiguration() {
        List<Integer> tmp = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i)
            tmp.add(JcopRandom.nextBoolean() ? 1 : 0);
        return new Configuration(tmp, "Empty knapsack created (random)");
    }

    /* GlobalSearchProblem interface */

    public Integer getMaximum(int index) {
        return 1;
    }
}
