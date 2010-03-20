/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

/**
 * One bucket of bucket problem.
 * <p/>
 * Bucket has its capacity and index. Bucket's current contents are stored in configuration.
 *
 * @author Ondrej Skalicka
 */
public class BucketItem {
    /**
     * Capacity of this bucket.
     */
    protected int capacity;
    /**
     * Index of this bucket.
     */
    protected int index;

    /**
     * Creates new item for {@link cz.cvut.felk.cig.jcop.problem.bucket.Bucket} with given capacity and index.
     *
     * @param index    index of bucket
     * @param capacity capacity of bucket
     */
    public BucketItem(int index, int capacity) {
        this.capacity = capacity;
        this.index = index;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected BucketItem clone() throws CloneNotSupportedException {
        super.clone();
        return new BucketItem(this.index, this.capacity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BucketItem)) return false;

        BucketItem that = (BucketItem) o;

        return that.capacity == this.capacity && that.index == this.index;
    }

    @Override
    public String toString() {
        return "BucketItem{" +
                "capacity=" + capacity +
                ", index=" + index +
                '}';
    }
}
