/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.*;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Boolean satisfiability problem.
 * <p/>
 * Satisfiability is the problem of determining if the variables of a given Boolean formula can be assigned in such a
 * way as to make the formula evaluate to TRUE.
 *
 * @author Ondrej Skalicka
 * @see <a href="http://service.felk.cvut.cz/courses/X36PAA/maxwsat.html">WSAT on felk.cvut.cz</a>
 */
public class SAT extends BaseProblem implements StartingConfigurationProblem, RandomConfigurationProblem, GlobalSearchProblem {
    /**
     * List of all possible {@link SetTrueOperation SetTrueOperations}. Index is {@link Variable#index}.
     */
    protected List<SetTrueOperation> setTrueOperations;
    /**
     * List of all possible {@link SetFalseOperation SetFalseOperations}. Index is {@link Variable#index}.
     */
    protected List<SetFalseOperation> setFalseOperations;
    /**
     * List of all items for this knapsack.
     */
    protected Formula formula;
    /**
     * List of all variables in problem.
     */
    protected List<Variable> variables;
    /**
     * Starting configuration in SAT - all variables set to false.
     */
    protected Configuration startingConfiguration;

    /**
     * Creates SAT from file.
     * <p/>
     * Ignores lines beginning with 'c'. Requires configuration line (beginning with 'p') in  format 'p cnf X  Y' where
     * X is number of variables, Y is number of clauses in formula. Optional weight line (beginning with 'w') in format
     * 'w X1 X2 X3 X4..Xn", where n is number of variables. Assigns weight to each variable
     * <p/>
     * Then expects list of clauses. Each clause contains several numbers, indicating index of variable (starting with
     * 1!). Negative number means negated variable. Each clause ends with zero (new line is not required. Clauses must
     * be after configuration and weight line.
     *
     * @param configFile input file to load data from
     * @throws java.io.IOException    if problem loading file occurs
     * @throws ProblemFormatException if line format is invalid
     */
    public SAT(File configFile) throws IOException, ProblemFormatException {
        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String line;

        Pattern commentPattern = Pattern.compile("^c");
        Pattern configPattern = Pattern.compile("^p cnf\\s+(\\d+)\\s+(\\d+)");
        Pattern clausePattern = Pattern.compile("^\\s*-?\\d+(\\s+-?\\d+)*\\s*$");
        Pattern clauseAtomPattern = Pattern.compile("(-)?(\\d+)");
        Pattern ignoredPattern = Pattern.compile("^\\s*(0|%)?\\s*$");
        Pattern weightAtomPattern = Pattern.compile("\\d+");
        Pattern weightPattern = Pattern.compile("^w(\\s+(\\d+))+\\s*$");

        boolean configurationFound = false;
        int clausesCount = -1;
        int lineCounter = 0;
        Clause activeClause = new Clause();
        this.formula = new Formula();


        while ((line = br.readLine()) != null) {
            Matcher m;
            lineCounter++;
            // skip comments
            m = commentPattern.matcher(line);
            if (m.find()) continue;
            // config line
            m = configPattern.matcher(line);
            if (m.find()) {
                if (configurationFound)
                    throw new ProblemFormatException("Found two sets of configurations, 2nd on line " + lineCounter);
                configurationFound = true;
                this.dimension = Integer.parseInt(m.group(1));
                this.variables = new ArrayList<Variable>(this.dimension);
                for (int i = 0; i < this.dimension; ++i)
                    this.variables.add(new Variable(i, i + 1));
                clausesCount = Integer.parseInt(m.group(2));

                continue;
            }
            // weight line
            m = weightPattern.matcher(line);
            if (m.find()) {
                if (!configurationFound)
                    throw new ProblemFormatException("Configuration not found but weight found on line " + lineCounter);

                m = weightAtomPattern.matcher(line);
                for (int i = 0; i < this.dimension; ++i) {
                    if (!m.find())
                        throw new ProblemFormatException("" + (this.dimension) + " weights required, " + i + " found");
                    this.variables.get(i).setWeight(Integer.parseInt(m.group()));
                }
                if (m.find())
                    throw new ProblemFormatException("" + (this.dimension) + " weights required, at least " + (this.dimension + 1) + " found");

                continue;
            }
            // clause line
            m = clausePattern.matcher(line);
            if (m.find()) {
                if (!configurationFound)
                    throw new ProblemFormatException("Configuration not found but clause found on line " + lineCounter);

                m = clauseAtomPattern.matcher(line);

                while (m.find()) {
                    int value = Integer.parseInt(m.group(2));
                    boolean negated = m.group(1) != null;

                    // zero found, add new clause
                    if (value == 0) {
                        // skip empty clauses
                        if (activeClause.size() > 0) {
                            this.formula.addClause(activeClause);
                            activeClause = new Clause();
                        }
                        continue;
                    }

                    Variable tmp;
                    try {
                        tmp = this.variables.get(value - 1);
                    } catch (IndexOutOfBoundsException e) {
                        throw new ProblemFormatException("Unknown variable " + value + " on line " + lineCounter);
                    }

                    if (negated) activeClause.addNegativeVariable(tmp);
                    else activeClause.addPositiveVariable(tmp);
                }

                continue;
            }

            m = ignoredPattern.matcher(line);

            if (m.find()) continue;

            throw new ProblemFormatException("Invalid line format (" + line + ") on line " + lineCounter);
        }

        if (activeClause.size() > 0) this.formula.addClause(activeClause);

        if (this.formula.clauses.size() != clausesCount)
            throw new ProblemFormatException("Invalid number of clauses found, expected " + clausesCount + ", found " + this.formula.clauses.size());

        this.setLabel(configFile.getName());
        this.initCommons();
    }

    /**
     * Initializes common attributes such as operations and default fitness.
     * <p/>
     * Requires {@link #formula}/{@link #variables} to be already fully loaded.
     */
    protected void initCommons() {
        this.setTrueOperations = new ArrayList<SetTrueOperation>(this.dimension);
        this.setFalseOperations = new ArrayList<SetFalseOperation>(this.dimension);
        SetTrueOperation setTrueOperation;
        SetFalseOperation setFalseOperation;
        for (int i = 0; i < this.dimension; ++i) {
            setTrueOperation = new SetTrueOperation(this.variables.get(i));
            setFalseOperation = new SetFalseOperation(this.variables.get(i));
            setTrueOperation.setReverse(setFalseOperation);
            setFalseOperation.setReverse(setTrueOperation);
            this.setTrueOperations.add(setTrueOperation);
            this.setFalseOperations.add(setFalseOperation);
        }
        // create starting configuration
        List<Integer> tmp = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i) tmp.add(0);
        this.startingConfiguration = new Configuration(tmp, "Empty SAT created");
    }

    /**
     * Returns formula of current SAT problem
     *
     * @return formula of current SAT problem
     */
    public Formula getFormula() {
        return formula;
    }

    /* Problem interface */

    public boolean isSolution(Configuration configuration) {
        return this.formula.isTrue(configuration);
    }

    public SATIterator getOperationIterator(Configuration configuration) {
        return new SATIterator(configuration, this);
    }

    public Fitness getDefaultFitness() {
        return new SATFitness(this);
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
        return new Configuration(tmp, "Empty SAT created (random)");
    }

    /* GlobalSearchProblem interface */

    public Integer getMaximum(int index) {
        return 1;
    }
}
