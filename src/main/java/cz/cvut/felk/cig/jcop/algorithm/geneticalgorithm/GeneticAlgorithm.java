/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import cz.cvut.felk.cig.jcop.algorithm.BaseAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Genetic Algorithm implementation using tournament selection, one point crossover reproduction and random mutation.
 * <p/>
 * Starts with random population (with given size) and in every iteration creates new population from previous one.
 * Individuals to become parents are randomly selected from population based on their fitness such that higher fitness
 * means higher chance of became parent. Two parents produces two children, which could be possibly mutated. Repeating
 * this creates new population.
 * <p/>
 * Selection method is used tournament selection, reproduction is one point crossover. Mutation (if happens) changes one
 * configuration variable to a random value (in min/max bounds).
 *
 * @author Ondrej Skalicka
 */
public class GeneticAlgorithm extends BaseAlgorithm {
    /**
     * Number of chromosomes in population. Must be even and larger than 1.
     */
    protected int populationSize;
    /**
     * Chance of offspring being mutated.
     * <p/>
     * 0.0 means no chance, 1.0 means always mutate.
     */
    protected double mutationRate;
    /**
     * Whole current population (eg. parents).
     */
    protected List<Chromosome> population;
    /**
     * Selection used to get parents from population.
     */
    protected Selection selection;
    /**
     * Reproduction used to transform parents into children.
     */
    protected Reproduction reproduction;
    /**
     * Mutation used to mutate children.
     */
    protected Mutation mutation;

    /**
     * Creates new GA with given mutation rate and population size.
     *
     * @param populationSize number of chromosomes in each generation (must be even and larger than 1)
     * @param mutationRate   rate of mutation, number between 0.0 (no mutation) and 1.0 (always mutate)
     * @throws IllegalArgumentException if population size is not even or larger than 1
     */
    public GeneticAlgorithm(int populationSize, double mutationRate) throws IllegalArgumentException {
        if (populationSize < 2 || populationSize % 2 != 0)
            throw new IllegalArgumentException("GA requires population size to be even and larger than 1");
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;

        this.setLabel("p=" + populationSize + ", m=" + mutationRate);
    }

    public void init(ObjectiveProblem problem) throws InvalidProblemException {
        if (!problem.hasGlobalSearch()) throw new InvalidProblemException("Requires problem with global search");
        if (!problem.hasRandomConfiguration())
            throw new InvalidProblemException("Requires problem with random starting configuration");

        this.fitness = problem.getDefaultFitness();
        this.problem = problem;
        // create population, find best configuration
        this.population = new ArrayList<Chromosome>(this.populationSize);
        for (int i = 0; i < this.populationSize; ++i) {
            Chromosome chromosome = new Chromosome(this.problem.getRandomConfiguration());
            this.population.add(chromosome);
            chromosome.setFitness(this.fitness.getValue(chromosome.getConfiguration()));
            if (this.bestConfiguration == null || this.bestFitness < chromosome.getFitness()) {
                this.bestConfiguration = chromosome.getConfiguration();
                this.bestFitness = chromosome.getFitness();
            }
        }

        this.selection = new TournamentSelection();
        this.reproduction = new OnePointCrossoverReproduction();
        this.mutation = new SimpleMutation(this.problem, this.mutationRate);
    }

    public void optimize() throws CannotContinueException {
        List<Chromosome> newGeneration = new ArrayList<Chromosome>(this.populationSize);
        this.selection.init(this.population);

        for (int i = 0; i < this.populationSize / 2; ++i) {
            List<Chromosome> children = this.reproduction.reproduce(this.selection.select(), this.selection.select(), this.mutation);
            Chromosome son = children.get(0);
            Chromosome daughter = children.get(1);

            son.setFitness(this.fitness.getValue(son.getConfiguration()));
            daughter.setFitness(this.fitness.getValue(daughter.getConfiguration()));

            if (son.getFitness() > this.bestFitness) {
                this.bestFitness = son.getFitness();
                this.bestConfiguration = son.getConfiguration();
            }
            if (daughter.getFitness() > this.bestFitness) {
                this.bestFitness = daughter.getFitness();
                this.bestConfiguration = daughter.getConfiguration();
            }

            newGeneration.add(son);
            newGeneration.add(daughter);
        }

        this.population = newGeneration;
    }

    @Override
    public void cleanUp() {
        this.population = null;
    }
}
