package cz.cvut.felk.cig.jcop.problem.tspfast;

/**
 * Neighbor structure used for sorting
 */
class Neighbor implements Comparable {
    int id;
    int distance;

    public int compareTo(Object o) {
        Neighbor tmp = (Neighbor) o;
        if (this.distance < tmp.distance) return -1;
        else if (this.distance > tmp.distance) return 1;
        return 0;
    }
}
