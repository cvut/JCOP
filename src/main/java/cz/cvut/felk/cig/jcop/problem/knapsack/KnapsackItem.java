/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

/**
 * Item which could be added or removed from knapsack.
 * <p/>
 * Every item has its weight, price and index.
 *
 * @author Ondrej Skalicka
 */
public class KnapsackItem {
    /**
     * Index of item.
     */
    protected int index;
    /**
     * Weight of item.
     */
    protected int weight;
    /**
     * Price of item.
     */
    protected int price;

    /**
     * Creates knapsack item with given weight, price and index.
     *
     * @param index  index of item
     * @param weight weight of item
     * @param price  price of item
     */
    public KnapsackItem(int index, int weight, int price) {
        this.weight = weight;
        this.price = price;
        this.index = index;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected KnapsackItem clone() throws CloneNotSupportedException {
        super.clone();
        return new KnapsackItem(this.index, this.weight, this.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KnapsackItem)) return false;

        KnapsackItem that = (KnapsackItem) o;

        return that.weight == this.weight &&
                that.price == this.price &&
                that.index == this.index;
    }

    @Override
    public String toString() {
        return "KnapsackItem{" +
                "index=" + index +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
