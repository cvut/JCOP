/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Implements some methods of {@link Problem} identical for most problems.
 * <p/>
 * Currently only {@link #getConfigurationMap()} is implemented, returning {@link IdentityConfigurationMap}.
 * <p/>
 * Note that to problems are considered equal if they are of same class (eg. getClass().equals(this.getClass()) equals)
 * and their labels are equal.
 *
 * @author Ondrej Skalicka
 */
public abstract class BaseProblem implements Problem {
    /**
     * Dimension of problem
     */
    protected int dimension;
    /**
     * Label for problem.
     * <p/>
     * For more info, see {@link cz.cvut.felk.cig.jcop.problem.Problem#getLabel()}.
     */
    protected String label = "";

    public ConfigurationMap getConfigurationMap() {
        return new IdentityConfigurationMap();
    }

    public int getDimension() {
        return this.dimension;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!o.getClass().equals(this.getClass())) return false;
        BaseProblem that = (BaseProblem) o;

        return !(this.getLabel() != null ? !label.equals(that.getLabel()) : that.getLabel() != null);
    }


    @Override
    public String toString() {
        if ("".equals(this.getLabel())) return this.getClass().getSimpleName();
        return this.getClass().getSimpleName() + " [" + this.getLabel() + "]";
    }
}
