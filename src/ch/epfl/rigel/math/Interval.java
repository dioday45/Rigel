package ch.epfl.rigel.math;


/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * abstract class representing a mathematic interval
 *
 */
public abstract class Interval {

    private final double start;
    private final double end;

    /**
     * @param a : first element of the interval
     * @param b : last element of the interval
     */
    protected Interval(double a, double b) {
        this.start = a;
        this.end = b;
    }

    /**
     * @return the first element of the interval
     */
    public double low() {
        return start;
    }

    /**
     * @return the last element of the interval
     */
    public double high() {
        return end;
    }

    /**
     * @return the size of the interval
     */
    public double size() {
        return (end - start);
    }

    /**
     * abstract method redefined in subclasses. Indicate if v is contained in the interval
     *
     * @param v
     * @return true if v is in the interval
     */
    public abstract boolean contains(double v);

    @Override
    public final boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }
}
