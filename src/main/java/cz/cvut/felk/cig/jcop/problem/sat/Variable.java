/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

/**
 * Single variable of {@link SAT SAT problem}.
 *
 * @author Ondrej Skalicka
 */
public class Variable {
    /**
     * Index of variable.
     */
    protected int index;
    /**
     * Label of variable. Could vary from index (usually is index+1).
     */
    protected int label;
    /**
     * Weight of variable. Only for optimization problems, otherwise set to 1.
     */
    protected int weight;

    /**
     * Creates new Variable with unit weight and label equal to index.
     *
     * @param index index of variable
     */
    public Variable(int index) {
        this.index = index;
        this.label = index;
        this.weight = 1;
    }

    /**
     * Creates new Variable with unit weight.
     *
     * @param index index of variable
     * @param label label of variable
     */
    public Variable(int index, int label) {
        this.index = index;
        this.label = label;
        this.weight = 1;
    }

    /**
     * Creates new Variable with given index, label and weight
     *
     * @param index  index index of variable
     * @param label  label label of variable
     * @param weight weight label of variable
     */
    public Variable(int index, int label, int weight) {
        this.index = index;
        this.label = label;
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "index=" + index +
                ", label=" + label +
                ", weight=" + weight +
                '}';
    }
}
