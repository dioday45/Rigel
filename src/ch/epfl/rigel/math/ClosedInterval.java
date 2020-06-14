package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * class representing a closed interval of the type [a, b]
 *
 */
public final class ClosedInterval extends Interval {

    /**
     * private constructor
     *
     * @param a : lower bond of the interval
     * @param b : upper bond of the interval
     */
    private ClosedInterval(double a, double b) {
        super(a, b);
    }

    /**
     * Creates a closedInterval [low, high]
     *
     * @param low  : lower bond of the interval
     * @param high : upper bond of the interval
     * @throws IllegalArgumentException if low >= high
     * @return a closed interval from low to high
     */
    public static ClosedInterval of(double low, double high) {
        Preconditions.checkArgument(low < high);
        return new ClosedInterval(low, high);
    }

    /**
     * creates a symmetric close interval around 0
     * @param size : size of the interval
     * @throws IllegalArgumentException if size <= 0
     * @return return a closed interval centralized on 0 and of size "size"
     */
    public static ClosedInterval symmetric(double size) {
        Preconditions.checkArgument(size > 0);
        return new ClosedInterval(-(size / 2), size / 2);
    }

    /**
     * clips v on the interval
     * @param v : value to clip
     * @return the value of v after being in the clip function
     */
    public double clip(double v) {
        if (v <= this.low()) return this.low();
        if (v >= this.high()) return this.high();
        return v;
    }


    @Override
    public boolean contains(double v) {
        return (v >= this.low() && v <= this.high());
    }


    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "[%f, %f]", this.low(), this.high());
    }
}
