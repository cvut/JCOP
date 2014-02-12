/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration is representation of one state in state space. It has configuration attributes, dimension and history
 * of operations applied to this configuration.
 *
 * @author Ondrej Skalicka
 */
public class Configuration implements Comparable<Configuration> {
    /**
     * Dimension of this configuration, eg. number of configuration attributes.
     */
    protected int dimension;
    /**
     * Configuration attributes.
     */
    protected List<Integer> attributes;
    /**
     * Last element in operationHistory of this configuration.
     */
    protected OperationHistory operationHistory;

    /**
     * Creates new Configuration with set attributes and operation history.
     *
     * @param attributes       new attributes for Configuration. List is NOT copied so take care not to use it for more
     *                         than one configuration or change it afterwards.
     * @param operationHistory last item in configuration history.
     */
    public Configuration(List<Integer> attributes, OperationHistory operationHistory) {
        this.dimension = attributes.size();
        this.attributes = attributes;
        this.operationHistory = operationHistory;
    }

    /**
     * Special constructor for creating first configuration (eg. starting configuration). DO NOT use in operations!
     * <p/>
     * Is equivalent to new Configuration(attributes, new OperationHistory(new NewConfigurationOperation(createText))).
     *
     * @param attributes new attributes for Configuration. List is NOT copied so take care not to use it for more than
     *                   one configuration or change it afterwards.
     * @param createText text for {@link NewConfigurationOperation} constructor
     */
    public Configuration(List<Integer> attributes, String createText) {
        this(attributes, new OperationHistory(new NewConfigurationOperation(createText)));
    }

    /**
     * Creates new configuration with UnknownOperation in operation history.
     *
     * @param attributes new attributes for Configuration. List is NOT copied so take care not to use it for more than
     *                   one configuration or change it afterwards.
     */
    public Configuration(List<Integer> attributes) {
        this(attributes, new OperationHistory(new UnknownOperation()));
    }

    /**
     * Gets value of an attribute specified by index.
     *
     * @param index index of attribute
     * @return value of attribute
     * @throws IndexOutOfBoundsException if index < 0 or index >= dimension
     */
    public Integer valueAt(int index) throws IndexOutOfBoundsException {
        return attributes.get(index);
    }

    /**
     * Returns configuration as list.
     * <p/>
     * Note that new list is created so it is safe to just edit some values and pass it to new configuration.
     *
     * @return copy of list
     */
    public List<Integer> asList() {
        return new ArrayList<Integer>(this.attributes);
    }


    public int[] asSimpleArray() {
        int[] result = new int[this.attributes.size()];
        for (int i=0; i<this.attributes.size(); i++) result[i] = this.attributes.get(i).intValue();
        return result;
    }

    /**
     * Fingerprint is somehow similar to hashCode in that way, that two configurations that are considered equal via
     * CompareTo has the same fingerprint. However, contrast to hashCode, if two configurations has the same
     * fingerprint, they are considered equal.
     *
     * @return Fingerprint of configuration
     */
    public String fingerprint() {
        StringBuffer fingerprint = new StringBuffer("");
        for (int i = 0; i < this.dimension; ++i) {
            if (i > 0) fingerprint.append("-");
            fingerprint.append(attributes.get(i));
        }
        return fingerprint.toString();
    }

    /**
     * Returns last element in operation history for this configuration.
     *
     * @return last operation history element
     */
    public OperationHistory getOperationHistory() {
        return operationHistory;

    }

    /**
     * Size of configuration
     *
     * @return size of configuration
     */
    public int size() {
        return this.attributes.size();
    }

    public int compareTo(Configuration o) {
        return o.fingerprint().compareTo(this.fingerprint());
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public String toString() {
        if (this.operationHistory == null) {
            return "Configuration{" +
                    "attributes=" + attributes +
                    "}}";
        }
        if (this.operationHistory.getCounter() > 10) return "Configuration{" +
                "attributes=" + attributes +
                ", operationHistory={" + this.operationHistory.getCounter() +
                " items}}";
        return "Configuration{" +
                "attributes=" + attributes +
                ", operationHistory={" + operationHistory +
                "}}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration)) return false;

        Configuration that = (Configuration) o;

        return dimension == that.dimension && attributes.equals(that.attributes);
    }

    @Override
    public int hashCode() {
        int result = dimension;
        result = 31 * result + attributes.hashCode();
        return result;
    }
}