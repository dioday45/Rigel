package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;


/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes a interval of the form [a,b[
 *
 */
public final class RightOpenInterval extends Interval {

    private RightOpenInterval(double a, double b) {
        super(a, b);
    }

    /**
     * Creates a right open interval [low, high[
     *
     * @param low  : lower bond of the interval
     * @param high : upper bond of the interval
     * @throws IllegalArgumentException if low >= high
     * @return a right open interval from low to high
     */
    public static RightOpenInterval of(double low, double high) {
        Preconditions.checkArgument((low < high));
        return new RightOpenInterval(low, high);
    }

    /**
     * creates a symmetric right open interval around 0
     *
     * @param size : size of the interval
     * @throws IllegalArgumentException if size <= 0
     * @return return a right open interval centralized on 0 and of size "size"
     */
    public static RightOpenInterval symmetric(double size) {
        Preconditions.checkArgument(size > 0);
        return new RightOpenInterval(-(size / 2), size / 2);
    }

    /**
     * reduces the value in the right open interval
     *
     * @param v : value tu be reduced
     * @return the reduction of v on the right open interval
     */
    public double reduce(double v) {
        return v - this.size() * Math.floor((v - this.low()) / this.size());
    }

    /**
     * @return the interval in a string form
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "[%f, %f[", this.low(), this.high());
    }

    /**
     * checks if an interval contains a value
     *
     * @param v : value to check if in interval
     * @return true if v is in the interval
     */
    @Override
    public boolean contains(double v) {
        //strict positive because right open interval
        return (v >= this.low() && v < this.high());
    }
}
