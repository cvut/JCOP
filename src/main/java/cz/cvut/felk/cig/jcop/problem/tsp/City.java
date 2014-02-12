/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import java.util.HashMap;
import java.util.Map;

/**
 * One city in TSP problem.
 * <p/>
 * Every city has its index and is able to tell distance to other cities.
 *
 * @author Ondrej Skalicka
 */
public class City {
    /**
     * Index of city.
     */
    protected int index;

    /**
     * Map of distances to other cities.
     */
    protected Map<City, Integer> distances;

    /**
     * Creates new city with given index.
     *
     * @param index index of city
     */
    public City(int index) {
        this.index = index;
        this.distances = new HashMap<City, Integer>();
    }

    /**
     * Creates new city with given index and prepares distance map to exact dimension.
     *
     * @param index     index of city
     * @param dimension dimension of distance map
     */
    public City(int index, int dimension) {
        this.index = index;
        this.distances = new HashMap<City, Integer>(dimension);
    }

    /**
     * Creates new city with given index and distance map.
     *
     * @param index     index of city
     * @param distances distance map
     */
    public City(int index, Map<City, Integer> distances) {
        this.index = index;
        this.distances = distances;
    }

    /**
     * Adds new distance to city.
     *
     * @param city     target city
     * @param distance distance to city
     * @return self, fluent interface
     */
    public City addDistance(City city, Integer distance) {
        this.distances.put(city, distance);
        return this;
    }

    /**
     * Returns distance to destination city.
     * <p/>
     * If there is no route to city, returns null.
     *
     * @param city target city
     * @return distance or null if no route exists
     */
    public Integer getDistance(City city) {
        return this.distances.get(city);
    }

    /**
     * Index of city.
     *
     * @return index of city
     */
    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        return index == city.index;

    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return "City{" +
                "index=" + index +
                '}';
    }
}
